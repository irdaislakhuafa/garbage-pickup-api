package com.irdaislakhuafa.garbagepickupapi.controllers.gql.voucher;

import com.irdaislakhuafa.garbagepickupapi.models.entities.Voucher;
import com.irdaislakhuafa.garbagepickupapi.services.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@SchemaMapping(typeName = "VoucherQuery")
public class VoucherQuery {
    private final VoucherService voucherService;

    @SchemaMapping
    public List<Voucher> findAll() {
        final var result = this.voucherService.findAll();
        return result;
    }

    @SchemaMapping
    public Optional<Voucher> findById(@Argument(name = "id") String id) {
        final var result = this.voucherService.findById(id);
        return result;
    }
}