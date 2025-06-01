package com.sv.app.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sv.app.Utilities.Utilities;
import com.sv.app.dto.request.ReqRegisterClient;
import com.sv.app.dto.request.RespErrorModel;
import com.sv.app.dto.response.RespLogin;
import com.sv.app.dto.response.RespRegisterClient;
import com.sv.app.entity.Account;
import com.sv.app.entity.UserApp;
import com.sv.app.repository.AccountRepository;
import com.sv.app.repository.UserAppRepository;
import com.sv.app.security.JwtProvider;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UserService {
	 private AuthenticationManager authenticationManager;
	 private PasswordEncoder passwordEncoder;
	 private AccountRepository accountReposiroty;
	 private UserAppRepository userRepository;
	 private JwtProvider jwtProvider;
	 
	public UserService(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder,
			AccountRepository accountReposiroty, UserAppRepository userRepository, JwtProvider jwtProvider) {
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
		this.accountReposiroty = accountReposiroty;
		this.userRepository = userRepository;
		this.jwtProvider = jwtProvider;
	}

	/**
	 * Realiza las validaciones correspondientes para realizar el guardado del usuario en la aplicacion 
	 * correctamente
	 * @param req
	 * @return
	 */
	public RespRegisterClient registerUser(ReqRegisterClient req) {
		Optional<UserApp> userValid = userRepository.findByEmail(req.email());
		BigDecimal saldoInicial = new BigDecimal(req.saldoInicial()).setScale(2, RoundingMode.HALF_UP);
		String accountNumber = Utilities.createAccountNumber();
		if(saldoInicial.compareTo(new BigDecimal(0.00)) == 0 || saldoInicial.compareTo(new BigDecimal(0.00)) == -1){
			throw new RespErrorModel("Saldo inicial incorrecto","El saldo inicial tiene que ser mayor a cero");
		}
	
		if(userValid.isPresent()) {
			UserApp user = userValid.get();
			Account account = new Account(accountNumber, saldoInicial);
			accountReposiroty.save(account);
			
			user.getRoles().add(account); 
			userRepository.save(user);
			return new RespRegisterClient(req.nombreCompleto(), accountNumber, req.saldoInicial(), req.email());
		}
		else {
			Account account = new Account(accountNumber, saldoInicial);
			accountReposiroty.save(account);
			
			UserApp user = new UserApp();
			user.setEmail(req.email());
			user.setFullName(req.nombreCompleto());
			user.setPassword(passwordEncoder.encode(req.pin()));
			user.setRoles(Arrays.asList(account));
			
			userRepository.save(user);
			return new RespRegisterClient(req.nombreCompleto(), accountNumber, req.saldoInicial(), req.email());
		}
		
		
	}
	
	public RespLogin validateUserLogin(String email, String pin) {
		Optional<UserApp> user = userRepository.findByEmail(email);
		if(!user.isPresent()) {
			throw new RespErrorModel("Ususario invalido", "El usuario no existe en el sistema");
		}
		if(!passwordEncoder.matches(pin,user.get().getPassword())) {
			throw new RespErrorModel("Pin invalido", "El pin colocado es invalido");
		}
			Authentication auth = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(email, pin));
			SecurityContextHolder.getContext().setAuthentication(auth);
			return new RespLogin(jwtProvider.generateTkn(auth));	
	}
	
	
	
	
	
}
