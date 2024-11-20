package com.memorybook.model.service;

public interface RefreshTokenService {

	String createAndSaveRefreshToken(String userId);

	String getUserIdByToken(String refreshToken);

}
