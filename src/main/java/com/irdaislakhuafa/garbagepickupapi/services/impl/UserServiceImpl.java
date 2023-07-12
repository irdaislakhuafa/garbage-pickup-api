package com.irdaislakhuafa.garbagepickupapi.services.impl;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.irdaislakhuafa.garbagepickupapi.models.entities.User;
import com.irdaislakhuafa.garbagepickupapi.repository.UserRepository;
import com.irdaislakhuafa.garbagepickupapi.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService<User> {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Override
	public Optional<User> save(User user) {
		user = user.builder()
				.password(this.passwordEncoder.encode(user.getPassword()))
				.build();
		return Optional.ofNullable(user);
	}

}
