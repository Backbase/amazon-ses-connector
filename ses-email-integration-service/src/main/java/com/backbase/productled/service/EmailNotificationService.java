package com.backbase.productled.service;

import com.backbase.buildingblocks.presentation.errors.InternalServerErrorException;
import com.backbase.email.integration.rest.spec.v2.email.Attachment;
import com.backbase.email.integration.rest.spec.v2.email.EmailPostRequestBody;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Status;
import com.backbase.productled.config.DefaultMailSenderProperties;
import com.backbase.productled.util.DeliveryCodes;
import com.backbase.productled.util.EmailPriority;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

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

    public Status sendEmail(EmailPostRequestBody emailPostRequestBody) {

        log.debug("Email: '{}'", emailPostRequestBody.toString());

        MimeMessage mimeMessage;
        try {
            mimeMessage = convertToMimeMessage(emailPostRequestBody);
            log.debug("Mime message subject: '{}' ", mimeMessage.getSubject());
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("Failed to convert request body to email '{}'", emailPostRequestBody);
            throw new InternalServerErrorException(e);
        }
        javaMailSender.send(mimeMessage);
        return new Status().status(DeliveryCodes.SENT);
    }

    private MimeMessage convertToMimeMessage(EmailPostRequestBody emailPostRequestBody)
            throws MessagingException, UnsupportedEncodingException {
        final var mimeMailMessage = new MimeMailMessage(javaMailSender.createMimeMessage());
        final var mimeMessage = mimeMailMessage.getMimeMessage();
        final var messageHelper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE);

        setEmailAddresses(messageHelper::setTo, emailPostRequestBody.getTo());
        setEmailAddresses(messageHelper::setCc, emailPostRequestBody.getCc());
        setEmailAddresses(messageHelper::setBcc, emailPostRequestBody.getBcc());

        messageHelper.setText(emailPostRequestBody.getBody(), HTML);
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
        //Resolve null for incoming message from web auth journey for eg. where cc, bcc is null
        if (nonNull(emails)) {
            if (!emails.isEmpty()) {
                emailsConsumer.acceptEmails(emails.toArray(new String[0]));
            }
        } else {
            log.debug("emails is null");
        }
    }

    private void setAttachments(MimeMessageHelper mimeMessageHelper, List<Attachment> attachments) {
        //Resolve null in attachments for incoming message from web auth journey
        if (nonNull(attachments)) {
            if (!attachments.isEmpty()) {
                attachments.forEach(addAttachmentToEmail(mimeMessageHelper));
            }
        } else {
            log.debug("Attachment is null.");
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
