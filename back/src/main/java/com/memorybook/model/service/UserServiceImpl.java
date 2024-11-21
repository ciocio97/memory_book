package com.memorybook.model.service;

import java.util.Random;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.memorybook.model.dao.UserDao;
import com.memorybook.model.dto.User;

@Service
public class UserServiceImpl implements UserService {

	private final UserDao userDao;

	public UserServiceImpl(UserDao userDao) {
		// TODO Auto-generated constructor stub
		this.userDao = userDao;
	}

	@Override
	public String getUserId(String email) {
		// TODO Auto-generated method stub
		try {
			User user = userDao.getUser(email);
			if (user == null) {
				return null;
			}
			return user.getUserId();

		} catch (DataAccessException e) {
			throw new RuntimeException("Faileds to get user into the database");
		} catch (Exception e) {
			// 기타 예상하지 못한 예외 처리
			throw new RuntimeException("An unexpected error occurred while finding userId.", e);
		}
	}

	@Override
	public User createUser(String email) {
		// TODO Auto-generated method stub
		User user = new User();
		user.setUserEmail(email);
		// 너무 긴 uuid 대신에 nanoid 사용

		Random random = new Random();
		char[] alphabet = "abcdefghijklmnopqrstuvwxyxABCDEFGHIJKLMOPQRSTUVWXYSZ@!#".toCharArray();
		String nanoId = NanoIdUtils.randomNanoId(random, alphabet, 20);
		user.setUserId(nanoId);

		try {
			int result = userDao.insertUser(user);
			if (result == 0) {
				// 생성에 실패함, 생성 가능할 때 까지 nanoid 재생성
				while (result == 0) {
					nanoId = NanoIdUtils.randomNanoId(random, alphabet, 20);
					user.setUserId(nanoId);
					result = userDao.insertUser(user);
				}
			}
			return user;

		} catch (DataAccessException e) {
			throw new RuntimeException("Faileds to create user into the database");
		} catch (Exception e) {
			// 기타 예상하지 못한 예외 처리
			throw new RuntimeException("An unexpected error occurred while finding userId.", e);
		}
	}

	@Override
	public boolean existsbyId(String userId) {
		// TODO Auto-generated method stub
		try {
			return userDao.selectById(userId);
		} catch (DataAccessException e) {
			throw new RuntimeException("Faileds to find user into the database");
		} catch (Exception e) {
			// 기타 예상하지 못한 예외 처리
			throw new RuntimeException("An unexpected error occurred while finding userId.", e);
		}
	}
}
