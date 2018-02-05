package com.letbeclear.security;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letbeclear.domain.LBCLogin;

public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
	//Autowiring is not happening in the Filter due to some unexpected reasons 
	
//	@Autowired
//	UserRegService userRegService;
//	
//	@Autowired
//	UsersService usersService;
	
//	@Autowired
//	JwtGenerator jwtGenerator;
//	
//	@Autowired
//	JwtUser jwtUser;
	
	private AuthenticationManager authenticationManager;
	
	public JwtUsernamePasswordAuthenticationFilter() 
	{
		System.out.println("Inside JwtUsernamePasswordAuthenticationFilter Default Constructor");
	}
	
	public JwtUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) 
	{
		this.authenticationManager=authenticationManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException 
	{
		JwtAuthenticationToken jwtAuthenticationToken=null;
		try
		{
			//Code of User authentication is done here
			
			//Getting LBCLogin Object from request
			
			ObjectMapper objectMapper=new ObjectMapper();
			
			//readValue method will need byte or json therefore extracting byte from request
			
			//Reader requestReader=request.getReader();
			
			//ServletInputStream inputStream=request.getInputStream();
			
			LBCLogin lbcLogin=objectMapper.readValue(request.getInputStream(), LBCLogin.class);
			
			String email=lbcLogin.getEmail();	//This will be setted as principal
			String password=lbcLogin.getPassword();	//This will be setted as Credentials
			
			System.out.println("Inside JwtUsernamePasswordAuthenticationFilter attemptAuthentication email is "+email);
			System.out.println("Inside JwtUsernamePasswordAuthenticationFilter attemptAuthentication password is "+password);
			
			Collection<? extends GrantedAuthority> authorities=Collections.emptyList();
			
			jwtAuthenticationToken=new JwtAuthenticationToken(email, password, authorities);
		}
		catch(IOException e)
		{
			System.out.println("Exception in JwtUsernamePasswordAuthenticationFilter attemptAuthentication");
			e.printStackTrace();
		}
		return authenticationManager.authenticate(jwtAuthenticationToken);
		
		//return super.attemptAuthentication(request, response);
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException 
	{
		//If you want to add the token in header then it should be added here
		
		//from this method the successhandler onAuthenticationSuccess() will execute
		
		//and from this method the Authentication object will be setted in SecurityContextHolder
		
		super.successfulAuthentication(request, response, chain, authResult);
		
		//sending request ahead to the filters BUT
		//THIS WILL NOT WORK HERE AS WE HAVE ALREADY READ THE REQUEST AND EXTRACTED DATA FROM IT
		//IT WILL GIVE US EXCEPTION AS STREAM CLOSED
		
		//BUT WE CAN DO ONE THING TO FORWARD REQUEST request.getRequestDispatcher("/login"));
	
//		javax.servlet.RequestDispatcher requestDispatcher=request.getRequestDispatcher("/login");
//		requestDispatcher.forward(request, response);
		
		//THIS WILL ALSO NOT WORK HERE
	
		//Token Generation Code
		
		String email=authResult.getName();
		System.out.println("Inside JwtUsernamePasswordAuthenticationFilter successfulAuthentication() Email is "+email);
		//System.out.println("UserRegService is "+userRegService);
	
		chain.doFilter(request, response);
	
//		System.out.println("Email "+authResult.getName());
//		System.out.println("Roles "+authResult.getAuthorities().toString());
//		System.out.println("Credentials "+authResult.getCredentials());
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException 
	{
		System.out.println("Inside JwtUsernamePasswordAuthenticationFilter unsuccessfulAuthentication()");
		super.unsuccessfulAuthentication(request, response, failed);
		//Inside from super.unsuccessfulAuthentication(request, response, failed);
		//the JwtAuthenticationFailureHandler onAuthenticationFailure
	}
}
