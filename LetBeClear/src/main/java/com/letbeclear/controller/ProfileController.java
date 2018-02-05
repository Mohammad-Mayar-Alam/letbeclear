package com.letbeclear.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.letbeclear.request.dto.RegisterRequest;
import com.letbeclear.service.ProfileService;

@RestController
public class ProfileController {
	
	@Autowired
	private ProfileService profileService;
	@CrossOrigin(origins="*")
	@RequestMapping(value="/rest/profile",method=RequestMethod.GET)
	public RegisterRequest showUserProfile(/*@RequestBody RegisterRequest registerRequest, HttpServletResponse response*/) throws IOException
	{
			return profileService.showUserProfile(/*registerRequest*/);
	}
	
	@CrossOrigin(origins="*")
	@RequestMapping(value="/rest/profile/{editProfile}",method=RequestMethod.POST)
	public RegisterRequest addUserProfile(@RequestBody RegisterRequest registerRequest) throws Exception
	{
		return profileService.editProfile(registerRequest);
	}
}
