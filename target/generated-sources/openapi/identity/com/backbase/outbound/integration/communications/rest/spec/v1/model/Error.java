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
package com.backbase.outbound.integration.communications.rest.spec.v1.model;

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
 * Error object for unsuccessful communication sent
 */
@ApiModel(description = "Error object for unsuccessful communication sent")
@javax.annotation.Generated(value = "com.backbase.oss.codegen.java.BoatSpringCodeGen", date = "2021-11-30T08:24:12.125199Z[Etc/UTC]")

public class Error 
 {
    @JsonProperty("code")
    private @Size(max=255)  String code;

    @JsonProperty("message")
    private  String message;


    public Error code(String code) {
        this.code = code;
        return this;
    }

    /**
     * Code of a error
     * @return code
     */
    @ApiModelProperty(value = "Code of a error")
    @Size(max=255) 
    public @Size(max=255)  String getCode() {
        return code;
    }

    public void setCode(@Size(max=255)  String code) {
        this.code = code;
    }


    public Error message(String message) {
        this.message = message;
        return this;
    }

    /**
     * Message of the error
     * @return message
     */
    @ApiModelProperty(value = "Message of the error")
    
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
        Error error = (Error) o;
        return Objects.equals(this.code, error.code) &&
                Objects.equals(this.message, error.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            code,
            message
        );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Error {\n");
        
        sb.append("        code: ").append(toIndentedString(code)).append("\n");
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

