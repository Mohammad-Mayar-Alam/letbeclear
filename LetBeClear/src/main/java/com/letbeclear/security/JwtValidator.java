package com.letbeclear.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.letbeclear.domain.JwtUser;
import com.letbeclear.model.Users;
import com.letbeclear.service.UsersService;
import com.letbeclear.token.config.JwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtValidator 
{
	@Autowired
	UsersService usersService;
	
	@Autowired
	JwtConfig jwtConfig;
	
	@Autowired
	JwtUser jwtUser;
	
	public JwtUser validate(String token)
	{
		System.out.println("Inside JwtValidator validate() token is "+token);
		
		String secretKey=jwtConfig.getSecretKey();
		System.out.println("Inside JwtValidator validate() secretKey is "+secretKey);
		//JwtUser jwtUser=null;
		try
		{	
			Claims claims=Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		
			//checking the token is valid or not
			long userId =Long.parseLong((String)claims.get("userId"));
			System.out.println("Inside JwtValidator validate() userId is "+userId);
			//saving userId at ClassLevel for further use
			//this.userId=Long.parseLong((String)claims.get("userId"));
			if(isValidToken(userId))
			{
				//jwtUser=new JwtUser();
				
				jwtUser.setEmail(claims.getSubject());
				jwtUser.setRole((String)claims.get("role"));
				jwtUser.setUserId( Long.parseLong((String)claims.get("userId")));
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in JwtValidator validate()");
			e.printStackTrace();
		}
		System.out.println("Inside JwtValidator validate() jwtUser userId is "+jwtUser.getUserId());
		return jwtUser;
	}
	
	public boolean isValidToken(long userId)
	{
		Users user=usersService.getUserByUserId(userId);
		if(user!=null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	//getter method for getting userId of the user anywhere in project for further processing
//	public long getUserId() 
//	{
//		return userId;
//	}
}
