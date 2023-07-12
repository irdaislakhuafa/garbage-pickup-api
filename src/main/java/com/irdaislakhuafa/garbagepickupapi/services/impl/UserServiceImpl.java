package com.irdaislakhuafa.garbagepickupapi.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.irdaislakhuafa.garbagepickupapi.entities.User;
import com.irdaislakhuafa.garbagepickupapi.repository.UserRepository;
import com.irdaislakhuafa.garbagepickupapi.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService<User> {
	private final UserRepository userRepository;

	@Override
	public Optional<User> save(User user) {
		user = user.builder()
				.build();
		return Optional.ofNullable(user);
	}

}
