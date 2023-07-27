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
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @Operation(summary = "Used to find all trash type")
    @GetMapping
    public ResponseEntity<RestResponse<List<TrashType>, Map<String, Object>>> findAll() {
        final var results = this.trashTypeService.findAll();
        return ResponseEntity.ok(RestResponse.<List<TrashType>, Map<String, Object>>builder()
                .data(results)
                .build());
    }

    @Operation(summary = "Used to find trash type by id")
    @GetMapping(value = {"/{id}"})
    public ResponseEntity<RestResponse<TrashType, Map<String, Object>>> findById(@PathVariable(name = "id") String id) {
        final var result = this.trashTypeService.findById(id);
        return ResponseEntity.ok(RestResponse.<TrashType, Map<String, Object>>builder()
                .data(result.get())
                .build());
    }
}