package com.irdaislakhuafa.garbagepickupapi.controllers.rest;

import com.irdaislakhuafa.garbagepickupapi.models.entities.PickupActivity;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.pickupactivity.PickupActivityRequest;
import com.irdaislakhuafa.garbagepickupapi.models.rest.response.RestResponse;
import com.irdaislakhuafa.garbagepickupapi.services.PickupActivityService;
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
@RequestMapping(value = {"/api/rest/pickupActivity"})
@RequiredArgsConstructor
public class PickupActivityController {
    private final RestValidationService restValidationService;
    private final PickupActivityService pickupActivityService;

    @Operation(summary = "Used to save or create new pickup activity")
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<PickupActivity, Map<String, Object>>> save(@RequestBody @Valid PickupActivityRequest request, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(RestResponse.<PickupActivity, Map<String, Object>>builder()
                            .errors(this.restValidationService.getListErrors(errors))
                            .build());
        }

        final var pickupActivity = this.pickupActivityService.fromRequestToEntity(request);
        final var result = this.pickupActivityService.save(pickupActivity);
        return ResponseEntity.ok(RestResponse.<PickupActivity, Map<String, Object>>builder()
                .data(result.get())
                .build());
    }

    @Operation(summary = "Used to find list of pickup activity by pickup id")
    @GetMapping(value = {"/pickup/{pickupId}"})
    public ResponseEntity<RestResponse<List<PickupActivity>, Map<String, Object>>> findAllByPickupId(@PathVariable(name = "pickupId") String pickupId) {
        final var results = this.pickupActivityService.findAllByPickupId(pickupId);
        return ResponseEntity.ok(RestResponse.<List<PickupActivity>, Map<String, Object>>builder()
                .data(results)
                .build());
    }

    @Operation(summary = "Used to find list of pickup activity by pickup resi")
    @GetMapping(value = {"/pickup/resi/{pickupResi}"})
    public ResponseEntity<RestResponse<List<PickupActivity>, Map<String, Object>>> findAllByPickupResi(@PathVariable(name = "pickupResi") String pickupResi) {
        final var results = this.pickupActivityService.findAllByPickupResi(pickupResi);
        return ResponseEntity.ok(RestResponse.<List<PickupActivity>, Map<String, Object>>builder()
                .data(results)
                .build());
    }
}