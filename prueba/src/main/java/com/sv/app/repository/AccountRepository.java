package com.sv.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sv.app.entity.Account;

import jakarta.transaction.Transactional;



@Repository
@Transactional
public interface AccountRepository extends JpaRepository<Account, String>{

}
