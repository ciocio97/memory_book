package com.memorybook.mvc.model.service;

import org.springframework.stereotype.Service;

import com.memorybook.mvc.model.dao.UserDao;

@Service
public class UserServiceImpl implements UserService{

	private final UserDao userDao;
	
	public UserServiceImpl(UserDao userDao) {
		// TODO Auto-generated constructor stub
		this.userDao = userDao;
	}
}
