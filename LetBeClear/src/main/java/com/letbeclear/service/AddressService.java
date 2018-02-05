package com.letbeclear.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letbeclear.model.Address;
import com.letbeclear.repository.AddressRepository;

@Service
public class AddressService 
{
	@Autowired
	AddressRepository addressTableRepository;
	
	public Address getAddressTableByEmail(String email)
	{
		return addressTableRepository.findByEmail(email);
	}
}
