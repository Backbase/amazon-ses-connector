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
import com.backbase.outbound.integration.communications.rest.spec.v1.model.Status;
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
 * Response object for batch send request, that contains info about status of message
 */
@ApiModel(description = "Response object for batch send request, that contains info about status of message")
@javax.annotation.Generated(value = "com.backbase.oss.codegen.java.BoatSpringCodeGen", date = "2021-12-01T00:27:49.450745Z[Etc/UTC]")

public class BatchPostResponseBody 
 {
    @JsonProperty("communicationBatchId")
    private @Size(max=255)  String communicationBatchId;

    @JsonProperty("statuses")
    private List<Status> statuses = null;


    public BatchPostResponseBody communicationBatchId(String communicationBatchId) {
        this.communicationBatchId = communicationBatchId;
        return this;
    }

    /**
     * Id of the batch
     * @return communicationBatchId
     */
    @ApiModelProperty(required = true, value = "Id of the batch")
    @NotNull @Size(max=255) 
    public @Size(max=255)  String getCommunicationBatchId() {
        return communicationBatchId;
    }

    public void setCommunicationBatchId(@Size(max=255)  String communicationBatchId) {
        this.communicationBatchId = communicationBatchId;
    }


    public BatchPostResponseBody statuses(List<Status> statuses) {
        this.statuses = statuses;
        return this;
    }

    public BatchPostResponseBody addStatusesItem(Status statusesItem) {
        if (this.statuses == null) {
            this.statuses = new ArrayList<>();
        }
        this.statuses.add(statusesItem);
        return this;
    }

    /**
     * Get statuses
     * @return statuses
     */
    @ApiModelProperty(value = "")
    @Valid 
    public List<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }




    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BatchPostResponseBody batchPostResponseBody = (BatchPostResponseBody) o;
        return Objects.equals(this.communicationBatchId, batchPostResponseBody.communicationBatchId) &&
                Objects.equals(this.statuses, batchPostResponseBody.statuses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            communicationBatchId,
            statuses
        );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class BatchPostResponseBody {\n");
        
        sb.append("        communicationBatchId: ").append(toIndentedString(communicationBatchId)).append("\n");
        sb.append("        statuses: ").append(toIndentedString(statuses)).append("\n");
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

