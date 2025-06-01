package com.sv.app.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.sv.app.dto.request.ReqRegisterClient;
import com.sv.app.dto.request.RespErrorModel;
import com.sv.app.entity.Account;
import com.sv.app.entity.UserApp;
import com.sv.app.repository.AccountRepository;
import com.sv.app.repository.UserAppRepository;
import com.sv.app.security.JwtProvider;

@SpringBootTest
@ActiveProfiles("dev")
class UserServiceTest {
	@MockBean
	private PasswordEncoder passwordEncoder;
	@MockBean
	private AccountRepository accountReposiroty;
	@MockBean
	private UserAppRepository userRepository;
	@MockBean
	private JwtProvider jwtProvider;
	
	@Autowired
	private UserService service;
	
	@Test
	void registerUserNew() {
		Account cuenta = new Account("963852",new BigDecimal("100.00"));
		when(userRepository.findByEmail(anyString()))
		.thenReturn(Optional.ofNullable(null));
		when(accountReposiroty.save(any())).thenReturn(cuenta);
		when(userRepository.save(any()))
		.thenReturn(
				new UserApp(UUID.randomUUID(),"prueba@gmail.com",
				"Jose Misael Moran Ayala",
				"fuigdsfrt57348·%543643",Arrays.asList(cuenta)));
		assertNotNull(service.registerUser(new ReqRegisterClient("Prueba", "1234", "prueba@gmail.com", "100.00")));
	}
	
	@Test
	void registerUserExist() {
		Account cuenta = new Account("963852",new BigDecimal("100.00"));
		List<Account> accounts = new ArrayList<>();
		accounts.add(cuenta);
		UserApp user = new UserApp(UUID.randomUUID(),"prueba@gmail.com",
				"Jose Misael Moran Ayala",
				"fuigdsfrt57348·%543643",accounts);
		Account cuentaNew = new Account("852741",new BigDecimal("12.00"));
		user.getRoles().add(cuentaNew);
		when(userRepository.findByEmail(anyString()))
		.thenReturn(Optional.ofNullable(user));
		when(accountReposiroty.save(any())).thenReturn(cuentaNew);
		accounts.add(cuentaNew);
		when(userRepository.save(any()))
		.thenReturn(user);
		assertNotNull(service.registerUser(new ReqRegisterClient("Prueba", "1234", "prueba@gmail.com", "100.00")));
	}

	@Test
	void SaldoErroneo() {
		List<Account> accounts = new ArrayList<>();
		UserApp user = new UserApp(UUID.randomUUID(),"prueba@gmail.com",
				"Jose Misael Moran Ayala",
				"fuigdsfrt57348·%543643",accounts);
		when(userRepository.findByEmail(anyString()))
		.thenReturn(Optional.ofNullable(user));
		RespErrorModel exep = assertThrows(RespErrorModel.class, () -> {
		          service.registerUser(new ReqRegisterClient("Prueba", "1234", "prueba@gmail.com", "-2.00"));
		        });
			
			 assertEquals("Saldo inicial incorrecto", exep.getTitle());
	}
	
	@Test
	void ErrorNotExist() {
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(null));
		RespErrorModel exep = assertThrows(RespErrorModel.class, ()-> {
			service.validateUserLogin("prueba@gmail.com", "1234");
		});
		 assertEquals("Ususario invalido", exep.getTitle());
	}
	
	@Test
	void ErrorInPassword() {
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(new UserApp(UUID.randomUUID(),"Prueba","Prueba@gmail.com","1234", Arrays.asList(new Account("123456",new BigDecimal("100.00"))))));
		when(passwordEncoder.matches(any(), anyString())).thenReturn(false);
		RespErrorModel exep = assertThrows(RespErrorModel.class, ()-> {
			service.validateUserLogin("prueba@gmail.com", "1234");
		});
		 assertEquals("Pin invalido", exep.getTitle());
	}
	
	@Test
	void passwordOk() {
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(new UserApp(UUID.randomUUID(),"Prueba","Prueba@gmail.com","1234", Arrays.asList(new Account("123456",new BigDecimal("100.00"))))));
		when(passwordEncoder.matches(any(), anyString())).thenReturn(true);
		assertNotNull(service.validateUserLogin("Prueba@gmail.com", "1234"));
		
	}
}
