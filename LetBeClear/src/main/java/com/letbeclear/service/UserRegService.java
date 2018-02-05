package com.letbeclear.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.letbeclear.model.UserReg;
import com.letbeclear.repository.UserRegRepository;

@Service
public class UserRegService
{
	@Autowired
	UserRegRepository userRegRepository;
	
	public UserReg getUserRegByEmail(String email)
	{
		UserReg userReg=null;
		try
		{
			System.out.println("Inside UserRegService getUserRegByEmail() email is "+email);
			
			userReg=userRegRepository.findByLoginid(email);
			
			System.out.println("Inside UserRegService getUserRegByEmail() userReg is "+userReg);
			return userReg;
		}
		catch(Exception e)
		{
			System.out.println("Exception in UserRegService getUserByEmail() ");
			e.printStackTrace();
			return null;
		}
	}
}
