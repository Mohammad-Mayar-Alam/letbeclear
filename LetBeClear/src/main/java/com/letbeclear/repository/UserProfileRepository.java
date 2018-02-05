package com.letbeclear.repository;

import org.springframework.data.repository.CrudRepository;

import com.letbeclear.model.UserProfile;

public interface UserProfileRepository extends CrudRepository<UserProfile, Integer> {

	public UserProfile findByUserId(long userId);

}
