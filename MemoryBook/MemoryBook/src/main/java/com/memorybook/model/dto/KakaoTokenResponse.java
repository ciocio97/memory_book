package com.memorybook.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KakaoTokenResponse {
	@JsonProperty("token_type")
	private String tokenType;
	@JsonProperty("access_token")
	private String accessToken;
	@JsonProperty("id_token")
	private String idToken;
	@JsonProperty("expires_in")
	private Integer expiresIn;
	@JsonProperty("refresh_token")
	private String refreshToken;
	@JsonProperty("refresh_token_expires_in")
	private Integer refreshTokenExpiresIn;
	@JsonProperty("scope")
	private String scope;

	public KakaoTokenResponse() {
		// TODO Auto-generated constructor stub
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getIdToken() {
		return idToken;
	}

	public void setIdToken(String idToken) {
		this.idToken = idToken;
	}

	public Integer getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refrestToken) {
		this.refreshToken = refrestToken;
	}

	public Integer getRefreshTokenExpiresIn() {
		return refreshTokenExpiresIn;
	}

	public void setRefreshTokenExpiresIn(Integer refreshTokenExpiresIn) {
		this.refreshTokenExpiresIn = refreshTokenExpiresIn;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

}
