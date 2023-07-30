package com.irdaislakhuafa.garbagepickupapi.services.impl;

import com.irdaislakhuafa.garbagepickupapi.models.entities.*;
import com.irdaislakhuafa.garbagepickupapi.models.entities.utils.PickupStatus;
import com.irdaislakhuafa.garbagepickupapi.models.entities.utils.ReceiptStatus;
import com.irdaislakhuafa.garbagepickupapi.repository.ReceiptRepository;
import com.irdaislakhuafa.garbagepickupapi.services.ReceiptService;
import com.irdaislakhuafa.garbagepickupapi.services.ReceiptServiceTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(value = MockitoExtension.class)
public class ReceiptServiceTestImpl implements ReceiptServiceTest {
    private static final User user = User.builder()
            .build();
    private static final User courier = User.builder()
            .build();
    private static final TrashType trashType = TrashType.builder()
            .build();

    private static final UserVoucher userVoucher = UserVoucher.builder()
            .build();
    public static final Pickup pickup = Pickup.builder()
            .resi("00000")
            .user(user)
            .courier(courier)
            .status(PickupStatus.IN_QUEUE)
            .weight(1)
            .place("tuban")
            .trashType(trashType)
            .description("this is description")
            .userVoucher(userVoucher)
            .price(1000)
            .lat(100)
            .lng(-100)
            .build();
    public static final Receipt receipt = Receipt.builder()
            .pickup(pickup)
            .status(ReceiptStatus.AWAITING)
            .point(1000)
            .build();

    @MockBean
    private ReceiptRepository receiptRepository;
    @Autowired
    private ReceiptService receiptService;

    @Override
    @Test
    public void findAll() {
        // success
        when(this.receiptRepository.findAll()).thenReturn(List.of(receipt));
        final var results = this.receiptService.findAll();
        assertFalse(results.isEmpty());
        assertEquals(results.get(0).getPickup().getResi(), "00000");

        reset(this.receiptRepository);

        // empty
        when(this.receiptRepository.findAll()).thenReturn(new ArrayList<Receipt>());
        assertTrue(this.receiptService.findAll().isEmpty());
    }

    @Override
    @Test
    public void findAllByPickupId() {
        // success
        when(this.receiptRepository.findAllByPickupId(anyString())).thenReturn(List.of(receipt));
        var results = this.receiptService.findAllByPickupId(anyString());
        assertFalse(results.isEmpty());
        reset(this.receiptRepository);

        // empty
        results = this.receiptService.findAllByPickupId(anyString());
        assertTrue(results.isEmpty());
    }
}