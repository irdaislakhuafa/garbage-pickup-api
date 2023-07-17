package com.irdaislakhuafa.garbagepickupapi.services.impl;

import java.util.Optional;

import com.irdaislakhuafa.garbagepickupapi.models.entities.User;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.UserRequest;
import com.irdaislakhuafa.garbagepickupapi.models.gql.request.UserUpdateRequest;
import com.irdaislakhuafa.garbagepickupapi.services.UserServiceTest;

public class UserServiceTestImpl implements UserServiceTest<User> {

	@Override
	public Optional<User> save(User user) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'save'");
	}

	@Override
	public Optional<User> update(User user) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'update'");
	}

	@Override
	public User fromRequestToEntity(UserRequest request) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'fromRequestToEntity'");
	}

	@Override
	public Optional<User> getCurrentUser() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getCurrentUser'");
	}

	@Override
	public User fromUpdateRequestToEntity(UserUpdateRequest request) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'fromUpdateRequestToEntity'");
	}

}
