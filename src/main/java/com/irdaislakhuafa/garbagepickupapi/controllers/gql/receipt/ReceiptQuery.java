package com.irdaislakhuafa.garbagepickupapi.controllers.gql.receipt;

import com.irdaislakhuafa.garbagepickupapi.models.entities.Receipt;
import com.irdaislakhuafa.garbagepickupapi.services.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@SchemaMapping(typeName = "ReceiptQuery")
public class ReceiptQuery {
    private final ReceiptService receiptService;

    @SchemaMapping
    public Optional<Receipt> findById(@Argument(name = "id") String id) {
        final var result = this.receiptService.findById(id);
        return result;
    }

    @SchemaMapping
    public Optional<Receipt> findByPickupId(@Argument(name = "pickupId") String pickupId) {
        final var result = this.receiptService.findByPickupId(pickupId);
        return result;
    }
}