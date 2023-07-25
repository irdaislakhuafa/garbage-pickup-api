package com.irdaislakhuafa.garbagepickupapi.controllers.gql.receipt;

import com.irdaislakhuafa.garbagepickupapi.models.entities.Receipt;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.receipt.ReceiptRequest;
import com.irdaislakhuafa.garbagepickupapi.services.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@SchemaMapping(typeName = "ReceiptMutation")
public class ReceiptMutation {
    private final ReceiptService receiptService;

    @SchemaMapping
    public Optional<Receipt> save(@Argument(name = "request") ReceiptRequest request) {
        final var receipt = this.receiptService.fromRequestToEntity(request);
        final var result = this.receiptService.save(receipt);
        return result;
    }
}