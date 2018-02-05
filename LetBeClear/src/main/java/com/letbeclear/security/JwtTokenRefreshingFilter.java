//package com.letbeclear.security;
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.letbeclear.model.JwtAuthenticationToken;
//import com.letbeclear.model.JwtUser;
//import com.letbeclear.token.JwtGenerator;
//
//@Component
//@WebFilter("/rest/**")
//public class JwtTokenRefreshingFilter implements Filter 
//{
//	@Autowired
//	JwtUser jwtUser;
//	
//	@Autowired
//	JwtGenerator jwtGenerator;
//	
//	@Autowired
//	JwtAuthenticationToken jwtAuthenticationToken;
//	
//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException 
//	{
//		try
//		{
//			long tokenGeneratedTime=jwtUser.getTokenGeneratedTime();
//			
//			//Checking for time interval of the token
//			System.out.println("Inside JwtTokenRefreshingFilter doFilter() loginTime is "+tokenGeneratedTime);
//			
//			if(jwtUser!=null)
//			{
//				long currentTime=System.currentTimeMillis();
//				
//				long timeElapsed=(currentTime-tokenGeneratedTime)/( 1000 * 60 );
//				
//				System.out.println("Inside JwtTokenRefreshingFilter doFilter() timeElapsed is "+timeElapsed);
//				
//				if(timeElapsed < 25)
//				{
//					//loginTime is less than 25 minutes
//					System.out.println("Inside JwtTokenRefreshingFilter doFilter() inside if");
//				}
//				else
//				{
//					//loginTime is greater than 25 minutes
//					jwtUser.setTokenGeneratedTime(System.currentTimeMillis());
//					
//					String token=jwtGenerator.generate(jwtUser);
//					
//					//Setting new token in JwtAuthenticationToken token static variable
//					jwtAuthenticationToken.setToken(token);
//				}
//			}
//		}
//		catch(Exception e)
//		{
//			System.out.println("Exception in JwtTokenRefreshingFilter doFilter() ");
//			e.printStackTrace();
//		}
//		
//		//Chaining the ahead filters
//		chain.doFilter(request, response);
//	}
//
//	@Override
//	public void destroy() {
//		// TODO Auto-generated method stub
//
//	}
//
//}
