package com.memorybook.mvc.model.service;

import com.memorybook.mvc.model.dto.User;

public interface UserService {

	String getUserId(String email);

	User createUser(String email);

}
