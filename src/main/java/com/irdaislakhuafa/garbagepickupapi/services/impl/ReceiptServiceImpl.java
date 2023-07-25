package com.irdaislakhuafa.garbagepickupapi.services.impl;

import com.irdaislakhuafa.garbagepickupapi.exceptions.custom.BadRequestException;
import com.irdaislakhuafa.garbagepickupapi.models.entities.Receipt;
import com.irdaislakhuafa.garbagepickupapi.repository.ReceiptRepository;
import com.irdaislakhuafa.garbagepickupapi.services.ReceiptService;
import com.irdaislakhuafa.garbagepickupapi.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReceiptServiceImpl implements ReceiptService {
    private final ReceiptRepository receiptRepository;
    private final UserService userService;

    @Override
    public Optional<Receipt> save(Receipt request) {
        try {
            request.setCreatedAt(LocalDateTime.now());
            request.setCreatedBy(this.userService.getCurrentUser().getId());

            final var result = this.receiptRepository.save(request);
            return Optional.of(result);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public Optional<Receipt> update(Receipt request) {
        return Optional.empty();
    }

    @Override
    public Optional<Receipt> delete(String s) {
        return Optional.empty();
    }

    @Override
    public Optional<Receipt> findById(String s) {
        return Optional.empty();
    }

    @Override
    public List<Receipt> findAll() {
        return null;
    }
}