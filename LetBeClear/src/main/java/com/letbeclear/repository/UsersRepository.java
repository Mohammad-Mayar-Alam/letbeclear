package com.letbeclear.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.letbeclear.model.Users;

@Repository("usersRepository")
public interface UsersRepository extends JpaRepository<Users, Long> 
{
	//Optional<Users> findByEmail(String email);
	public Users findByUserId(Long userId);

}
