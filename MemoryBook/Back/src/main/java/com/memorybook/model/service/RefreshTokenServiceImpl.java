package com.memorybook.model.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.memorybook.model.dao.RefreshTokenDao;
import com.memorybook.model.provider.JwtTokenProvider;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
	private final RefreshTokenDao refreshTokenDao;
	private final JwtTokenProvider jwtTokenProvider;

	public RefreshTokenServiceImpl(RefreshTokenDao refreshTokenDao, JwtTokenProvider jwtTokenProvider) {
		// TODO Auto-generated constructor stub
		this.refreshTokenDao = refreshTokenDao;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	@Transactional
	public String createAndSaveRefreshToken(String userId) {
		// TODO Auto-generated method stub
		// 기존 리프레시 토큰은 삭제.. 한 디바이스에서만 로그인할 수 있게 된다.
		refreshTokenDao.deleteByUserId(userId);

		String refreshToken = jwtTokenProvider.createRefreshToken(userId);

		refreshTokenDao.insert(userId, refreshToken);

		return refreshToken;
	}

	@Override
	public String getUserIdByToken(String refreshToken) {
		return refreshTokenDao.selectByToken(refreshToken);
	}
}
