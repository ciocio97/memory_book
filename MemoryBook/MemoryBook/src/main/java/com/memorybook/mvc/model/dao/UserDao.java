package com.memorybook.mvc.model.dao;

import com.memorybook.mvc.model.dto.User;

public interface UserDao {

	User getUser(String email);

	int insertUser(User user);

}
