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
import com.backbase.email.integration.rest.spec.v2.email.Attachment;
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
 * EmailPostRequestBody
 */
@javax.annotation.Generated(value = "com.backbase.oss.codegen.java.BoatSpringCodeGen", date = "2021-12-01T00:27:49.044203Z[Etc/UTC]")

public class EmailPostRequestBody 
 {
    @JsonProperty("to")
    private List<String> to = new ArrayList<>();

    @JsonProperty("cc")
    private List<String> cc = null;

    @JsonProperty("bcc")
    private List<String> bcc = null;

    @JsonProperty("from")
    private  String from;

    @JsonProperty("body")
    private  String body;

    @JsonProperty("subject")
    private  String subject;

    @JsonProperty("important")
    private  Boolean important = false;

    @JsonProperty("attachments")
    private List<Attachment> attachments = null;

    @JsonProperty("additions")
    private Map<String, String> additions = null;


    public EmailPostRequestBody to(List<String> to) {
        this.to = to;
        return this;
    }

    public EmailPostRequestBody addToItem(String toItem) {
        this.to.add(toItem);
        return this;
    }

    /**
     * List of recipients that should be im 'copy'
     * @return to
     */
    @ApiModelProperty(required = true, value = "List of recipients that should be im 'copy'")
    @NotNull 
    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }


    public EmailPostRequestBody cc(List<String> cc) {
        this.cc = cc;
        return this;
    }

    public EmailPostRequestBody addCcItem(String ccItem) {
        if (this.cc == null) {
            this.cc = new ArrayList<>();
        }
        this.cc.add(ccItem);
        return this;
    }

    /**
     * List of recipients that should be im 'copy'
     * @return cc
     */
    @ApiModelProperty(value = "List of recipients that should be im 'copy'")
    
    public List<String> getCc() {
        return cc;
    }

    public void setCc(List<String> cc) {
        this.cc = cc;
    }


    public EmailPostRequestBody bcc(List<String> bcc) {
        this.bcc = bcc;
        return this;
    }

    public EmailPostRequestBody addBccItem(String bccItem) {
        if (this.bcc == null) {
            this.bcc = new ArrayList<>();
        }
        this.bcc.add(bccItem);
        return this;
    }

    /**
     * List of recipients that should be im 'blind copy'
     * @return bcc
     */
    @ApiModelProperty(value = "List of recipients that should be im 'blind copy'")
    
    public List<String> getBcc() {
        return bcc;
    }

    public void setBcc(List<String> bcc) {
        this.bcc = bcc;
    }


    public EmailPostRequestBody from(String from) {
        this.from = from;
        return this;
    }

    /**
     * Email address of email sender
     * @return from
     */
    @ApiModelProperty(value = "Email address of email sender")
    
    public  String getFrom() {
        return from;
    }

    public void setFrom( String from) {
        this.from = from;
    }


    public EmailPostRequestBody body(String body) {
        this.body = body;
        return this;
    }

    /**
     * Base64 encoded email body
     * @return body
     */
    @ApiModelProperty(required = true, value = "Base64 encoded email body")
    @NotNull 
    public  String getBody() {
        return body;
    }

    public void setBody( String body) {
        this.body = body;
    }


    public EmailPostRequestBody subject(String subject) {
        this.subject = subject;
        return this;
    }

    /**
     * Email subject
     * @return subject
     */
    @ApiModelProperty(value = "Email subject")
    
    public  String getSubject() {
        return subject;
    }

    public void setSubject( String subject) {
        this.subject = subject;
    }


    public EmailPostRequestBody important(Boolean important) {
        this.important = important;
        return this;
    }

    /**
     * Email importance flag
     * @return important
     */
    @ApiModelProperty(value = "Email importance flag")
    
    public  Boolean getImportant() {
        return important;
    }

    public void setImportant( Boolean important) {
        this.important = important;
    }


    public EmailPostRequestBody attachments(List<Attachment> attachments) {
        this.attachments = attachments;
        return this;
    }

    public EmailPostRequestBody addAttachmentsItem(Attachment attachmentsItem) {
        if (this.attachments == null) {
            this.attachments = new ArrayList<>();
        }
        this.attachments.add(attachmentsItem);
        return this;
    }

    /**
     * List of email attachments
     * @return attachments
     */
    @ApiModelProperty(value = "List of email attachments")
    @Valid 
    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }


    public EmailPostRequestBody additions(Map<String, String> additions) {
        this.additions = additions;
        return this;
    }

    public EmailPostRequestBody putAdditionsItem(String key, String additionsItem) {
        if (this.additions == null) {
            this.additions = new HashMap<>();
        }
        this.additions.put(key, additionsItem);
        return this;
    }

    /**
     * Additional properties
     * @return additions
     */
    @ApiModelProperty(value = "Additional properties")
    
    public Map<String, String> getAdditions() {
        return additions;
    }

    public void setAdditions(Map<String, String> additions) {
        this.additions = additions;
    }




    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EmailPostRequestBody emailPostRequestBody = (EmailPostRequestBody) o;
        return Objects.equals(this.to, emailPostRequestBody.to) &&
                Objects.equals(this.cc, emailPostRequestBody.cc) &&
                Objects.equals(this.bcc, emailPostRequestBody.bcc) &&
                Objects.equals(this.from, emailPostRequestBody.from) &&
                Objects.equals(this.body, emailPostRequestBody.body) &&
                Objects.equals(this.subject, emailPostRequestBody.subject) &&
                Objects.equals(this.important, emailPostRequestBody.important) &&
                Objects.equals(this.attachments, emailPostRequestBody.attachments) &&
                Objects.equals(this.additions, emailPostRequestBody.additions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            to,
            cc,
            bcc,
            from,
            body,
            subject,
            important,
            attachments,
            additions
        );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class EmailPostRequestBody {\n");
        
        sb.append("        to: ").append(toIndentedString(to)).append("\n");
        sb.append("        cc: ").append(toIndentedString(cc)).append("\n");
        sb.append("        bcc: ").append(toIndentedString(bcc)).append("\n");
        sb.append("        from: ").append(toIndentedString(from)).append("\n");
        sb.append("        body: ").append(toIndentedString(body)).append("\n");
        sb.append("        subject: ").append(toIndentedString(subject)).append("\n");
        sb.append("        important: ").append(toIndentedString(important)).append("\n");
        sb.append("        attachments: ").append(toIndentedString(attachments)).append("\n");
        sb.append("        additions: ").append(toIndentedString(additions)).append("\n");
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

