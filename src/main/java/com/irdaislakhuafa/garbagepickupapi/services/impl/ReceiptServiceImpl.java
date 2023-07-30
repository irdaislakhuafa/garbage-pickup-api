package com.irdaislakhuafa.garbagepickupapi.services.impl;

import com.irdaislakhuafa.garbagepickupapi.exceptions.custom.BadRequestException;
import com.irdaislakhuafa.garbagepickupapi.models.entities.Receipt;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.receipt.ReceiptRequest;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.receipt.ReceiptUpdateRequest;
import com.irdaislakhuafa.garbagepickupapi.repository.PickupRepository;
import com.irdaislakhuafa.garbagepickupapi.repository.ReceiptRepository;
import com.irdaislakhuafa.garbagepickupapi.repository.UserRepository;
import com.irdaislakhuafa.garbagepickupapi.services.ReceiptService;
import com.irdaislakhuafa.garbagepickupapi.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
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
    private final PickupRepository pickupRepository;
    private final UserRepository userRepository;

    @Override
    public Optional<Receipt> save(Receipt request) {
        try {
            var user = Optional.of(request.getPickup().getUser());
            if (!this.userRepository.existsById(user.get().getId())) {
                throw new BadRequestException(String.format("user with id '%s' doesn't exist, please register first", user.get().getId()));
            }

            user.get().setPoint(user.get().getPoint() + request.getPoint());
            user = this.userService.update(user.get());
            user.ifPresent(u -> request.getPickup().setUser(u));

            request.setCreatedAt(LocalDateTime.now());
            request.setCreatedBy(this.userService.getCurrentUser().getId());

            final var result = this.receiptRepository.save(request);
            return Optional.of(result);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(String.format("receipt for pickup with id '%s' already exists, %s", request.getPickup().getId(), e.getMessage()));
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
    public Optional<Receipt> findById(String id) {
        final var result = this.receiptRepository.findById(id);
        if (result.isEmpty()) {
            throw new BadRequestException(String.format("receipt with id '%s' not found", id));
        }
        return result;
    }

    @Override
    public List<Receipt> findAll() {
        final var results = this.receiptRepository.findAll();
        return results;
    }

    @Override
    public Receipt fromRequestToEntity(ReceiptRequest request) {
        final var pickup = this.pickupRepository.findById(request.getPickupId());
        if (pickup.isEmpty()) {
            throw new BadRequestException(String.format("pickup with id '%s' not found", request.getPickupId()));
        }

        final var result = Receipt
                .builder()
                .status(request.getStatus())
                .point(pickup.get().getWeight()) // TODO: added business logic here
                .pickup(pickup.get())
                .build();

        return result;
    }

    @Override
    public Receipt fromUpdateRequestToEntity(ReceiptUpdateRequest request) {
        return null;
    }

    @Override
    public Optional<Receipt> findByPickupId(String pickupId) {
        final var result = this.receiptRepository.findByPickupId(pickupId);
        if (result.isEmpty()) {
            throw new BadRequestException(String.format("receipt for pickup id '%s' not found", pickupId));
        }
        return result;
    }

    @Override
    public List<Receipt> findAllByPickupId(String pickupId) {
        final var results = this.receiptRepository.findAll();
        return null;
    }
}