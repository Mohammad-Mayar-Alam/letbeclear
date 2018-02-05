package com.letbeclear.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letbeclear.model.Address;
import com.letbeclear.model.JwtUser;
import com.letbeclear.model.UserProfile;
import com.letbeclear.repository.AddressRepository;
import com.letbeclear.repository.UserProfileRepository;
import com.letbeclear.request.dto.RegisterRequest;

@Service
public class ProfileService {

	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired 
	JwtUser jwtUser;
	
	@Autowired
	private UserProfileRepository userProfileRepository;
	
	public RegisterRequest showUserProfile(/*RegisterRequest registerRequest*/)
	{	 
		// Address address=addressRepository.findByUserId(1L);
		 
		long userId=jwtUser.getUserId(); 
		 
		// UserProfile userProfile=userProfileRepository.findByUserId(userId);
		 
		 
		 Address address=addressRepository.findByUserId(userId);
		 UserProfile userProfile=userProfileRepository.findByUserId(userId);
		 
		 RegisterRequest registerRequest=new RegisterRequest();
		 
		 registerRequest.setFirstName(address.getFirstname());
		 System.out.println(address.getFirstname());
		 registerRequest.setLastName(address.getLastname());
		 registerRequest.setAddress1(address.getAddress1());
		 registerRequest.setAddress2(address.getAddress2());
		 registerRequest.setCity(address.getCity());
		 registerRequest.setState(address.getState());
		 registerRequest.setCountry(address.getCountry());
		 registerRequest.setZipcode(address.getZipcode());
		 registerRequest.setContact(Long.parseLong(address.getMobilephone()));
		 System.out.println("showUserProfile "+address.getEmail());
		 registerRequest.setEmail(address.getEmail());
		 registerRequest.setGender(userProfile.getGender());
		 registerRequest.setPhoto(userProfile.getPhoto());
		 registerRequest.setDisplayName(userProfile.getDisplayname());
		 registerRequest.setMaritalStatus(userProfile.getMaritalstatus());
		 
		 SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		 String dateOfBirth=format.format(userProfile.getDateofbirth());
		 System.out.println(dateOfBirth);
		 registerRequest.setDateOfBirth(dateOfBirth);
		
		 
		 return registerRequest;
	}
	
	@Transactional
	public RegisterRequest editProfile(RegisterRequest registerRequest) throws Exception
	{
		Address address=addressRepository.findByUserId(1L);
		UserProfile userProfile=userProfileRepository.findByUserId(1L);
		
		//AddressTable addressTable=addressRepository.findByUserId(registerRequest.getUserId());
		//UserProfile userProfile=userProfileRepository.findByUserId(registerRequest.getUserId());
		
		address.setFirstname(registerRequest.getFirstName());
		address.setLastname(registerRequest.getLastName());
		address.setAddress1(registerRequest.getAddress1());
		address.setAddress2(registerRequest.getAddress2());
		address.setCity(registerRequest.getCity());
		address.setState(registerRequest.getState());
		address.setCountry(registerRequest.getCountry());
		//addressTable.setEmail(registerRequest.getEmail());
		address.setZipcode(registerRequest.getZipcode());
		address.setMobilephone(String.valueOf(registerRequest.getContact()));
		
		userProfile.setGender(registerRequest.getGender());
		userProfile.setPhoto(registerRequest.getPhoto());
		userProfile.setDisplayname(registerRequest.getDisplayName());
		userProfile.setMaritalstatus(registerRequest.getMaritalStatus());
		
		SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		Date dateOfBirth=format.parse(registerRequest.getDateOfBirth());
		System.out.println(dateOfBirth);
		userProfile.setDateofbirth(dateOfBirth);
		
		addressRepository.save(address);
		userProfileRepository.save(userProfile);
		
		return showUserProfile(/*registerRequest*/);
	}
	
}
