package com.backbase.productled.validator;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.emptyIterable;
import static org.junit.Assert.assertThat;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;
import java.util.List;
import java.util.stream.Collectors;
import com.backbase.email.integration.rest.spec.v2.email.EmailPostRequestBody;
import com.backbase.productled.testutils.EmailRequestFactory;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

public class EmailAddressValidatorTest {

    private static final String OBJECT_NAME = "emailPostRequestBody";
    private Validator validator;

    @Before
    public void setUp() {
        this.validator = new EmailAddressValidator();
    }

    @Test
    public void shouldNotAddErrorsWhenRequestIsValid() {
        EmailPostRequestBody emailPostRequestBody = EmailRequestFactory.createRandomEmailRequest();
        Errors errors = new BeanPropertyBindingResult(emailPostRequestBody, OBJECT_NAME);
        validator.validate(emailPostRequestBody, errors);

        assertThat(errors.getAllErrors(), Matchers.is(emptyIterable()));
    }

    @Test
    public void shouldAddErrorWhen_To_Field_IsInvalid() {
        final String invalidEmail = "invalidEmail";
        EmailPostRequestBody emailPostRequestBody = EmailRequestFactory.createRandomEmailRequest().from(null).to(singletonList(
                invalidEmail));

        Errors errors = new BeanPropertyBindingResult(emailPostRequestBody, OBJECT_NAME);
        validator.validate(emailPostRequestBody, errors);

        assertReflectionEquals(singletonList("invalidEmail is not valid email address!"),
                getActualErrorMessages(errors));
    }

    @Test
    public void shouldAddErrorWhen_CC_Field_IsInvalid() {
        final String invalidEmail = "invalidEmail";
        EmailPostRequestBody emailPostRequestBody = EmailRequestFactory.createRandomEmailRequest().cc(singletonList(
                invalidEmail));

        Errors errors = new BeanPropertyBindingResult(emailPostRequestBody, OBJECT_NAME);
        validator.validate(emailPostRequestBody, errors);

        assertReflectionEquals(singletonList("invalidEmail is not valid email address!"),
                getActualErrorMessages(errors));
    }

    @Test
    public void shouldAddErrorWhen_BCC_Field_IsInvalid() {
        final String invalidEmail = "invalidEmail";
        EmailPostRequestBody emailPostRequestBody = EmailRequestFactory.createRandomEmailRequest().bcc(singletonList(
                invalidEmail));

        Errors errors = new BeanPropertyBindingResult(emailPostRequestBody, OBJECT_NAME);
        validator.validate(emailPostRequestBody, errors);

        assertReflectionEquals(singletonList("invalidEmail is not valid email address!"),
                getActualErrorMessages(errors));
    }

    @Test
    public void shouldAddErrorWhen_To_Field_IsNull() {
        EmailPostRequestBody emailPostRequestBody = EmailRequestFactory.createRandomEmailRequest().to(null);

        Errors errors = new BeanPropertyBindingResult(emailPostRequestBody, OBJECT_NAME);
        validator.validate(emailPostRequestBody, errors);

        assertReflectionEquals(singletonList("`TO` field could not be empty"),
                getActualErrorMessages(errors));
    }

    private List<String> getActualErrorMessages(Errors errors) {
        return errors.getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
    }


}