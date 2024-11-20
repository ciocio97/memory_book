package com.memorybook.model.dao;

public interface RefreshTokenDao {

	void insert(String userId, String refreshToken);

	void deleteByUserId(String userId);

	String selectByToken(String refreshToken);

}
