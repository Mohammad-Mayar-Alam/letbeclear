package com.letbeclear.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler 
{
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException 
	{
		System.out.println("Inside JwtAuthenticationFailureHandler onAuthenticationFailure()");
		
		Logger logger=Logger.getLogger(JwtAuthenticationFailureHandler.class);
		
		logger.error(exception);
		
		//Sending request to /loginFailed because to send Failed message in ResponseSender Object
		
		request.getRequestDispatcher("/loginFailed").forward(request, response);
	}
}
