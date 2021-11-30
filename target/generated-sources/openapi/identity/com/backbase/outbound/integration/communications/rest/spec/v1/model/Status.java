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
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Error;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.*;


/**
 * Status of the batch
 */
@ApiModel(description = "Status of the batch")
@javax.annotation.Generated(value = "com.backbase.oss.codegen.java.BoatSpringCodeGen", date = "2021-11-30T08:24:12.125199Z[Etc/UTC]")

public class Status 
 {
    @JsonProperty("ref")
    private @Size(max=255)  String ref;

    @JsonProperty("status")
    private @Size(max=255)  String status;

    @JsonProperty("error")
    private  Error error;


    public Status ref(String ref) {
        this.ref = ref;
        return this;
    }

    /**
     * Reference number of the status
     * @return ref
     */
    @ApiModelProperty(required = true, value = "Reference number of the status")
    @NotNull @Size(max=255) 
    public @Size(max=255)  String getRef() {
        return ref;
    }

    public void setRef(@Size(max=255)  String ref) {
        this.ref = ref;
    }


    public Status status(String status) {
        this.status = status;
        return this;
    }

    /**
     * String representation of status of the batch
     * @return status
     */
    @ApiModelProperty(required = true, value = "String representation of status of the batch")
    @NotNull @Size(max=255) 
    public @Size(max=255)  String getStatus() {
        return status;
    }

    public void setStatus(@Size(max=255)  String status) {
        this.status = status;
    }


    public Status error(Error error) {
        this.error = error;
        return this;
    }

    /**
     * Get error
     * @return error
     */
    @ApiModelProperty(value = "")
    @Valid 
    public  Error getError() {
        return error;
    }

    public void setError( Error error) {
        this.error = error;
    }




    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Status status = (Status) o;
        return Objects.equals(this.ref, status.ref) &&
                Objects.equals(this.status, status.status) &&
                Objects.equals(this.error, status.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            ref,
            status,
            error
        );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Status {\n");
        
        sb.append("        ref: ").append(toIndentedString(ref)).append("\n");
        sb.append("        status: ").append(toIndentedString(status)).append("\n");
        sb.append("        error: ").append(toIndentedString(error)).append("\n");
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

