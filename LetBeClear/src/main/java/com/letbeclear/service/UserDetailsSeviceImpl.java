package com.letbeclear.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.letbeclear.model.UserReg;
import com.letbeclear.repository.UserRegRepository;

@Service
public class UserDetailsSeviceImpl implements UserDetailsService
{
	@Autowired
	UserRegRepository userRegRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		//here username will be email of the UserReg class
		UserReg userReg=userRegRepository.findByLoginid(username);
		
		if(userReg==null)
		{
			throw new UsernameNotFoundException(username);
		}
		else
		{
			//returning the UserDetailsImpl Object but the return type of this
			//method is UserDetails and we also know that the class UserDetails extends User
			//and class User implements UserDetails 
			//therefore Child Object can be catched in Parent Object i.e Upcasting 
			
			//AND THIS IS THE PRINCIPAL INSIDE THE AUTHENTICATION OBJECT in fo
			return new com.letbeclear.security.UserDetailsImpl(userReg);
		}
	}

}
