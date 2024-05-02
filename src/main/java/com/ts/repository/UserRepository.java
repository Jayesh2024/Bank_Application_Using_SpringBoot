package com.ts.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ts.model.User;

@Repository
public interface UserRepository  extends JpaRepository<User, Long>{

	public Optional<User> findByAadharOrPanOrEmail(String aadhar, String pan, String email);
	public Optional<User> findByEmail(String email);
}
