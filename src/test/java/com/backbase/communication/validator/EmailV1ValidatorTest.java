package com.backbase.communication.validator;

import com.backbase.buildingblocks.presentation.errors.BadRequestException;
import com.backbase.buildingblocks.presentation.errors.Error;
import com.backbase.communication.testutils.EmailV1Factory;
import com.backbase.communication.util.ErrorCodes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class EmailV1ValidatorTest {

    static final List<Error> ERROR_LIST_O1 = List.of(new Error()
            .withKey(ErrorCodes.ERR_01.getErrorCode())
            .withMessage(ErrorCodes.ERR_01.getErrorMessage()));

    static final List<Error> ERROR_LIST_O2 = List.of(new Error()
            .withKey(ErrorCodes.ERR_02.getErrorCode())
            .withMessage(ErrorCodes.ERR_02.getErrorMessage()));

    static final List<Error> ERROR_LIST_O3 = List.of(new Error()
            .withKey(ErrorCodes.ERR_03.getErrorCode())
            .withMessage(ErrorCodes.ERR_03.getErrorMessage())
            .withContext(Map.of("contentId", "1")));

    EmailV1Validator emailV1Validator;

    @Test
    public void expect_BadRequestException_whenRecipientIs_Empty() {
        emailV1Validator = new EmailV1Validator();
        try{
            emailV1Validator.validate(EmailV1Factory.emptyRecipientEmailV1());
        }catch(BadRequestException e){
            assertEquals(e.getMessage(), ErrorCodes.ERR_01.getErrorMessage());
            assertEquals(1, e.getErrors().size());
            assertEquals(ERROR_LIST_O1, e.getErrors().stream().collect(Collectors.toList()));
        }
    }

    @Test
    public void expect_BadRequestException_whenContentIs_Empty() {
        emailV1Validator = new EmailV1Validator();
        try{
            emailV1Validator.validate(EmailV1Factory.emptyContentEmailV1());
        }catch(BadRequestException e){
            assertEquals(e.getMessage(), ErrorCodes.ERR_02.getErrorMessage());
            assertEquals(1, e.getErrors().size());
            assertEquals(ERROR_LIST_O2, e.getErrors().stream().collect(Collectors.toList()));
        }
    }

    @Test
    public void expect_BadRequestException_whenContentId_does_not_match() {
        emailV1Validator = new EmailV1Validator();
        try{
            emailV1Validator.validate(EmailV1Factory.mismatchedContentIdEmailV1());
        }catch(BadRequestException e){
            assertEquals(e.getMessage(), ErrorCodes.ERR_03.getErrorMessage());
            assertEquals(1, e.getErrors().size());
            assertEquals(ERROR_LIST_O3, e.getErrors().stream().collect(Collectors.toList()));
        }

    }

}