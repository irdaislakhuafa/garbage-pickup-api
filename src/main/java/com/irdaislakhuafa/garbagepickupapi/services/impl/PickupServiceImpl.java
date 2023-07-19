package com.irdaislakhuafa.garbagepickupapi.services.impl;

import com.irdaislakhuafa.garbagepickupapi.exceptions.custom.BadRequestException;
import com.irdaislakhuafa.garbagepickupapi.exceptions.custom.DataAlreadyExists;
import com.irdaislakhuafa.garbagepickupapi.helpers.DistanceCalculatorHelper;
import com.irdaislakhuafa.garbagepickupapi.models.entities.Pickup;
import com.irdaislakhuafa.garbagepickupapi.models.entities.User;
import com.irdaislakhuafa.garbagepickupapi.models.entities.utils.PickupStatus;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.pickup.PickupRequest;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.pickup.PickupUpdateRequest;
import com.irdaislakhuafa.garbagepickupapi.repository.PickupRepository;
import com.irdaislakhuafa.garbagepickupapi.repository.TrashTypeRepository;
import com.irdaislakhuafa.garbagepickupapi.repository.UserRepository;
import com.irdaislakhuafa.garbagepickupapi.services.PickupService;
import com.irdaislakhuafa.garbagepickupapi.services.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PickupServiceImpl implements PickupService {
    private final PickupRepository pickupRepository;
    private final UserRepository userRepository;
    private final TrashTypeRepository trashTypeRepository;
    private final UserService userService;
    private final DistanceCalculatorHelper distanceCalculatorHelper;
    //    private final SimpleDateFormat dateFormatter;

    @Value(value = "${app.config.date-format-layout}")
    private String dateFormatLayout;

    @Override
    public Optional<Pickup> save(Pickup request) {
        try {
            /*
             * added logic to select courier
             */

            // find all user with role "courier" and not deleted
            final var listCourier = this.userRepository.findAllByRolesNameEqualsIgnoreCaseAndIsDeleted("courier", false);
            if (listCourier.isEmpty()) {
                throw new BadRequestException("no registered courier");
            }


            // calculate distance between user from request pickup with each courier
            @Data
            class CourierDistance {
                private User courier;
                private double distance; // in KM
            }

            final var listCourierDistance = new ArrayList<CourierDistance>();
            listCourier.forEach(courier -> {
                final var courierLastLocation = courier.getLastLocation();
                final var distance = distanceCalculatorHelper.calculateDistance(
                        DistanceCalculatorHelper.Distance.builder()
                                .lat(request.getLat())
                                .lng(request.getLng())
                                .build(),
                        DistanceCalculatorHelper.Distance.builder()
                                .lat(courierLastLocation.getLat())
                                .lng(courierLastLocation.getLng())
                                .build()
                );

                final var courierDistance = new CourierDistance() {{
                    setCourier(courier);
                    setDistance(distance);
                }};

                listCourierDistance.add(courierDistance);
            });

            // sort ascending by field "distance"
            listCourierDistance.sort((a, b) -> (int) (a.distance - b.distance));

            // apply selected courier
            final var selected = listCourierDistance.get(0);
            request.setCourier(selected.getCourier());

            // generate no resi based on current millis
            request.setResi(String.valueOf(System.currentTimeMillis()));

            // save request
            final var result = this.pickupRepository.save(request);

            return Optional.of(result);
        } catch (DataIntegrityViolationException e) {
            throw new DataAlreadyExists("this request pickup already exists");
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public Optional<Pickup> update(Pickup request) {
        try {
            final var pickup = this.pickupRepository.findById(request.getId());
            if (pickup.isEmpty()) {
                throw new BadRequestException(String.format("pickup with id '%s' doesn't exist", request.getId()));
            }

            pickup.get().setUser(request.getUser());
            pickup.get().setStatus(request.getStatus());
            pickup.get().setPlace(request.getPlace());
            pickup.get().setTrashType(request.getTrashType());
            pickup.get().setWeight(request.getWeight());
            pickup.get().setDescription(request.getDescription());
            pickup.get().setLat(request.getLat());
            pickup.get().setLng(request.getLng());
            pickup.get().setDeleted(request.isDeleted());

            final var result = this.pickupRepository.save(pickup.get());
            return Optional.of(result);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public Set<Pickup> findAllByUserIdWithRange(String userId, String start, String end, PickupStatus status) {
        try {
            final var formatter = DateTimeFormatter.ofPattern(dateFormatLayout);
            final var startDate = LocalDateTime.parse(start, formatter);
            final var endDate = LocalDateTime.parse(end, formatter);

            var results = new HashSet<Pickup>();
            if (status == null) {
                results = this.pickupRepository.findAllByUserIdAndCreatedAtBetween(userId, startDate, endDate);
            } else {
                results = this.pickupRepository.findAllByUserIdAndCreatedAtBetweenAndStatus(userId, startDate, endDate, status);
            }

            return results;
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public Optional<Pickup> delete(String s) {
        return Optional.empty();
    }

    @Override
    public Optional<Pickup> findById(String s) {
        return Optional.empty();
    }

    @Override
    public List<Pickup> findAll() {
        return null;
    }


    /**
     * @param request is request body to create new pickup
     * @return result this method doesn't specify the courier and no resi
     */
    @Override
    public Pickup fromRequestToEntity(PickupRequest request) {
        // find user by id and throw exception if not exists
        final var user = this.userRepository.findById(request.getUserId());
        if (user.isEmpty()) {
            throw new BadRequestException(String.format("user with id '%s' not found", request.getUserId()));
        }

        // find trash type by id and throw exception if not exists
        final var trashType = this.trashTypeRepository.findById(request.getTrashTypeId());
        if (trashType.isEmpty()) {
            throw new BadRequestException(String.format("trash type with id '%s' not found", request.getTrashTypeId()));
        }

        // build result
        final var result = Pickup.builder()
                .user(user.get())
                .status(request.getStatus())
                .weight(request.getWeight())
                .place(request.getPlace())
                .trashType(trashType.get())
                .description(request.getDescription())
                .lat(request.getLat())
                .lng(request.getLng())
                .createdAt(LocalDateTime.now())
                .createdBy(this.userService.getCurrentUser().getId())
                .build();

        return result;
    }

    @Override
    public Pickup fromUpdateRequestToEntity(PickupUpdateRequest request) {
        final var user = this.userRepository.findById(request.getUserId());
        if (user.isEmpty()) {
            throw new BadRequestException(String.format("user with id '%s' does not exist", request.getUserId()));
        }

        final var trashType = this.trashTypeRepository.findById(request.getId());
        if (trashType.isEmpty()) {
            throw new BadRequestException(String.format("trash type with id '%s' does not exist", request.getTrashTypeId()));
        }

        final var result = Pickup.builder()
                .id(request.getId())
                .user(user.get())
                .status(request.getStatus())
                .place(request.getPlace())
                .trashType(trashType.get())
                .weight(request.getWeight())
                .description(request.getDescription())
                .lat(request.getLat())
                .lng(request.getLng())
                .updatedAt(LocalDateTime.now())
                .updatedBy(this.userService.getCurrentUser().getId())
                .isDeleted(request.isDeleted())
                .build();

        return result;
    }


}