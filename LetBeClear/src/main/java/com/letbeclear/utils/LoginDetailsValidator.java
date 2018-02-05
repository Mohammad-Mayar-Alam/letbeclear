package com.letbeclear.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.letbeclear.model.LBCLogin;
import com.letbeclear.model.UserReg;
import com.letbeclear.service.UserRegService;

@Component
public class LoginDetailsValidator 
{	
	@Autowired
	UserRegService userRegService;
	
	public UserReg validateEmailPassword(LBCLogin login)
	{
		System.out.println("Inside LoginDetailsValidator validateEmailPassword()");
		try
		{
			String email=login.getEmail();
			String password=login.getPassword();
			
			//Validating for Null
			if(isEmailPasswordNull(email,password))
			{
				return null;
			}
			else
			{
				UserReg userReg=userRegService.getUserRegByEmail(email);
				if(userReg!=null)
				{
					if(email.trim().equals(userReg.getLoginid()) && password.trim().equals(userReg.getLogonpassword()))
					{
						return userReg;	//if email & password of db is same as sent from login page
					}
					else
					{
						return null;	//else
					}
				}
				else
				{
					return null;
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in LoginDetailsValidator in ValidateEmailPassword method");
			return null;
		}
		
		//return false;	//CHECK IT DEGAULT I AM SENDING FALSE
	}
	
	private static boolean isEmailPasswordNull(String email,String password)
	{
		System.out.println("Inside LoginDetailsValidator isEmailPasswordNull()");
		if(email.isEmpty() || email==null || password.isEmpty() || password==null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public UserReg validateEmail(String email)
	{
		System.out.println("Inside LoginDetailsValidator validateEmail()");
		try
		{
			if(email.isEmpty() || email==null)
			{
				//If email is empty
				return null;
			}
			
			UserReg userReg=userRegService.getUserRegByEmail(email);
			
			if(userReg!= null)
			{
				//User found in the database
				return userReg;
			}
			else
			{
				//User not found in the database
				return null;
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception Inside LoginDetailsValidator validateEmail method");
			e.printStackTrace();
			return null;
		}
	}
}
