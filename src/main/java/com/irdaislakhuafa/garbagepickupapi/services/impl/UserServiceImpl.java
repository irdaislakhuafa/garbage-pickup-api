package com.irdaislakhuafa.garbagepickupapi.services.impl;

import com.irdaislakhuafa.garbagepickupapi.exceptions.custom.BadRequestException;
import com.irdaislakhuafa.garbagepickupapi.exceptions.custom.DataAlreadyExists;
import com.irdaislakhuafa.garbagepickupapi.exceptions.custom.DataNotFound;
import com.irdaislakhuafa.garbagepickupapi.models.entities.LastLocation;
import com.irdaislakhuafa.garbagepickupapi.models.entities.Role;
import com.irdaislakhuafa.garbagepickupapi.models.entities.User;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.user.UserRequest;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.user.UserUpdateRequest;
import com.irdaislakhuafa.garbagepickupapi.repository.LastLocationRepository;
import com.irdaislakhuafa.garbagepickupapi.repository.RoleRepository;
import com.irdaislakhuafa.garbagepickupapi.repository.UserRepository;
import com.irdaislakhuafa.garbagepickupapi.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final LastLocationRepository lastLocationRepository;

    @Override
    public Optional<User> save(User user) {
        try {
            log.info("saving new user");

            final var lastLocation = this.lastLocationRepository.save(LastLocation.builder().build());

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setLastLocation(lastLocation);

            final var result = this.userRepository.save(user);

            return Optional.ofNullable(result);
        } catch (DataIntegrityViolationException e) {
            throw new DataAlreadyExists("user already exists");
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public User fromRequestToEntity(UserRequest request) {
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
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .image(request.getImage())
                .phone(request.getPhone())
                .address(request.getAddress())
                .saldo(request.getSaldo())
                .point(request.getPoint())
                .roles(roles)
                .createdBy(this.getCurrentUser().getId())
                .build();

        return result;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final var user = this.userRepository.findByEmail(username);
        if (!user.isPresent()) {
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
        user.get().setSaldo(request.getSaldo());
        user.get().setPoint(request.getPoint());
        user.get().setRoles(request.getRoles());
        user.get().setDeleted(request.isDeleted());
        user.get().setUpdatedBy(this.getCurrentUser().getId());
        user.get().setUpdatedAt(LocalDateTime.now());

        final var updated = this.userRepository.save(user.get());
        return Optional.of(updated);
    }

    @Override
    public User fromUpdateRequestToEntity(UserUpdateRequest request) {
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
                .name(request.getName())
                .email(request.getEmail())
                .image(request.getImage())
                .phone(request.getPhone())
                .address(request.getAddress())
                .saldo(request.getSaldo())
                .point(request.getPoint())
                .roles(roles)
                .isDeleted(request.isDeleted())
                .updatedBy(this.getCurrentUser().getId())
                .build();

        return result;
    }

    @Override
    public User findById(String id) {
        final var user = this.userRepository.findById(id).orElseThrow(() -> {
            throw new DataNotFound(String.format("user with id '%s' not found", id));
        });
        return user;
    }

}