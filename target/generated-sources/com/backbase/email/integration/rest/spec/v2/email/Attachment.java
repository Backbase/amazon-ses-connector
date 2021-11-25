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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.*;


/**
 * Email attachment
 */
@ApiModel(description = "Email attachment")
@javax.annotation.Generated(value = "com.backbase.oss.codegen.java.BoatSpringCodeGen", date = "2021-11-25T13:00:10.138157Z[Etc/UTC]")

public class Attachment 
 {
    @JsonProperty("fileName")
    private  String fileName;

    @JsonProperty("content")
    private  String content;

    @JsonProperty("additions")
    private Map<String, String> additions = null;


    public Attachment fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    /**
     * Name of attached file
     * @return fileName
     */
    @ApiModelProperty(value = "Name of attached file")
    
    public  String getFileName() {
        return fileName;
    }

    public void setFileName( String fileName) {
        this.fileName = fileName;
    }


    public Attachment content(String content) {
        this.content = content;
        return this;
    }

    /**
     * Base64 encoded attachment
     * @return content
     */
    @ApiModelProperty(required = true, value = "Base64 encoded attachment")
    @NotNull 
    public  String getContent() {
        return content;
    }

    public void setContent( String content) {
        this.content = content;
    }


    public Attachment additions(Map<String, String> additions) {
        this.additions = additions;
        return this;
    }

    public Attachment putAdditionsItem(String key, String additionsItem) {
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
        Attachment attachment = (Attachment) o;
        return Objects.equals(this.fileName, attachment.fileName) &&
                Objects.equals(this.content, attachment.content) &&
                Objects.equals(this.additions, attachment.additions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            fileName,
            content,
            additions
        );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Attachment {\n");
        
        sb.append("        fileName: ").append(toIndentedString(fileName)).append("\n");
        sb.append("        content: ").append(toIndentedString(content)).append("\n");
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

