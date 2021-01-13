package com.tessaro.moneyapi.config.security.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
	
	List<User> findByEmail(String email);
	
}
