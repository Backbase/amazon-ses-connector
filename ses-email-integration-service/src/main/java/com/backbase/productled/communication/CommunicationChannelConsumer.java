package com.backbase.productled.communication;


import com.backbase.productled.service.EmailNotificationService;
import com.backbase.productled.validator.BatchResponseValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.BatchResponse;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Content;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommunicationChannelConsumer implements Consumer<BatchResponse> {
    private final BatchResponseValidator requestValidator;
    private final EmailNotificationService emailNotificationService;

    @Override
    public void accept(BatchResponse batchResponse) {
        log.debug("BatchResponse payload: '{}'", batchResponse.toString());
        requestValidator.validateBatchResponse(batchResponse);

        Map<String, Content> contentMap = batchResponse.getContent()
                .stream()
                .collect(Collectors.toMap(Content::getContentId, Function.identity()));
        batchResponse.getRecipients().forEach(recipient -> emailNotificationService.processRecipient(recipient, contentMap.get(recipient.getContentId())));
    }


}
