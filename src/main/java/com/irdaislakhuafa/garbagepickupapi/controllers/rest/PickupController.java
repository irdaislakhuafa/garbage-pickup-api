package com.irdaislakhuafa.garbagepickupapi.controllers.rest;

import com.irdaislakhuafa.garbagepickupapi.models.entities.Pickup;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.pickup.PickupCheckPriceRequest;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.pickup.PickupRequest;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.pickup.PickupUpdateRequest;
import com.irdaislakhuafa.garbagepickupapi.models.gql.response.PickupCheckPriceResponse;
import com.irdaislakhuafa.garbagepickupapi.models.rest.response.RestResponse;
import com.irdaislakhuafa.garbagepickupapi.services.PickupService;
import com.irdaislakhuafa.garbagepickupapi.services.RestValidationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = {"/api/rest/pickups"})
@RequiredArgsConstructor
public class PickupController {
    private final PickupService pickupService;
    private final RestValidationService restValidationService;

    @Operation(summary = "Used to save or create new request pickup")
    @PostMapping
    public ResponseEntity<RestResponse<Pickup, Map<String, Object>>> save(@RequestBody @Valid PickupRequest request, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(RestResponse.<Pickup, Map<String, Object>>builder()
                            .errors(this.restValidationService.getListErrors(errors))
                            .build());
        }

        final var pickup = this.pickupService.fromRequestToEntity(request);
        final var result = this.pickupService.save(pickup);
        return ResponseEntity.ok(RestResponse.<Pickup, Map<String, Object>>builder()
                .data(result.get())
                .build());
    }

    @Operation(summary = "Used to update pickup")
    @PutMapping
    public ResponseEntity<RestResponse<Pickup, Map<String, Object>>> update(@RequestBody @Valid PickupUpdateRequest request, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(RestResponse.<Pickup, Map<String, Object>>builder()
                            .errors(this.restValidationService.getListErrors(errors))
                            .build());
        }

        final var pickup = this.pickupService.fromUpdateRequestToEntity(request);
        final var result = this.pickupService.update(pickup);
        return ResponseEntity.ok(RestResponse.<Pickup, Map<String, Object>>builder()
                .data(result.get())
                .build());
    }


    @Operation(summary = "Used to check price of request pickup with weight and lat log as parameter")
    @PostMapping(value = {"/checkPrice"})
    public ResponseEntity<RestResponse<PickupCheckPriceResponse, Map<String, Object>>> checkPrice(@RequestBody @Valid PickupCheckPriceRequest request, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(RestResponse.<PickupCheckPriceResponse, Map<String, Object>>builder()
                            .errors(this.restValidationService.getListErrors(errors))
                            .build());
        }

        final var result = this.pickupService.checkPrice(request);
        return ResponseEntity.ok(RestResponse.<PickupCheckPriceResponse, Map<String, Object>>builder()
                .data(result)
                .build());
    }
}