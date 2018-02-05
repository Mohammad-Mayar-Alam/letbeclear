package com.letbeclear.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.letbeclear.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer>
{
	public Address findByEmail(String email);
	
	//Pappu Pandit Code
	@Query("select u from Address u where u.users.userId=?1")
	public Address findByUserId(Long userId);
}
