package com.irdaislakhuafa.garbagepickupapi.services.impl;

import com.irdaislakhuafa.garbagepickupapi.exceptions.custom.BadRequestException;
import com.irdaislakhuafa.garbagepickupapi.exceptions.custom.DataAlreadyExists;
import com.irdaislakhuafa.garbagepickupapi.helpers.DistanceCalculatorHelper;
import com.irdaislakhuafa.garbagepickupapi.models.entities.Pickup;
import com.irdaislakhuafa.garbagepickupapi.models.entities.User;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.pickup.PickupRequest;
import com.irdaislakhuafa.garbagepickupapi.repository.PickupRepository;
import com.irdaislakhuafa.garbagepickupapi.repository.TrashTypeRepository;
import com.irdaislakhuafa.garbagepickupapi.repository.UserRepository;
import com.irdaislakhuafa.garbagepickupapi.services.PickupService;
import com.irdaislakhuafa.garbagepickupapi.services.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PickupServiceImpl implements PickupService {
    private final PickupRepository pickupRepository;
    private final UserRepository userRepository;
    private final TrashTypeRepository trashTypeRepository;
    private final UserService userService;
    private final DistanceCalculatorHelper distanceCalculatorHelper;


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
    public Set<Pickup> findAllByUserId(String userId) {
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
                .createdAt(LocalDateTime.now())
                .createdBy(this.userService.getCurrentUser().getId())
                .build();

        return result;
    }

    @Override
    public Pickup fromUpdateRequestToEntity(Object request) {
        return null;
    }
}