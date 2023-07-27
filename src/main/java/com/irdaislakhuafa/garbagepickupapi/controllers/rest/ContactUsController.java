package com.irdaislakhuafa.garbagepickupapi.controllers.rest;

import com.irdaislakhuafa.garbagepickupapi.models.entities.ContactUs;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.contactus.ContactUsRequest;
import com.irdaislakhuafa.garbagepickupapi.models.rest.response.RestResponse;
import com.irdaislakhuafa.garbagepickupapi.services.ContactUsService;
import com.irdaislakhuafa.garbagepickupapi.services.RestValidationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = {"/api/rest/contactUs"})
@RequiredArgsConstructor
public class ContactUsController {
    private final ContactUsService contactUsService;
    private final RestValidationService restValidationService;

    @Operation(summary = "Used to save new contact us")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<RestResponse<ContactUs, Map<String, Object>>> save(@ModelAttribute @Valid ContactUsRequest request, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(RestResponse.<ContactUs, Map<String, Object>>builder()
                            .errors(this.restValidationService.getListErrors(errors))
                            .build());
        }

        final var contactUs = this.contactUsService.fromRequestToEntity(request);
        final var result = this.contactUsService.save(contactUs);
        return ResponseEntity.ok(RestResponse.<ContactUs, Map<String, Object>>builder()
                .data(result.get())
                .build());
    }

    @Operation(summary = "Used to find list of contact us")
    @GetMapping
    public ResponseEntity<RestResponse<List<ContactUs>, Map<String, Object>>> findAll() {
        final var results = this.contactUsService.findAll();
        return ResponseEntity.ok(RestResponse.<List<ContactUs>, Map<String, Object>>builder()
                .data(results)
                .build());
    }
}