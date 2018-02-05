package com.letbeclear.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.letbeclear.model.UserPost;

@Repository
public interface UserPostRepository extends JpaRepository<UserPost, Integer>
{
	public UserPost findByPostId(int postId);
}
