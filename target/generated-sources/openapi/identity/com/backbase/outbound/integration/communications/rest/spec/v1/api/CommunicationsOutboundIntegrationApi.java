/*
Boat Generator configuration:
    useBeanValidation: true
    useOptional: false
    addServletRequest: true
    addBindingResult: false
    useLombokAnnotations: false
    openApiNullable: true
    useSetForUniqueItems: false
    useWithModifiers: false
*/
package com.backbase.outbound.integration.communications.rest.spec.v1.api;

import com.backbase.outbound.integration.communications.rest.spec.v1.model.BadRequestError;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.BatchPostResponseBody;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.BatchResponse;
import java.util.Set;
import java.util.LinkedHashSet;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@javax.annotation.Generated(value = "com.backbase.oss.codegen.java.BoatSpringCodeGen", date = "2021-11-30T10:29:11.075893Z[Etc/UTC]")
@Validated
@Api(value = "CommunicationsOutboundIntegration", description = "the CommunicationsOutboundIntegration API")
public interface CommunicationsOutboundIntegrationApi {

    /**
     * POST /service-api/v1/communications/batches : Send batch.
     * Send batch
     *
     * @param batchResponse Send batch (required)
     * @return Sent(offline) (status code 202)
     *         or BadRequest (status code 400)
     */
    @ApiOperation(value = "Send batch.", nickname = "postBatches", notes = "Send batch", response = BatchPostResponseBody.class, tags={ "communications-outbound-integration", })
    @ApiResponses(value = { 
        @ApiResponse(code = 202, message = "Sent(offline)", response = BatchPostResponseBody.class),
        @ApiResponse(code = 400, message = "BadRequest", response = BadRequestError.class) })
    @RequestMapping(value = "/service-api/v1/communications/batches",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<BatchPostResponseBody> postBatches(
            @ApiParam(value = "Send batch" ,required=true )
            @Valid @NotNull
            @RequestBody BatchResponse batchResponse
,
        HttpServletRequest httpServletRequest
    );

}
