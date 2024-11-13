package com.memorybook.mvc.model.dto;

public class User {
	private String userId;
	private String userEmail;

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(String userId, String userEmail) {
		super();
		this.userId = userId;
		this.userEmail = userEmail;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userEmail=" + userEmail + "]";
	}

}
