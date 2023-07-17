package com.irdaislakhuafa.garbagepickupapi.services.impl;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.irdaislakhuafa.garbagepickupapi.exceptions.custom.DataAlreadyExists;
import com.irdaislakhuafa.garbagepickupapi.models.entities.Role;
import com.irdaislakhuafa.garbagepickupapi.models.entities.User;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.RoleRequest;
import com.irdaislakhuafa.garbagepickupapi.repository.RoleRepository;
import com.irdaislakhuafa.garbagepickupapi.services.RoleService;
import com.irdaislakhuafa.garbagepickupapi.services.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService<Role> {
	private final RoleRepository roleRepository;
	private final UserService<User> userService;

	@Override
	public Optional<Role> save(Role request) {
		log.info("save new role with name '" + request.getName() + "'");
		try {
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
				.createdBy(this.userService.getCurrentUser().get().getId())
				.build();

		return result;
	}

}
