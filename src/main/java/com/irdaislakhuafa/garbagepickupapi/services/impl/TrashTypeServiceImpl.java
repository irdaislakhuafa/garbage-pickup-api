package com.irdaislakhuafa.garbagepickupapi.services.impl;

import com.irdaislakhuafa.garbagepickupapi.models.entities.TrashType;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.trashtype.TrashTypeRequest;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.trashtype.TrashTypeUpdateRequest;
import com.irdaislakhuafa.garbagepickupapi.repository.TrashTypeRepository;
import com.irdaislakhuafa.garbagepickupapi.services.TrashTypeService;
import com.irdaislakhuafa.garbagepickupapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrashTypeServiceImpl implements TrashTypeService {
    private final UserService userService;
    private final TrashTypeRepository trashTypeRepository;

    @Override
    public Optional<TrashType> save(TrashType request) {
        return Optional.empty();
    }

    @Override
    public Optional<TrashType> update(TrashType request) {
        return Optional.empty();
    }

    @Override
    public Optional<TrashType> delete(String s) {
        return Optional.empty();
    }

    @Override
    public Optional<TrashType> findById(String s) {
        return Optional.empty();
    }

    /**
     * @return result of this method doesn't map id
     */
    @Override
    public TrashType fromRequestToEntity(TrashTypeRequest request) {
        final var result = TrashType.builder()
                .name(request.getName())
                .description(request.getDescription())
                .createdAt(LocalDateTime.now())
                .createdBy(this.userService.getCurrentUser().getId())
                .build();
        return result;
    }

    @Override
    public TrashType fromUpdateRequestToEntity(TrashTypeUpdateRequest request) {
        return null;
    }
}