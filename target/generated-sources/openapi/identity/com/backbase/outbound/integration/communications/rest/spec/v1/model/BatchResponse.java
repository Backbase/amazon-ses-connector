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
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Content;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Recipient;
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
 * Batch of communications
 */
@ApiModel(description = "Batch of communications")
@javax.annotation.Generated(value = "com.backbase.oss.codegen.java.BoatSpringCodeGen", date = "2021-11-30T10:29:11.075893Z[Etc/UTC]")

public class BatchResponse 
 {
    @JsonProperty("recipients")
    private List<Recipient> recipients = new ArrayList<>();

    @JsonProperty("content")
    private List<Content> content = new ArrayList<>();


    public BatchResponse recipients(List<Recipient> recipients) {
        this.recipients = recipients;
        return this;
    }

    public BatchResponse addRecipientsItem(Recipient recipientsItem) {
        this.recipients.add(recipientsItem);
        return this;
    }

    /**
     * Get recipients
     * @return recipients
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull @Valid 
    public List<Recipient> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<Recipient> recipients) {
        this.recipients = recipients;
    }


    public BatchResponse content(List<Content> content) {
        this.content = content;
        return this;
    }

    public BatchResponse addContentItem(Content contentItem) {
        this.content.add(contentItem);
        return this;
    }

    /**
     * Get content
     * @return content
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull @Valid 
    public List<Content> getContent() {
        return content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }




    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BatchResponse batchResponse = (BatchResponse) o;
        return Objects.equals(this.recipients, batchResponse.recipients) &&
                Objects.equals(this.content, batchResponse.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            recipients,
            content
        );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class BatchResponse {\n");
        
        sb.append("        recipients: ").append(toIndentedString(recipients)).append("\n");
        sb.append("        content: ").append(toIndentedString(content)).append("\n");
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

