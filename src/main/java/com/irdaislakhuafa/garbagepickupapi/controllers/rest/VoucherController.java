package com.irdaislakhuafa.garbagepickupapi.controllers.rest;

import com.irdaislakhuafa.garbagepickupapi.models.entities.Voucher;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.voucher.VoucherRequest;
import com.irdaislakhuafa.garbagepickupapi.models.rest.response.RestResponse;
import com.irdaislakhuafa.garbagepickupapi.services.RestValidationService;
import com.irdaislakhuafa.garbagepickupapi.services.VoucherService;
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
@RequestMapping(value = {"/api/rest/vouchers"})
@RequiredArgsConstructor
public class VoucherController {
    private final VoucherService voucherService;
    private final RestValidationService restValidationService;

    @Operation(summary = "Used to save or create new voucher")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<RestResponse<Voucher, Map<String, Object>>> save(@ModelAttribute @Valid VoucherRequest request, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(RestResponse.<Voucher, Map<String, Object>>builder()
                            .errors(this.restValidationService.getListErrors(errors))
                            .build());
        }

        final var voucher = this.voucherService.fromRequestToEntity(request);
        final var result = this.voucherService.save(voucher);
        return ResponseEntity.ok(RestResponse.<Voucher, Map<String, Object>>builder()
                .data(result.get())
                .build());
    }

    @Operation(summary = "Used to get list of voucher")
    @GetMapping
    public ResponseEntity<RestResponse<List<Voucher>, Map<String, Object>>> findAll() {
        final var results = this.voucherService.findAll();
        return ResponseEntity.ok(RestResponse.<List<Voucher>, Map<String, Object>>builder()
                .data(results)
                .build());
    }
}