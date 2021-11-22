package com.backbase.productled.service;

import com.backbase.buildingblocks.presentation.errors.InternalServerErrorException;
import com.backbase.email.integration.rest.spec.v2.email.Attachment;
import com.backbase.email.integration.rest.spec.v2.email.EmailPostRequestBody;
import com.backbase.productled.config.DefaultMailSenderProperties;
import com.backbase.productled.mapper.EmailPostRequestBodyMapper;
import com.backbase.productled.util.Base64Utils;
import com.backbase.productled.util.DeliveryCodes;
import com.backbase.productled.util.EmailPriority;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.Service;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Recipient;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Content;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Status;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Error;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.function.Consumer;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailNotificationService {

    private static final boolean MULTIPART_MODE = true;
    private static final boolean HTML = true;
    private final JavaMailSender javaMailSender;
    private final DefaultMailSenderProperties defaultMailSenderProperties;
    private final EmailPostRequestBodyMapper emailRequestMapper;

    public Status processRecipient(Recipient recipient, Content content) {
        var responseStatus = new Status().ref(recipient.getRef());
        Status deliveryStatus = null;

        try {
            deliveryStatus = sendEmail(recipient, content);
            responseStatus.setStatus(deliveryStatus.getStatus());
            responseStatus.setError(deliveryStatus.getError());
        } catch (Exception e) {
            log.error("Communications call failed with error: {}", e.getMessage());
            responseStatus.error(new Error().code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                            .message(e.getMessage()))
                    .status(DeliveryCodes.FAILED);
        }

        return deliveryStatus;
    }


    public Status sendEmail(Recipient recipient, Content content) {
        log.debug("Content data: '{}'", content);
        log.debug("Delivering Email from: '{}' to targets: '{}'", recipient.getFrom(), recipient.getTo());

        final var contentId = recipient.getContentId();
        final var recipientRef = recipient.getRef();
        final var emailPostRequestBody = emailRequestMapper.toEmailPostRequestBody(recipient, content);
        log.debug("EmailPostRequestBody: '{}'", emailPostRequestBody.toString());

        sendEmail(emailPostRequestBody);
        return new Status().status(DeliveryCodes.SENT);
    }

    public void sendEmail(EmailPostRequestBody emailPostRequestBody) {
        MimeMessage mimeMessage;
        try {
            mimeMessage = convertToMimeMessage(emailPostRequestBody);
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("Failed to convert request body to email {}", emailPostRequestBody);
            throw new InternalServerErrorException(e);
        }
        javaMailSender.send(mimeMessage);
    }

    private MimeMessage convertToMimeMessage(EmailPostRequestBody emailPostRequestBody)
            throws MessagingException, UnsupportedEncodingException {
        final var mimeMailMessage = new MimeMailMessage(javaMailSender.createMimeMessage());
        final var mimeMessage = mimeMailMessage.getMimeMessage();
        final var messageHelper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE);

        setEmailAddresses(messageHelper::setTo, emailPostRequestBody.getTo());
        setEmailAddresses(messageHelper::setCc, emailPostRequestBody.getCc());
        setEmailAddresses(messageHelper::setBcc, emailPostRequestBody.getBcc());

        if (!Base64Utils.isNotBase64(emailPostRequestBody.getBody())) {
            messageHelper.setText(Base64Utils.decodeBase64(emailPostRequestBody.getBody()), HTML);
        } else {
            // Email body from Identity - BatchPostResponseBody is not in base64 like in actions hence skip base64 decoding
            log.debug("Skipping email message body decoding.");
            messageHelper.setText(emailPostRequestBody.getBody(), HTML);
        }
        messageHelper.setPriority(EmailPriority.getPriority(emailPostRequestBody.getImportant()));
        messageHelper.setSubject(emailPostRequestBody.getSubject());
        setFromAddress(emailPostRequestBody.getFrom(), messageHelper);

        setAttachments(messageHelper, emailPostRequestBody.getAttachments());

        return mimeMessage;
    }

    private void setFromAddress(String from, MimeMessageHelper messageHelper)
            throws MessagingException, UnsupportedEncodingException {
        if (nonNull(from)) {
            messageHelper.setFrom(from);
        } else {
            messageHelper
                    .setFrom(defaultMailSenderProperties.getFromAddress(), defaultMailSenderProperties.getFromName());
        }
    }

    private void setEmailAddresses(EmailsConsumer emailsConsumer, List<String> emails) throws MessagingException {
        if (!emails.isEmpty()) {
            emailsConsumer.acceptEmails(emails.toArray(new String[0]));
        }
    }

    private void setAttachments(MimeMessageHelper mimeMessageHelper, List<Attachment> attachments) {
        if (!attachments.isEmpty()) {
            attachments.forEach(addAttachmentToEmail(mimeMessageHelper));
        }
    }

    private Consumer<Attachment> addAttachmentToEmail(MimeMessageHelper mimeMessageHelper) {
        return attachment -> {
            try {
                mimeMessageHelper.addAttachment(attachment.getFileName(),
                        () -> getAttachmentAsInputStream(attachment.getContent()));
            } catch (MessagingException e) {
                log.error("Failed to add file as attachment {}", attachment.getFileName());
                throw new InternalServerErrorException(e);
            }
        };
    }

    private ByteArrayInputStream getAttachmentAsInputStream(String attachmentContent) {
        final byte[] bytes = Base64.decodeBase64(attachmentContent);
        return new ByteArrayInputStream(bytes);
    }

}
