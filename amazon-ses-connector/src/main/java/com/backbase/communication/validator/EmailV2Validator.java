package com.backbase.communication.validator;

import com.backbase.buildingblocks.presentation.errors.BadRequestException;
import com.backbase.buildingblocks.presentation.errors.Error;
import com.backbase.communication.model.EmailV2;
import com.backbase.communication.util.ErrorCodes;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Sample validator.
 */
@Component
public class EmailV2Validator {

    /**
     * Simple validation method. <ul> <li>List of recipients should not be empty.</li></ul>
     */
    public void validate(EmailV2 emailV2) {
        // return BadRequest when list of recipients is empty
        if (isEmptyRecipient(emailV2)) {
            throw new BadRequestException().withMessage(ErrorCodes.ERR_01.getErrorMessage())
                    .withErrors(List.of(new Error().withKey(ErrorCodes.ERR_01.getErrorCode())
                            .withMessage(ErrorCodes.ERR_01.getErrorMessage())));
        }

    }

    private boolean isEmptyRecipient(EmailV2 emailV2) {
        if(emailV2.getTo().isEmpty() && emailV2.getCc().isEmpty() && emailV2.getBcc().isEmpty())
            return true;
        return emailV2.getFrom().isEmpty();
    }

}
