package com.memorybook.model.service;

import com.memorybook.model.dto.User;

public interface UserService {

	String getUserId(String email);

	User createUser(String email);

	boolean existsbyId(String userId);

}
