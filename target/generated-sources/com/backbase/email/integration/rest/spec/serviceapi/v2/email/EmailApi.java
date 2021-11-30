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
package com.backbase.email.integration.rest.spec.serviceapi.v2.email;

import com.backbase.email.integration.rest.spec.v2.email.BadRequestError;
import com.backbase.email.integration.rest.spec.v2.email.EmailPostRequestBody;
import com.backbase.email.integration.rest.spec.v2.email.InternalServerError;
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
@javax.annotation.Generated(value = "com.backbase.oss.codegen.java.BoatSpringCodeGen", date = "2021-11-30T10:29:10.600795Z[Etc/UTC]")
@Validated
@Api(value = "Email", description = "the Email API")
public interface EmailApi {

    /**
     * POST /service-api/v2/email : Endpoint for sending email message.
     * Endpoint for sending email message
     *
     * @param emailPostRequestBody Endpoint for sending email message (optional)
     * @return Created (status code 201)
     *         or BadRequest (status code 400)
     *         or InternalServerError (status code 500)
     */
    @ApiOperation(value = "Endpoint for sending email message.", nickname = "postEmail", notes = "Endpoint for sending email message", tags={ "email", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 400, message = "BadRequest", response = BadRequestError.class),
        @ApiResponse(code = 500, message = "InternalServerError", response = InternalServerError.class) })
    @RequestMapping(value = "/service-api/v2/email",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> postEmail(
            @ApiParam(value = "Endpoint for sending email message"  )
            @Valid
            @RequestBody(required = false) EmailPostRequestBody emailPostRequestBody
,
        HttpServletRequest httpServletRequest
    );

}
