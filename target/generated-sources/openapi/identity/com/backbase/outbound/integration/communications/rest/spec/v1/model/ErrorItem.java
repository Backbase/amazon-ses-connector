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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.*;


/**
 * ErrorItem
 */
@javax.annotation.Generated(value = "com.backbase.oss.codegen.java.BoatSpringCodeGen", date = "2021-11-30T08:24:12.125199Z[Etc/UTC]")

public class ErrorItem 
 {
    @JsonProperty("message")
    private  String message;

    @JsonProperty("key")
    private  String key;

    @JsonProperty("context")
    private Map<String, String> context = null;


    public ErrorItem message(String message) {
        this.message = message;
        return this;
    }

    /**
     * Any further information.
     * @return message
     */
    @ApiModelProperty(value = "Any further information.")
    
    public  String getMessage() {
        return message;
    }

    public void setMessage( String message) {
        this.message = message;
    }


    public ErrorItem key(String key) {
        this.key = key;
        return this;
    }

    /**
     * {capability-name}.api.{api-key-name}. For generated validation errors this is the path in the document the error resolves to. e.g. object name + '.' + field
     * @return key
     */
    @ApiModelProperty(value = "{capability-name}.api.{api-key-name}. For generated validation errors this is the path in the document the error resolves to. e.g. object name + '.' + field")
    
    public  String getKey() {
        return key;
    }

    public void setKey( String key) {
        this.key = key;
    }


    public ErrorItem context(Map<String, String> context) {
        this.context = context;
        return this;
    }

    public ErrorItem putContextItem(String key, String contextItem) {
        if (this.context == null) {
            this.context = new HashMap<>();
        }
        this.context.put(key, contextItem);
        return this;
    }

    /**
     * Context can be anything used to construct localised messages.
     * @return context
     */
    @ApiModelProperty(value = "Context can be anything used to construct localised messages.")
    
    public Map<String, String> getContext() {
        return context;
    }

    public void setContext(Map<String, String> context) {
        this.context = context;
    }




    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ErrorItem errorItem = (ErrorItem) o;
        return Objects.equals(this.message, errorItem.message) &&
                Objects.equals(this.key, errorItem.key) &&
                Objects.equals(this.context, errorItem.context);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            message,
            key,
            context
        );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ErrorItem {\n");
        
        sb.append("        message: ").append(toIndentedString(message)).append("\n");
        sb.append("        key: ").append(toIndentedString(key)).append("\n");
        sb.append("        context: ").append(toIndentedString(context)).append("\n");
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

