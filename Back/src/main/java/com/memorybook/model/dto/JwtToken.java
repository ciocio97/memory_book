package com.memorybook.model.dto;

public class JwtToken {
	private String grantType;
	private String accessToken;
	private String refreshToken;
	
	public JwtToken() {
		// TODO Auto-generated constructor stub
	}
	

	public JwtToken( String accessToken, String refreshToken) {
		this.grantType = "Bearer";
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}


	private String getGrantType() {
		return grantType;
	}

	private void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	private String getAccessToken() {
		return accessToken;
	}

	private void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	private String getRefreshToken() {
		return refreshToken;
	}

	private void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
