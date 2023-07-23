package com.irdaislakhuafa.garbagepickupapi.controllers.gql.voucher;

import com.irdaislakhuafa.garbagepickupapi.models.entities.Voucher;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.voucher.VoucherRequest;
import com.irdaislakhuafa.garbagepickupapi.services.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
@SchemaMapping(typeName = "VoucherMutation")
@RequiredArgsConstructor
public class VoucherMutation {
    private final VoucherService voucherService;

    @SchemaMapping
    public Optional<Voucher> save(@Argument(name = "request") VoucherRequest request) {
        final var voucher = this.voucherService.fromRequestToEntity(request);
        final var result = this.voucherService.save(voucher);
        return result;
    }
}