package com.irdaislakhuafa.garbagepickupapi.controllers.rest;

import com.irdaislakhuafa.garbagepickupapi.models.entities.TrashType;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.trashtype.TrashTypeRequest;
import com.irdaislakhuafa.garbagepickupapi.models.rest.response.RestResponse;
import com.irdaislakhuafa.garbagepickupapi.services.RestValidationService;
import com.irdaislakhuafa.garbagepickupapi.services.TrashTypeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = {"/api/rest/trashTypes"})
@RequiredArgsConstructor
public class TrashTypeController {
    private final TrashTypeService trashTypeService;
    private final RestValidationService restValidationService;

    @Operation(summary = "Used to save or create new trash type")
    @PostMapping
    public ResponseEntity<RestResponse<TrashType, Map<String, Object>>> save(@RequestBody @Valid TrashTypeRequest request, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(RestResponse.<TrashType, Map<String, Object>>builder()
                            .errors(this.restValidationService.getListErrors(errors))
                            .build());
        }
        final var trashType = this.trashTypeService.fromRequestToEntity(request);
        final var result = this.trashTypeService.save(trashType);
        return ResponseEntity.ok(RestResponse.<TrashType, Map<String, Object>>builder()
                .data(result.get())
                .build());
    }
}