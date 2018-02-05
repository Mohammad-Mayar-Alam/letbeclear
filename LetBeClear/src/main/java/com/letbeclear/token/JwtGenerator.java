package com.letbeclear.token;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.letbeclear.model.JwtUser;
import com.letbeclear.token.config.JwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtGenerator 
{
	@Autowired 
	JwtUser jwtUser;
	
	@Autowired
	JwtConfig jwtConfig;
	
	//JwtUser jwtUser, JwtConfig jwtConfig
	public String generate()
	{
		System.out.println("Inside JwtGenerator generate()");
		
		long expirationTimeInMiliSecond=jwtConfig.getExpirationTimeInMiliSecond();
		String secretKey=jwtConfig.getSecretKey();
		
		System.out.println("Inside JwtGenerator expirationTimeInMiliSecond "+expirationTimeInMiliSecond);
		System.out.println("Inside JwtGenerator secretKey "+secretKey);
		
		Claims claims=Jwts.claims()
						.setSubject(jwtUser.getEmail());
			   
		claims.put("userId", String.valueOf(jwtUser.getUserId()) );	//Converting Integer value to String as
																	//we need to pass String in value field
		claims.put("role", jwtUser.getRole());
		
		//claims.put("tokenGeneratedTime", System.currentTimeMillis());
		
		String token = Jwts.builder()
						.setClaims(claims)
						.setExpiration(new Date(System.currentTimeMillis() + expirationTimeInMiliSecond ))	//setting 1800000 the expiration time for JWT
						.signWith(SignatureAlgorithm.HS512, secretKey)	//Signing the token with a key
						.compact();	//making the token small	
		return token;
	}
}
