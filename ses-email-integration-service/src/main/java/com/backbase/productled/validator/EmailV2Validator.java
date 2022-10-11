package com.backbase.productled.validator;

import com.backbase.buildingblocks.presentation.errors.BadRequestException;
import com.backbase.buildingblocks.presentation.errors.Error;
import com.backbase.productled.model.EmailV2;
import com.backbase.productled.util.ErrorCodes;
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
        if (isEmptyReceipant(emailV2)) {
            throw new BadRequestException().withMessage(ErrorCodes.ERR_01.getErrorMessage())
                    .withErrors(List.of(new Error().withKey(ErrorCodes.ERR_01.getErrorCode())
                            .withMessage(ErrorCodes.ERR_01.getErrorMessage())));
        }

    }

    private boolean isEmptyReceipant(EmailV2 emailV2) {
        if(emailV2.getTo().isEmpty() && emailV2.getCc().isEmpty() && emailV2.getBcc().isEmpty())
            return true;
        if (emailV2.getFrom().isEmpty())
            return true;
        return false;
    }

}
