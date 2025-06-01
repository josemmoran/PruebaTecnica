package com.sv.app.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.sv.app.Utilities.Constants;
import com.sv.app.dto.request.ReqTransactionAccount;
import com.sv.app.dto.request.RespErrorModel;
import com.sv.app.entity.Account;
import com.sv.app.entity.Transaction;
import com.sv.app.entity.UserApp;
import com.sv.app.repository.AccountRepository;
import com.sv.app.repository.TransactionRepository;
import com.sv.app.repository.UserAppRepository;

@SpringBootTest
@ActiveProfiles("dev")
class TransactionServiceTest {

	@MockBean
	private AccountRepository accountRepo;
	
	@MockBean
	private TransactionRepository trxRepo;
	
	@MockBean
	private UserAppRepository userRepo;
	
	@MockBean
	private PasswordEncoder encoder;
	
	@Autowired
	private TransactionService service;
	
	
	@Test
	void testConsulta() {
		when(accountRepo.findById(anyString())).thenReturn(Optional.ofNullable(new Account("123456", new BigDecimal(82.36))));
		assertNotNull(service.getBalanceAccount("123456"));
	}
	
	@Test
	void testTrxCredit() {
		String numberAccount ="012023";
		when(accountRepo.findById(anyString())).thenReturn(Optional.ofNullable(new Account(numberAccount,new BigDecimal(150.00))));
		when(userRepo.findByRoles(anyList())).thenReturn(new UserApp(UUID.randomUUID(),"Jose Moran","jmmoran@outlook.com","123456765",Arrays.asList(new Account(numberAccount,new BigDecimal(150.00)))));
		when(encoder.matches(any(),anyString())).thenReturn(true);
		when(trxRepo.save(any())).thenReturn(new Transaction(UUID.randomUUID().toString(),Constants.TRX_CREDITO,LocalDateTime.now(),new BigDecimal(150.00),numberAccount,new BigDecimal(50.00)));
		assertNotNull(service.createTransaction(new ReqTransactionAccount(numberAccount, "50.00", "Credito", "1234")));
	}
	
	
	@Test
	void testTrxDebit() {
		String numberAccount ="012024";
		when(accountRepo.findById(anyString())).thenReturn(Optional.ofNullable(new Account(numberAccount,new BigDecimal(150.00))));
		when(userRepo.findByRoles(anyList())).thenReturn(new UserApp(UUID.randomUUID(),"Jose Moran","jmmoran@outlook.com","123456765",Arrays.asList(new Account(numberAccount,new BigDecimal(150.00)))));
		when(encoder.matches(any(),anyString())).thenReturn(true);
		when(trxRepo.save(any())).thenReturn(new Transaction(UUID.randomUUID().toString(),Constants.TRX_DEBITO,LocalDateTime.now(),new BigDecimal(100.00),numberAccount,new BigDecimal(50.00)));
		assertNotNull(service.createTransaction(new ReqTransactionAccount(numberAccount, "100.00", "Debito", "1234")));
	}
	
	@Test
	void testPinInvalid() {
		String numberAccount = "852741";
		when(accountRepo.findById(anyString())).thenReturn(Optional.ofNullable(new Account(numberAccount,new BigDecimal(50.00))));
		when(userRepo.findByRoles(anyList())).thenReturn(new UserApp(UUID.randomUUID(),"Jose Moran","jmmoran@outlook.com","123456765",Arrays.asList(new Account(numberAccount,new BigDecimal(50.00)))));
		when(encoder.matches(any(),anyString())).thenReturn(false);
		 RespErrorModel exep = assertThrows(RespErrorModel.class, () -> {
	          service.createTransaction(new ReqTransactionAccount(numberAccount, "10.00", "Debito", "1234"));
	        });
		
		 assertEquals("Pin invalido", exep.getTitle());
	}
	
	@Test
	void testTrxNameInvalid() {
		String numberAccount ="012024";
		when(accountRepo.findById(anyString())).thenReturn(Optional.ofNullable(new Account(numberAccount,new BigDecimal(150.00))));
		when(userRepo.findByRoles(anyList())).thenReturn(new UserApp(UUID.randomUUID(),"Jose Moran","jmmoran@outlook.com","123456765",Arrays.asList(new Account(numberAccount,new BigDecimal(150.00)))));
		when(encoder.matches(any(),anyString())).thenReturn(true);
		RespErrorModel exep = assertThrows(RespErrorModel.class, () -> {
	          service.createTransaction(new ReqTransactionAccount(numberAccount, "10.00", "Prueba", "1234"));
	        });
		
		 assertEquals("Tipo de transaccion", exep.getTitle());
	}
	
	
	
	
	
	@Test
	void testTrxSaldoFail() {
		String numberAccount ="012024";
		when(accountRepo.findById(anyString())).thenReturn(Optional.ofNullable(new Account(numberAccount,new BigDecimal(5.00))));
		when(userRepo.findByRoles(anyList())).thenReturn(new UserApp(UUID.randomUUID(),"Jose Moran","jmmoran@outlook.com","123456765",Arrays.asList(new Account(numberAccount,new BigDecimal(5.00)))));
		when(encoder.matches(any(),anyString())).thenReturn(true);
		RespErrorModel exep = assertThrows(RespErrorModel.class, () -> {
	          service.createTransaction(new ReqTransactionAccount(numberAccount, "10.00", "Debito", "1234"));
	        });
		
		 assertEquals("Saldo insuficiente", exep.getTitle());
	}
	
	@Test
	void testGetTransaction() {
		when(trxRepo.findByNumberAccount(anyString())).thenReturn(Arrays.asList(
				new Transaction(UUID.randomUUID().toString(), Constants.TRX_CREDITO, LocalDateTime.now(), new BigDecimal("50.00"), "123456" ,new BigDecimal("150.00"))));
		assertNotNull(service.getTransaction("123456"));
	}
}

