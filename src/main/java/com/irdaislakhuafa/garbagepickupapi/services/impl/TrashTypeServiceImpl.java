package com.irdaislakhuafa.garbagepickupapi.services.impl;

import com.irdaislakhuafa.garbagepickupapi.exceptions.custom.BadRequestException;
import com.irdaislakhuafa.garbagepickupapi.models.entities.TrashType;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.trashtype.TrashTypeRequest;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.trashtype.TrashTypeUpdateRequest;
import com.irdaislakhuafa.garbagepickupapi.repository.TrashTypeRepository;
import com.irdaislakhuafa.garbagepickupapi.services.TrashTypeService;
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
public class TrashTypeServiceImpl implements TrashTypeService {
    private final UserService userService;
    private final TrashTypeRepository trashTypeRepository;

    @Override
    public Optional<TrashType> save(TrashType request) {
        try {
            final var result = this.trashTypeRepository.save(request);
            return Optional.of(result);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
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

    @Override
    public List<TrashType> findAll() {
        final var results = this.trashTypeRepository.findAll();
        return results;
    }
}