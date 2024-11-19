package com.memorybook.model.dao;

import com.memorybook.model.dto.User;

public interface UserDao {

	User getUser(String email);

	int insertUser(User user);

}
