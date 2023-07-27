package com.irdaislakhuafa.garbagepickupapi.controllers.rest;

import com.irdaislakhuafa.garbagepickupapi.models.entities.UserVoucher;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.uservoucher.UserVoucherExchangeRequest;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.uservoucher.UserVoucherFindAllByUserIdAndStatus;
import com.irdaislakhuafa.garbagepickupapi.models.rest.response.RestResponse;
import com.irdaislakhuafa.garbagepickupapi.services.RestValidationService;
import com.irdaislakhuafa.garbagepickupapi.services.UserVoucherService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = {"/api/rest/userVouchers"})
@RequiredArgsConstructor
public class UserVoucherController {
    private final UserVoucherService userVoucherService;
    private final RestValidationService restValidationService;

    @Operation(summary = "Used to find all available voucher for user", description = "Allowed valus for field 'statuses' is [USED, AVAILABLE, CLAIMED]")
    @PostMapping(value = {"/vouchers"})
    public ResponseEntity<RestResponse<List<UserVoucher>, Map<String, Object>>> findAllByUserIdAndStatus(@RequestBody @Valid UserVoucherFindAllByUserIdAndStatus request, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(RestResponse.<List<UserVoucher>, Map<String, Object>>builder()
                            .errors(this.restValidationService.getListErrors(errors))
                            .build());
        }

        final var results = this.userVoucherService.findAll(request);
        return ResponseEntity.ok(RestResponse.<List<UserVoucher>, Map<String, Object>>builder()
                .data(results)
                .build());
    }


    @Operation(summary = "Used to exchange user point with available voucher for user")
    @PostMapping(value = {"/exchange"})
    public ResponseEntity<RestResponse<List<UserVoucher>, Map<String, Object>>> exchange(@RequestBody @Valid UserVoucherExchangeRequest request, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(RestResponse.<List<UserVoucher>, Map<String, Object>>builder()
                            .errors(this.restValidationService.getListErrors(errors))
                            .build());
        }

        final var results = this.userVoucherService.exchange(request);
        return ResponseEntity.ok(RestResponse.<List<UserVoucher>, Map<String, Object>>builder()
                .data(results)
                .build());
    }
}