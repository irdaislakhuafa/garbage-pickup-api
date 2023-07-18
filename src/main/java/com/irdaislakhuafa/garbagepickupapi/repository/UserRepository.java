package com.irdaislakhuafa.garbagepickupapi.repository;

import com.irdaislakhuafa.garbagepickupapi.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    Set<User> findAllByRolesNameEqualsIgnoreCaseAndIsDeleted(String roleName, boolean isDeleted);
}