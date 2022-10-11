package com.backbase.productled.validator;

import com.backbase.buildingblocks.presentation.errors.BadRequestException;
import com.backbase.productled.model.EmailV2;
import com.backbase.productled.testutils.EmailV2Factory;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
public class EmailV2ValidatorTest {
    private final EmailV2Validator emailV2Validator = new EmailV2Validator();

    @Test
    public void validate_success() {
        EmailV2 randomEmailV2 = EmailV2Factory.createRandomEmailV2();
        assertDoesNotThrow(() -> emailV2Validator.validate(randomEmailV2));
    }

    @Test
    public void validate_BadRequestException_emptyReceipants() {
        EmailV2 randomEmailV2 = EmailV2Factory.createRandomEmailV2();
        randomEmailV2.setTo(List.of());
        randomEmailV2.setCc(List.of());
        randomEmailV2.setBcc(List.of());
        assertThrows(BadRequestException.class, () -> emailV2Validator.validate(randomEmailV2));
    }

    @Test
    public void validate_BadRequestException_emptyFromList() {
        EmailV2 randomEmailV2 = EmailV2Factory.createRandomEmailV2();
        randomEmailV2.setFrom("");
        assertThrows(BadRequestException.class, () -> emailV2Validator.validate(randomEmailV2));
    }

}