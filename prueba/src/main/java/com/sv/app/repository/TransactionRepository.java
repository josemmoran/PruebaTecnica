package com.sv.app.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sv.app.entity.Transaction;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface TransactionRepository extends CrudRepository<Transaction, String>{

	List<Transaction> findByNumberAccount(String numberAccount);
	
}
