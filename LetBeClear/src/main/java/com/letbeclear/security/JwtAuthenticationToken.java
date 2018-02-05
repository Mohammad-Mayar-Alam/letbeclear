package com.letbeclear.security;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken
{
	//This token will be returned to everyone who wants it
	private static String token;
	
	//This Constructor is used to get token whenever we want to return the token to the user
	public JwtAuthenticationToken() 
	{
		super(null, null);
	}
	public JwtAuthenticationToken(String token)
	{
		//this constructor is called from JwtAuthenticationTokenFilter
		// public Authentication attemptAuthentication(request,response) 
		//which returns an Authentication Object
		//and the token which comes in header i.e "Authorisation" is saved here
		
		super(null, null);
		this.token=token;
	}
	
	//This Constructor is used at the time of authentication of login request
	public JwtAuthenticationToken(String email, String password, Collection<? extends GrantedAuthority> authorities)
	{
		super(email,password,authorities);
		System.out.println("Inside JwtAuthenticationToken authentication Constructor Principal Set");
	}
	
	public String getToken() 
	{
		return token;
	}

	public void setToken(String token) 
	{
		this.token = token;
	}	
}
