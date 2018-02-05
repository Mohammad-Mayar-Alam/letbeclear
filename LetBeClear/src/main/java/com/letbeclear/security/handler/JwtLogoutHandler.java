package com.letbeclear.security.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

public class JwtLogoutHandler extends SecurityContextLogoutHandler
{
	private TokenBasedRememberMeServices forgetMe;
	
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) 
	{
		System.out.println("Inside JwtLogoutHandler logout()");
		super.logout(request, response, authentication);
	}
	
	public void setForgetMe(TokenBasedRememberMeServices forgetMe)
	{
		this.forgetMe=forgetMe;
	}
}
