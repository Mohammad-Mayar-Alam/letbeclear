package com.letbeclear.security;

import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.letbeclear.model.UserReg;

//This Class is Used to provide support to the UserDetailsServiceImpl class
//at the time of runtime the User class implements UserDetails
public class UserDetailsImpl extends User
{
	UserReg userReg;
	
	//Constructor used at the time of authentication
	public UserDetailsImpl(UserReg userReg)
	{
		super(userReg.getLoginid(), userReg.getLogonpassword(), Collections.emptyList());
		this.userReg=userReg;
	}

	//Constructor used at the time of authorization
	public UserDetailsImpl(String email, String password, List<? extends GrantedAuthority> grantedAuthorities) 
	{
		super(email, null, grantedAuthorities);
	}
	public UserReg getUserReg() 
	{
		return userReg;
	}

	public void setUserReg(UserReg userReg) 
	{
		this.userReg = userReg;
	}
}
