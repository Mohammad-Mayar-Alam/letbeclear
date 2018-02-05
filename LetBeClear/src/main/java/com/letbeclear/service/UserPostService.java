package com.letbeclear.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letbeclear.model.UserPost;
import com.letbeclear.repository.UserPostRepository;

@Service
public class UserPostService 
{
	@Autowired
	UserPostRepository userPostRepository;
	public UserPost getUserPostObjByPostId(int postId)
	{
		return userPostRepository.findByPostId(postId);
	}
}
