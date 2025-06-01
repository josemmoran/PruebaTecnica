package com.sv.app.validation;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.sv.app.dto.request.RespErrorModel;
import com.sv.app.entity.Account;
import com.sv.app.entity.UserApp;
import com.sv.app.repository.UserAppRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class ValidationAccount {
	
	@Autowired
	private UserAppRepository userRepo;
	 
	
	public boolean preValidate(String account,Authentication auth) {
		User userAuth = (User) auth.getPrincipal();
		log.info("email {}",userAuth.getUsername());
		Optional<UserApp> opUser = userRepo.findByEmail(userAuth.getUsername());
		if(opUser.isPresent()) {
			UserApp user = opUser.get();
			for (Account ac: user.getRoles()) {
				log.info("rol a validar de userApp {}",ac.getNumberAccount());
				if(ac.getNumberAccount().equalsIgnoreCase(account)) {
					return true;
				}
			}
		}
		throw new RespErrorModel("Error en cliente","Cliente no posee la cuenta associada");
		
	}
	
}
