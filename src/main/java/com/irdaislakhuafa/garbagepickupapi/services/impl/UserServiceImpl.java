package com.irdaislakhuafa.garbagepickupapi.services.impl;

import com.irdaislakhuafa.garbagepickupapi.exceptions.custom.BadRequestException;
import com.irdaislakhuafa.garbagepickupapi.exceptions.custom.DataNotFound;
import com.irdaislakhuafa.garbagepickupapi.models.entities.LastLocation;
import com.irdaislakhuafa.garbagepickupapi.models.entities.Role;
import com.irdaislakhuafa.garbagepickupapi.models.entities.User;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.user.UserRequest;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.user.UserUpdateRequest;
import com.irdaislakhuafa.garbagepickupapi.repository.LastLocationRepository;
import com.irdaislakhuafa.garbagepickupapi.repository.RoleRepository;
import com.irdaislakhuafa.garbagepickupapi.repository.UserRepository;
import com.irdaislakhuafa.garbagepickupapi.services.MinIOFileService;
import com.irdaislakhuafa.garbagepickupapi.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final LastLocationRepository lastLocationRepository;
    private final MinIOFileService minIOFileService;

    @Value(value = "${minio.buckets.users}")
    private String BUCKET_USERS;

    @Override
    public Optional<User> save(User user) {
        try {
            log.info("saving new user");

            final var lastLocation = this.lastLocationRepository.save(LastLocation.builder().build());

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setLastLocation(lastLocation);

            final var result = this.userRepository.save(user);

            return Optional.of(result);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public User fromRequestToEntity(UserRequest request) {
        try {

            final var roles = new ArrayList<Role>();
            request.getRoles().forEach(v -> {
                final var role = roleRepository.findByNameEqualsIgnoreCase(v.name());
                if (role.isPresent()) {
                    roles.add(role.get());
                } else {
                    throw new DataNotFound("role with name '" + v.name() + "'' not found");
                }
            });

            var imageLink = "";
            if (request.getImage() != null) {
                final var imageFileName = this.minIOFileService.upload(request.getImage(), this.BUCKET_USERS);
                imageLink = this.minIOFileService.getPresignedUrl(MinIOFileService.PresignedUrl
                        .builder()
                        .bucketName(this.BUCKET_USERS)
                        .fileName(imageFileName)
                        .build());
            }

            final var result = User.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(request.getPassword())
                    .image(imageLink)
                    .phone(request.getPhone())
                    .address(request.getAddress())
                    .point(request.getPoint())
                    .roles(roles)
                    .createdBy(this.getCurrentUser().getId())
                    .build();

            return result;
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final var user = this.userRepository.findByEmail(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("user now found");
        }
        return user.get();
    }

    @Override
    public User getCurrentUser() {
        final var authentication = SecurityContextHolder.getContext().getAuthentication();
        final var user = this.userRepository.findByEmail(authentication.getName());
        if (user.isEmpty()) {
            final var defaultUser = User.builder()
                    .id("system")
                    .build();
            return defaultUser;
        }
        return user.get();
    }

    @Override
    public List<User> findAll() {
        final var results = this.userRepository.findAll();
        return results;
    }

    @Override
    public Optional<User> update(User request) {
        final var user = this.userRepository.findById(request.getId());
        if (user.isEmpty()) {
            throw new DataNotFound(String.format("user with id '%s' not found, please register first", request.getId()));
        }

        user.get().setName(request.getName());
        user.get().setEmail(request.getEmail());
        user.get().setImage(request.getImage());
        user.get().setPhone(request.getPhone());
        user.get().setAddress(request.getAddress());
        user.get().setPoint(request.getPoint());
        user.get().setRoles(request.getRoles());
        user.get().setDeleted(request.isDeleted());
        user.get().setUpdatedBy(this.getCurrentUser().getId());
        user.get().setUpdatedAt(LocalDateTime.now());

        final var updated = this.userRepository.save(user.get());
        return Optional.of(updated);
    }

    @Override
    public Optional<User> update(UserUpdateRequest request) {
        final var user = this.userRepository.findById(request.getId());
        if (user.isEmpty()) {
            throw new DataNotFound(String.format("user with id '%s' not found, please register first", request.getId()));
        }

        final var converted = this.fromUpdateRequestToEntity(request);
        try {
            if (request.getImage() != null) {
                if (!request.getImage().isEmpty()) {
                    final var imageFileName = this.minIOFileService.upload(request.getImage(), this.BUCKET_USERS);
                    final var imageLink = this.minIOFileService.getPresignedUrl(MinIOFileService.PresignedUrl
                            .builder()
                            .bucketName(this.BUCKET_USERS)
                            .fileName(imageFileName)
                            .build());
                    converted.setImage(imageLink);
                }
            } else {
                converted.setImage(user.get().getImage());
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

        return this.update(converted);
    }

    @Override
    public Optional<User> delete(String s) {
        return Optional.empty();
    }

    @Override
    @Transactional
    public User fromUpdateRequestToEntity(UserUpdateRequest request) {
        try {

            final var roles = new ArrayList<Role>();
            request.getRoles().forEach(v -> {
                final var role = roleRepository.findByNameEqualsIgnoreCase(v.name());
                if (role.isPresent()) {
                    roles.add(role.get());
                } else {
                    throw new DataNotFound("role with name '" + v.name() + "'' not found");
                }
            });

            final var result = User.builder()
                    .id(request.getId())
                    .name(request.getName()) // doesn't mapping field image
                    .email(request.getEmail())
                    .phone(request.getPhone())
                    .address(request.getAddress())
                    .point(request.getPoint())
                    .roles(roles)
                    .isDeleted(request.getIsDeleted())
                    .updatedBy(this.getCurrentUser().getId())
                    .build();

            return result;
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public Optional<User> findById(String id) {
        final var user = this.userRepository.findById(id).orElseThrow(() -> {
            throw new DataNotFound(String.format("user with id '%s' not found", id));
        });
        return Optional.ofNullable(user);
    }

}