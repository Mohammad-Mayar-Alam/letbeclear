package com.letbeclear.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.letbeclear.model.JwtAuthenticationToken;
import com.letbeclear.model.JwtUser;
import com.letbeclear.token.JwtGenerator;
import com.letbeclear.token.config.JwtConfig;


public class JwtAutenticationTokenFilter extends AbstractAuthenticationProcessingFilter 
{
			//UsernamePasswordAuthenticationFilter also extends  AbstractAuthenticationProcessingFilter
	
	@Autowired
	JwtConfig jwtConfig;
	
	@Autowired
	JwtAuthenticationProvider jwtAuthenticationProvider;
	
	@Autowired
	JwtAuthenticationToken jwtAuthenticationToken;
	public JwtAutenticationTokenFilter() 
	{
		super("/rest/**");	//Check for JWT token only for those request which are having /rest/** 
		System.out.println("Inside JwtAuthenticationTokenFilter Constructor");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException 
	{
		//Inside this method we will extract token from the header
		///
		/**
		 * Performs actual authentication.
		 * <p>
		 * The implementation should do one of the following:
		 * <ol>
		 * <li>Return a populated authentication token for the authenticated user, indicating
		 * successful authentication</li>
		 * <li>Return null, indicating that the authentication process is still in progress.
		 * Before returning, the implementation should perform any additional work required to
		 * complete the process.</li>
		 * <li>Throw an <tt>AuthenticationException</tt> if the authentication process fails</li>
		*/ 
		
		String header=request.getHeader("Authorization");
		
		System.out.println("Inside JwtAuthenticationTokenFilter attempAuthentication header is "+header);
		
		String tokenStartingWord=jwtConfig.getTokenStartingWord();
		
		System.out.println("JwtAuthenticationTokenFilter attemptAuthentication() tokenStartingWord "+tokenStartingWord);
		
		if(header==null || !header.startsWith(tokenStartingWord))
		{
			throw new RuntimeException("JWT Token is missing");
		}
		
		//Check for if token in JwtAuthenticationToken is null
		
		if(jwtAuthenticationToken.getToken()==null)
		{
			throw new RuntimeException("Please Login first and Send token");
		}
		
		/*	substring(int beginIndex)
			Returns a new string that is a substring of this string.
		*/
		String authenticationToken=header.substring(6);	//This will return the whole token except "Token "
											//Here we are giving 6 in substring(6) as length of "Token " is 6
		
		JwtAuthenticationToken jwtToken=new JwtAuthenticationToken(authenticationToken);
		
		/* Here we are passing JwtAuthenticationToken object in authenticate(Authentication auth) of AuthenticationManager
		 * where as it requires  an Authentication object,
		 * Answer:- Because Our Class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken which indirectly 
		 * implements Authentication there Child class obj of JwtAuthenticationToken can be passed in 
		 * authenticate method of AuthenticationManager where it requires an Authentication object
		*/
		
		return getAuthenticationManager().authenticate(jwtToken);
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException 
	{
		System.out.println("Inside JwtAuthenticationTokenFilter successfulAuthentication()");
	
		//SecurityContextHolder.getContext().setAuthentication(authResult);
		
		super.successfulAuthentication(request, response, chain, authResult);
		
		chain.doFilter(request, response);	//On success we are sending request to the next Filter
		
//		String servletPath=request.getServletPath();
//		System.out.println("Inside JwtAuthenticationTokenFilter successfulAuthentication() servletPath is "+servletPath);
//		
//		RequestDispatcher requestDispatcher=request.getRequestDispatcher(servletPath);
//		requestDispatcher.forward(request, response);
	}
	
	@Override
	public void setAuthenticationManager(AuthenticationManager authenticationManager) 
	{
		// TODO Auto-generated method stub
		super.setAuthenticationManager(authenticationManager);
		System.out.println("Inside JwtAuthenticationTokenFilter setAuthenticationManager()");
	}
	
	@Override
	protected AuthenticationManager getAuthenticationManager() 
	{
		// TODO Auto-generated method stub
		System.out.println("Inside JwtAuthenticationTokenFilter getAuthenticationManager()");
		return super.getAuthenticationManager();
	}
}
