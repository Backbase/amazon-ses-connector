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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.*;


/**
 * Recipient
 */
@ApiModel(description = "Recipient")
@javax.annotation.Generated(value = "com.backbase.oss.codegen.java.BoatSpringCodeGen", date = "2021-11-30T10:29:11.075893Z[Etc/UTC]")

public class Recipient 
 {
    @JsonProperty("ref")
    private @Size(max=255)  String ref;

    @JsonProperty("to")
    private List<@Size(max=255) String> to = new ArrayList<>();

    @JsonProperty("from")
    private @Size(max=255)  String from;

    @JsonProperty("contentId")
    private @Size(max=255)  String contentId;

    @JsonProperty("data")
    private Map<String, String> data = new HashMap<>();


    public Recipient ref(String ref) {
        this.ref = ref;
        return this;
    }

    /**
     * Reference number of the recipient
     * @return ref
     */
    @ApiModelProperty(required = true, value = "Reference number of the recipient")
    @NotNull @Size(max=255) 
    public @Size(max=255)  String getRef() {
        return ref;
    }

    public void setRef(@Size(max=255)  String ref) {
        this.ref = ref;
    }


    public Recipient to(List<String> to) {
        this.to = to;
        return this;
    }

    public Recipient addToItem(String toItem) {
        this.to.add(toItem);
        return this;
    }

    /**
     * Get to
     * @return to
     */
    @ApiModelProperty(required = true, value = "")
    @NotNull 
    public List<@Size(max=255) String> getTo() {
        return to;
    }

    public void setTo(List<@Size(max=255) String> to) {
        this.to = to;
    }


    public Recipient from(String from) {
        this.from = from;
        return this;
    }

    /**
     * Sender info
     * @return from
     */
    @ApiModelProperty(value = "Sender info")
    @Size(max=255) 
    public @Size(max=255)  String getFrom() {
        return from;
    }

    public void setFrom(@Size(max=255)  String from) {
        this.from = from;
    }


    public Recipient contentId(String contentId) {
        this.contentId = contentId;
        return this;
    }

    /**
     * Id of the contend for message
     * @return contentId
     */
    @ApiModelProperty(required = true, value = "Id of the contend for message")
    @NotNull @Size(max=255) 
    public @Size(max=255)  String getContentId() {
        return contentId;
    }

    public void setContentId(@Size(max=255)  String contentId) {
        this.contentId = contentId;
    }


    public Recipient data(Map<String, String> data) {
        this.data = data;
        return this;
    }

    public Recipient putDataItem(String key, String dataItem) {
        this.data.put(key, dataItem);
        return this;
    }

    /**
     * A collection of data objects to be used to fill the templates of the messages
     * @return data
     */
    @ApiModelProperty(required = true, value = "A collection of data objects to be used to fill the templates of the messages")
    @NotNull 
    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }




    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Recipient recipient = (Recipient) o;
        return Objects.equals(this.ref, recipient.ref) &&
                Objects.equals(this.to, recipient.to) &&
                Objects.equals(this.from, recipient.from) &&
                Objects.equals(this.contentId, recipient.contentId) &&
                Objects.equals(this.data, recipient.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            ref,
            to,
            from,
            contentId,
            data
        );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Recipient {\n");
        
        sb.append("        ref: ").append(toIndentedString(ref)).append("\n");
        sb.append("        to: ").append(toIndentedString(to)).append("\n");
        sb.append("        from: ").append(toIndentedString(from)).append("\n");
        sb.append("        contentId: ").append(toIndentedString(contentId)).append("\n");
        sb.append("        data: ").append(toIndentedString(data)).append("\n");
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

