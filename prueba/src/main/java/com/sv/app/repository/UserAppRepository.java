package com.sv.app.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sv.app.entity.UserApp;

import jakarta.transaction.Transactional;
import java.util.List;
import com.sv.app.entity.Account;



@Repository
@Transactional
public interface UserAppRepository extends CrudRepository<UserApp, UUID>{

	Optional<UserApp> findByEmail(String email);
	UserApp findByRoles(List<Account> roles);
	
}
