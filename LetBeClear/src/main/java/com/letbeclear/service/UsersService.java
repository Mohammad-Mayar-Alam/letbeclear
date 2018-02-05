package com.letbeclear.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letbeclear.model.Users;
import com.letbeclear.repository.UsersRepository;

@Service
public class UsersService 
{
	@Autowired
	UsersRepository usersRepository;
	public Users getProfileType(Long userId)
	{
		System.out.println("Inside UsersService getProfileType() userId is "+userId);
		return usersRepository.findByUserId(userId);
	}
	
	public Users getUserByUserId(Long userId)
	{
		System.out.println("Inside UsersService getUserByUserId() userId is "+userId);
		return usersRepository.findByUserId(userId);
	}
}
