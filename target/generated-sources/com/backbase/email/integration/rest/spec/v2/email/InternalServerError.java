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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.*;


/**
 * Represents HTTP 500 Internal Server Error
 */
@ApiModel(description = "Represents HTTP 500 Internal Server Error")
@javax.annotation.Generated(value = "com.backbase.oss.codegen.java.BoatSpringCodeGen", date = "2021-11-30T08:24:11.588171Z[Etc/UTC]")

public class InternalServerError 
 {
    @JsonProperty("message")
    private  String message;


    public InternalServerError message(String message) {
        this.message = message;
        return this;
    }

    /**
     * Further Information
     * @return message
     */
    @ApiModelProperty(value = "Further Information")
    
    public  String getMessage() {
        return message;
    }

    public void setMessage( String message) {
        this.message = message;
    }




    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InternalServerError internalServerError = (InternalServerError) o;
        return Objects.equals(this.message, internalServerError.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            message
        );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class InternalServerError {\n");
        
        sb.append("        message: ").append(toIndentedString(message)).append("\n");
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

