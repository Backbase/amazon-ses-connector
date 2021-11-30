/*
Boat Generator configuration:
    useBeanValidation: true
    useOptional: false
    addServletRequest: true
    useLombokAnnotations: false
    openApiNullable: true
    useSetForUniqueItems: false
    useWithModifiers: false
*/
package com.backbase.email.integration.rest.spec.v2.email;

import java.util.Objects;
import com.backbase.email.integration.rest.spec.v2.email.ErrorItem;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.*;


/**
 * BadRequestError
 */
@javax.annotation.Generated(value = "com.backbase.oss.codegen.java.BoatSpringCodeGen", date = "2021-11-30T10:29:10.600795Z[Etc/UTC]")

public class BadRequestError 
 {
    @JsonProperty("message")
    private  String message;

    @JsonProperty("errors")
    private List<ErrorItem> errors = null;


    public BadRequestError message(String message) {
        this.message = message;
        return this;
    }

    /**
     * Any further information
     * @return message
     */
    @ApiModelProperty(required = true, value = "Any further information")
    @NotNull 
    public  String getMessage() {
        return message;
    }

    public void setMessage( String message) {
        this.message = message;
    }


    public BadRequestError errors(List<ErrorItem> errors) {
        this.errors = errors;
        return this;
    }

    public BadRequestError addErrorsItem(ErrorItem errorsItem) {
        if (this.errors == null) {
            this.errors = new ArrayList<>();
        }
        this.errors.add(errorsItem);
        return this;
    }

    /**
     * Detailed error information
     * @return errors
     */
    @ApiModelProperty(value = "Detailed error information")
    @Valid 
    public List<ErrorItem> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorItem> errors) {
        this.errors = errors;
    }




    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BadRequestError badRequestError = (BadRequestError) o;
        return Objects.equals(this.message, badRequestError.message) &&
                Objects.equals(this.errors, badRequestError.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            message,
            errors
        );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class BadRequestError {\n");
        
        sb.append("        message: ").append(toIndentedString(message)).append("\n");
        sb.append("        errors: ").append(toIndentedString(errors)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n        ");
    }
}

