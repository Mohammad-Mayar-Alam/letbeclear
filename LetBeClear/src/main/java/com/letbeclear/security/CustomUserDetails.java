package com.letbeclear.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.letbeclear.model.Users;

public class CustomUserDetails extends Users implements UserDetails
//public class CustomUserDetails implements UserDetails
{
	private static final long serialVersionUID = 12L;
	
	private String email;
	private long userId;
	private String token;
	private Collection<? extends GrantedAuthority> grantedAuthorities;
	
	//Constructor used by CustomerUserDetailsService
//	public CustomUserDetails(final Users users) 
//	{
//		super(users);
//	}
	
	//Constructor used by JwtAuthenticationProvider retreiveUser
	public CustomUserDetails(String email, long userId, String token, List<GrantedAuthority> grantedAuthorities) 
	{
		this.email=email;
		this.userId=userId;
		this.token=token;
		this.grantedAuthorities=grantedAuthorities;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() 
	{
		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		
		return null;
	}

	@Override
	public String getUsername() {	//used for email used as username from the login page text
		
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getEmail() {
		return email;
	}

	public Long getUserId() {
		return userId;
	}

	public String getToken() {
		return token;
	}

	public Collection<? extends GrantedAuthority> getGrantedAuthorities() {
		return grantedAuthorities;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setGrantedAuthorities(Collection<? extends GrantedAuthority> grantedAuthorities) {
		this.grantedAuthorities = grantedAuthorities;
	}

	
}
