package com.letbeclear.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.letbeclear.model.UserReg;

@Repository
public interface UserRegRepository extends JpaRepository<UserReg, Long>
{
	public UserReg findByLoginid(String email);

}
