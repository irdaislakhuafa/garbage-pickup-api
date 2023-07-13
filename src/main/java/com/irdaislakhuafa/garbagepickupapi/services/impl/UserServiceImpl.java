package com.irdaislakhuafa.garbagepickupapi.services.impl;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.irdaislakhuafa.garbagepickupapi.models.entities.User;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.UserRequest;
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
		} catch (Exception e) {
			log.error("error while save new user, " + e.getMessage(), e);
		}
		return Optional.empty();
	}

	@Override
	public User fromRequestToEntity(UserRequest request) {
		try {
			var result = User.builder()
					.name(request.getName())
					.email(request.getEmail())
					.password(request.getPassword())
					.image(request.getImage())
					.phone(request.getPhone())
					.saldo(request.getSaldo())
					.point(request.getPoint())
					.createdBy("system") // TODO: get current user id
					.build();

			return result;
		} catch (Exception e) {
			return null;
		}
	}

}
