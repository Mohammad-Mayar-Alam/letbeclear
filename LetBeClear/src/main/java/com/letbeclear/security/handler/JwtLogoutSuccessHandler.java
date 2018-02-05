package com.letbeclear.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler
{
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException 
	{
		
		System.out.println("\n\nInside JwtLogoutSuccessHandler onlogoutSuccess()");
		System.out.println("Inside JwtLogoutSuccessHandler onLogoutSuccess Session is "+request.getSession(false));
	}
}
