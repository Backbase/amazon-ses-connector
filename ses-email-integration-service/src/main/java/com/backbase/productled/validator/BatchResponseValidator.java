package com.backbase.productled.validator;

import com.backbase.buildingblocks.presentation.errors.BadRequestException;
import com.backbase.buildingblocks.presentation.errors.Error;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.BatchResponse;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Content;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Recipient;
import com.backbase.productled.util.ErrorCodes;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Sample validator.
 */
@Component
public class BatchResponseValidator {

    /**
     * Simple validation method. <ul> <li>List of recipients should not be empty.</li> <li>List of Content should not be
     * empty.</li> <li>Recipient contentId should link to a Content object</li> </ul>
     */
    public void validateBatchResponse(BatchResponse requestBody) {

        // return BadRequest when list of recipients is empty
        if (CollectionUtils.isEmpty(requestBody.getRecipients())) {
            throw new BadRequestException().withMessage(ErrorCodes.ERR_01.getErrorMessage())
                    .withErrors(List.of(new Error().withKey(ErrorCodes.ERR_01.getErrorCode())
                            .withMessage(ErrorCodes.ERR_01.getErrorMessage())));
        }

        // return BadRequest when list of content is empty
        if (CollectionUtils.isEmpty(requestBody.getContent())) {
            throw new BadRequestException().withMessage(ErrorCodes.ERR_02.getErrorMessage())
                    .withErrors(List.of(new Error().withKey(ErrorCodes.ERR_02.getErrorCode())
                            .withMessage(ErrorCodes.ERR_02.getErrorMessage())));
        }

        // missing content
        for (Recipient recipient : requestBody.getRecipients()) {

            if (requestBody.getContent()
                    .stream()
                    .map(Content::getContentId)
                    .noneMatch(contentId -> Objects.equals(contentId, recipient.getContentId()))) {

                throw new BadRequestException().withMessage(ErrorCodes.ERR_03.getErrorMessage())
                        .withErrors(List.of(new Error().withKey(ErrorCodes.ERR_03.getErrorCode())
                                .withMessage(ErrorCodes.ERR_03.getErrorMessage())
                                .withContext(Map.of("contentId", recipient.getContentId()))));
            }
        }

    }

}
