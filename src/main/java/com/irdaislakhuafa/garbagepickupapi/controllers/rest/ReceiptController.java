package com.irdaislakhuafa.garbagepickupapi.controllers.rest;

import com.irdaislakhuafa.garbagepickupapi.models.entities.Receipt;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.receipt.ReceiptRequest;
import com.irdaislakhuafa.garbagepickupapi.models.rest.response.RestResponse;
import com.irdaislakhuafa.garbagepickupapi.services.ReceiptService;
import com.irdaislakhuafa.garbagepickupapi.services.RestValidationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = {"/api/rest/receipts"})
@RequiredArgsConstructor
@Slf4j
public class ReceiptController {
    private final ReceiptService receiptService;
    private RestValidationService restValidationService;

    @Operation(summary = "Used to find all receipt")
    @GetMapping
    public ResponseEntity<RestResponse<List<Receipt>, Map<String, Object>>> findAll() {
        final var results = this.receiptService.findAll();
        return ResponseEntity.ok(RestResponse.<List<Receipt>, Map<String, Object>>builder()
                .data(results)
                .build());
    }

    @Operation(summary = "Used to save or create new receipt")
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RestResponse<Receipt, Map<String, Object>>> save(@RequestBody @Valid ReceiptRequest request, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(RestResponse.<Receipt, Map<String, Object>>builder()
                            .errors(this.restValidationService.getListErrors(errors))
                            .build());
        }

        final var receipt = this.receiptService.fromRequestToEntity(request);
        final var result = this.receiptService.save(receipt);
        return ResponseEntity.ok(RestResponse.<Receipt, Map<String, Object>>builder()
                .data(result.get())
                .build());
    }

    @Operation(summary = "Used to find receipt bu receipt id")
    @GetMapping(value = {"/{id}"})
    public ResponseEntity<RestResponse<Receipt, Map<String, Object>>> findById(@PathVariable(name = "id") String id) {
        final var result = this.receiptService.findById(id);
        return ResponseEntity.ok(RestResponse.<Receipt, Map<String, Object>>builder()
                .data(result.get())
                .build());
    }

    @Operation(summary = "Used to find receipt by pickup id")
    @GetMapping(value = {"/pickups/{pickupId}"})
    public ResponseEntity<RestResponse<Receipt, Map<String, Object>>> findByPickupId(@PathVariable(name = "pickupId") String pickupId) {
        final var result = this.receiptService.findByPickupId(pickupId);
        return ResponseEntity.ok(RestResponse.<Receipt, Map<String, Object>>builder()
                .data(result.get())
                .build());
    }

    @GetMapping(value = {"/pickups/all/{pickupId}"})
    public ResponseEntity<RestResponse<List<Receipt>, Map<String, Object>>> findAllByPickupId(@PathVariable(name = "pickupId") String pickupId) {
        final var results = this.receiptService.findAllByPickupId(pickupId);
        return ResponseEntity.ok(RestResponse.<List<Receipt>, Map<String, Object>>builder()
                .data(results)
                .build());
    }
}