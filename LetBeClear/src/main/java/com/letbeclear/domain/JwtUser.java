package com.letbeclear.domain;

import org.springframework.stereotype.Component;

@Component
public class JwtUser 
{
	private String email;
	private String role;
	private long userId;
	
	public String getEmail() 
	{
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
}
