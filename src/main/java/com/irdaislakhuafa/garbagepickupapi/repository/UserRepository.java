package com.irdaislakhuafa.garbagepickupapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.irdaislakhuafa.garbagepickupapi.models.entities.User;

public interface UserRepository extends JpaRepository<User, String> {

}
