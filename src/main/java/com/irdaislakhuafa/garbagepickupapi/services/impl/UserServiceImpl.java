package com.irdaislakhuafa.garbagepickupapi.services.impl;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.irdaislakhuafa.garbagepickupapi.models.entities.User;
import com.irdaislakhuafa.garbagepickupapi.repository.UserRepository;
import com.irdaislakhuafa.garbagepickupapi.services.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService<User> {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Override
	public Optional<User> save(User user) {
		try {
			log.info("saving new user");
			user = user.builder()
					.password(this.passwordEncoder.encode(user.getPassword()))
					.build();
			var result = this.userRepository.save(user);
			return Optional.ofNullable(result);
		} catch (Exception e) {
			log.error("error while save new user, " + e.getMessage(), e);
			return Optional.empty();
		}
	}

}
