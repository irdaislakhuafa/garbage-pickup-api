package com.irdaislakhuafa.garbagepickupapi.services.impl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.irdaislakhuafa.garbagepickupapi.exceptions.custom.DataAlreadyExists;
import com.irdaislakhuafa.garbagepickupapi.exceptions.custom.DataNotFound;
import com.irdaislakhuafa.garbagepickupapi.models.entities.Role;
import com.irdaislakhuafa.garbagepickupapi.models.entities.User;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.UserRequest;
import com.irdaislakhuafa.garbagepickupapi.repository.RoleRepository;
import com.irdaislakhuafa.garbagepickupapi.repository.UserRepository;
import com.irdaislakhuafa.garbagepickupapi.services.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService<User, UserRequest> {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public Optional<User> save(User user) {
        try {
            log.info("saving new user");

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            var result = this.userRepository.save(user);

            log.info("success save new user");
            return Optional.ofNullable(result);
        } catch (DataIntegrityViolationException e) {
            log.error("user already exists, " + e.getMessage(), e);
            throw new DataAlreadyExists("user already exists");
        } catch (Exception e) {
            log.error("error while save new user, " + e.getMessage(), e);
        }
        return Optional.empty();
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

        var result = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .image(request.getImage())
                .phone(request.getPhone())
                .address(request.getAddress())
                .saldo(request.getSaldo())
                .point(request.getPoint())
                .roles(roles)
                .createdBy(this.getCurrentUser().get().getId())
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
    public Optional<User> getCurrentUser() {
        final var authentication = SecurityContextHolder.getContext().getAuthentication();
        final var user = this.userRepository.findByEmail(authentication.getName());
        if (!user.isPresent()) {
            final var defaultUser = User.builder()
                    .id("system")
                    .build();
            return Optional.ofNullable(defaultUser);
        }
        return user;
    }

}