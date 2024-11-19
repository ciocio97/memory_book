package com.memorybook.model.provider;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	private final Key key;
	private final Key refreshKey;
	private final long accessTokenExpTime;
	private final long refreshTokenExpTime;

	public JwtTokenProvider(@Value("${jwt.secret}") String secretKey,
			@Value("${jwt.refresh.secret}") String refreshSecretKey,
			@Value("${jwt.expiration_time}") long accessTokenExpTime,
			@Value("${jwt.refresh.expiration_time}") long refreshTokenExpTime) {
		// TODO Auto-generated constructor stub
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		byte[] refreshKeyBytes = Decoders.BASE64.decode(refreshSecretKey);
		// 키 길이 검증
		if (keyBytes.length < 32) {
			throw new IllegalArgumentException("The key must be at least 256 bits (32 bytes)");
		}
		if (refreshKeyBytes.length < 32) {
			throw new IllegalArgumentException("The key must be at least 256 bits (32 bytes)");
		}
		this.key = Keys.hmacShaKeyFor(keyBytes);
		this.refreshKey = Keys.hmacShaKeyFor(refreshKeyBytes);
		this.accessTokenExpTime = accessTokenExpTime;
		this.refreshTokenExpTime = refreshTokenExpTime;
	}

	// 엑세스 토큰 생성
	public String createAccessToken(String userId) {
		return createToken(userId, accessTokenExpTime, key);
	}

	public String createToken(String userId, long expireTime, Key key) {

		ZonedDateTime now = ZonedDateTime.now();
		ZonedDateTime tokenValidity = now.plusSeconds(expireTime);

		String token = Jwts.builder().claim("userId", userId).issuedAt(Date.from(now.toInstant()))
				.expiration(Date.from(tokenValidity.toInstant())).signWith(key).compact();

		return token;
	}

	public String createRefreshToken(String userId) {
		// refresh token 생성
		String refreshToken = createToken(userId, refreshTokenExpTime, refreshKey);
		
		//db에 토큰 저장
		
		
		return refreshToken;
	}
}
