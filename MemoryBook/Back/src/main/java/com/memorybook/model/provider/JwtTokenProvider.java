package com.memorybook.model.provider;

import java.time.ZonedDateTime;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtTokenProvider {

	private final SecretKey key;
	private final long accessTokenExpTime;
	private final long refreshTokenExpTime;

	public JwtTokenProvider(@Value("${jwt.secret}") String secretKey,
			@Value("${jwt.expiration_time}") long accessTokenExpTime,
			@Value("${jwt.refresh.expiration_time}") long refreshTokenExpTime) {
		// TODO Auto-generated constructor stub
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		// 키 길이 검증
		if (keyBytes.length < 32) {
			throw new IllegalArgumentException("The key must be at least 256 bits (32 bytes)");
		}
		this.key = Keys.hmacShaKeyFor(keyBytes);
		this.accessTokenExpTime = accessTokenExpTime;
		this.refreshTokenExpTime = refreshTokenExpTime;
	}

	// access token 생성
	public String createAccessToken(String userId) {
		return createToken(userId, accessTokenExpTime);
	}

	// refresh token 생성
	public String createRefreshToken(String userId) {
		return createToken(userId, refreshTokenExpTime);
	}

	// create token
	public String createToken(String userId, long expireTime) {

		ZonedDateTime now = ZonedDateTime.now();
		ZonedDateTime tokenValidity = now.plusSeconds(expireTime);

		String token = Jwts.builder().header().add("typ", "JWT").and().claim("userId", userId)
				.issuedAt(Date.from(now.toInstant())).expiration(Date.from(tokenValidity.toInstant())).signWith(key)
				.compact();

		return token;
	}

	// 토큰 유효성 검증
	public boolean validateToken(String token) {
		try {
			Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
			if (claims.getExpiration().before(new Date())) {
				return false; // 토큰이 만료됨
			}
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			// not valid
			return false;
		}
	}

	public String resolveAccessToken(HttpServletRequest request) {
		// 엑세스 토큰을 반환한다.
		return request.getHeader("Authorization").substring(7);
	}

	public String resolveRefreshTokenFromCookie(HttpServletRequest request) {
		// 쿠키에서 리프레시 토큰 추출
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("refreshToken".equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	public String getUserIdFromToken(String token) {
		// 토큰에서 userId를 추출한다.
		Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
		return claims.get("userId", String.class);
	}
}
