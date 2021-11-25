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
 * Content of the message
 */
@ApiModel(description = "Content of the message")
@javax.annotation.Generated(value = "com.backbase.oss.codegen.java.BoatSpringCodeGen", date = "2021-11-25T13:00:10.618502Z[Etc/UTC]")

public class Content 
 {
    @JsonProperty("contentId")
    private @Size(max=255)  String contentId;

    @JsonProperty("body")
    private  String body;

    @JsonProperty("title")
    private  String title;


    public Content contentId(String contentId) {
        this.contentId = contentId;
        return this;
    }

    /**
     * Id of the content
     * @return contentId
     */
    @ApiModelProperty(required = true, value = "Id of the content")
    @NotNull @Size(max=255) 
    public @Size(max=255)  String getContentId() {
        return contentId;
    }

    public void setContentId(@Size(max=255)  String contentId) {
        this.contentId = contentId;
    }


    public Content body(String body) {
        this.body = body;
        return this;
    }

    /**
     * Body of the message that can be template like
     * @return body
     */
    @ApiModelProperty(required = true, value = "Body of the message that can be template like")
    @NotNull 
    public  String getBody() {
        return body;
    }

    public void setBody( String body) {
        this.body = body;
    }


    public Content title(String title) {
        this.title = title;
        return this;
    }

    /**
     * A title for the message
     * @return title
     */
    @ApiModelProperty(value = "A title for the message")
    
    public  String getTitle() {
        return title;
    }

    public void setTitle( String title) {
        this.title = title;
    }




    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Content content = (Content) o;
        return Objects.equals(this.contentId, content.contentId) &&
                Objects.equals(this.body, content.body) &&
                Objects.equals(this.title, content.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            contentId,
            body,
            title
        );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Content {\n");
        
        sb.append("        contentId: ").append(toIndentedString(contentId)).append("\n");
        sb.append("        body: ").append(toIndentedString(body)).append("\n");
        sb.append("        title: ").append(toIndentedString(title)).append("\n");
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

