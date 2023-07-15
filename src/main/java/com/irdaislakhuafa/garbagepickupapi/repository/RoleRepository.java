package com.irdaislakhuafa.garbagepickupapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.irdaislakhuafa.garbagepickupapi.models.entities.Role;

public interface RoleRepository extends JpaRepository<Role, String> {
	Optional<Role> findByNameEqualsIgnoreCase(String name);
}
