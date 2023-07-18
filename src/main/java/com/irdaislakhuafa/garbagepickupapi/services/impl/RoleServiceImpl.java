package com.irdaislakhuafa.garbagepickupapi.services.impl;

import com.irdaislakhuafa.garbagepickupapi.exceptions.custom.DataAlreadyExists;
import com.irdaislakhuafa.garbagepickupapi.models.entities.Role;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.role.RoleRequest;
import com.irdaislakhuafa.garbagepickupapi.repository.RoleRepository;
import com.irdaislakhuafa.garbagepickupapi.services.RoleService;
import com.irdaislakhuafa.garbagepickupapi.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final UserService userService;

    @Override
    public Optional<Role> save(Role request) {
        log.info("save new role with name '" + request.getName().toUpperCase() + "'");
        try {
//			always set name role to uppercase
            request.setName(request.getName().toUpperCase());
            final var role = this.roleRepository.save(request);
            return Optional.ofNullable(role);
        } catch (DataIntegrityViolationException e) {
            throw new DataAlreadyExists("role with name '" + request.getName() + "' already exists");
        } catch (Exception e) {
            log.info("error while save new role, " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Role fromRequestToEntity(RoleRequest request) {
        final var result = Role.builder()
                .name(request.getName())
                .description(request.getDescription())
                .createdBy(this.userService.getCurrentUser().getId())
                .build();

        return result;
    }

    @Override
    public Role fromUpdateRequestToEntity(Object request) {
        return null;
    }

}