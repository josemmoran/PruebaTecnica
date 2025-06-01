package com.sv.app.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sv.app.Utilities.Constants;
import com.sv.app.dto.request.ReqTransactionAccount;
import com.sv.app.dto.request.RespErrorModel;
import com.sv.app.dto.response.RespTransaccionAccount;
import com.sv.app.entity.Account;
import com.sv.app.entity.Transaction;
import com.sv.app.entity.UserApp;
import com.sv.app.repository.AccountRepository;
import com.sv.app.repository.TransactionRepository;
import com.sv.app.repository.UserAppRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class TransactionService {

	@Autowired
	private AccountRepository accountRepo;
	
	@Autowired
	private TransactionRepository trxRepo;
	
	@Autowired
	private UserAppRepository userRepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public RespTransaccionAccount getBalanceAccount(String account) {
	     Optional<Account> dataAccount = accountRepo.findById(account);
	     return new RespTransaccionAccount(
	    		 UUID.randomUUID().toString(), 
	    		 Constants.TRX_CONSULTA, 
	    		 dataAccount.get().getNumberAccount(),
	    		 null,
	    		 dataAccount.get().getAccountBalance().toString(), 
	    		 LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
	}
	
	
	public RespTransaccionAccount createTransaction(ReqTransactionAccount req) {
		log.info("iniciando transaccion con cuenta {}",req.numeroCuenta());
		Optional<Account> dataAccount = accountRepo.findById(req.numeroCuenta());
		
		if(!validatePassword(dataAccount.get(), req.pin())) {
			throw new RespErrorModel("Pin invalido", "Pin no coincide con la cuenta");
		}
		
		switch (req.tipoTransaccion()) {
		case Constants.TRX_CREDITO:
			log.info("tipo de transaccion abono o credito");
			
			log.info("saldo inicial {}",dataAccount.get().getAccountBalance().toString());
			BigDecimal newBalance = new BigDecimal(req.montoTransaccion()).add(dataAccount.get().getAccountBalance()).setScale(2, RoundingMode.HALF_UP);
			Account accountCredit = new Account();
			accountCredit.setNumberAccount(dataAccount.get().getNumberAccount());
			accountCredit.setAccountBalance(newBalance);
			log.info("saldo final {}",newBalance.toString());
			accountRepo.save(accountCredit);
			
			return saveTransaction(req, newBalance);
			
		case Constants.TRX_DEBITO:
			log.info("tipo de transaccion debito o retiro");
			BigDecimal newBalanceDebit = dataAccount.get().getAccountBalance().subtract(new BigDecimal(req.montoTransaccion()));
			if(newBalanceDebit.compareTo(new BigDecimal(0.00)) == 0 || newBalanceDebit.compareTo(new BigDecimal(0.00)) == 1) {
				log.info("Continua con la transaccion");
				Account accountDebit = new Account();
				accountDebit.setNumberAccount(dataAccount.get().getNumberAccount());
				accountDebit.setAccountBalance(newBalanceDebit);
				accountRepo.save(accountDebit);
				
				return saveTransaction(req, newBalanceDebit);
			}
			else {
				log.info("saldo insuficiente para realizar transacion saldo de {}",dataAccount.get().getAccountBalance().toString());
				throw new RespErrorModel("Saldo insuficiente",String.format("saldo insuficiente para realizar transacion saldo de %s",dataAccount.get().getAccountBalance().toString().toString()));
			}
			
		default:
			log.info("tipo de transaccion no autorizada");
			throw new RespErrorModel("Tipo de transaccion",String.format("la transaccion que se ha colocado no es un tipo valido ",req.tipoTransaccion()));
		}
	}
	
	
	private RespTransaccionAccount saveTransaction(ReqTransactionAccount req,BigDecimal newBalance) {
		log.info("guardando transaccion");
		Transaction  trx = new Transaction();
		trx.setIdTransaction(UUID.randomUUID().toString());
		trx.setTypeTransaction(req.tipoTransaccion());
		trx.setDateTransaction(LocalDateTime.now());
		trx.setNumberAccount(req.numeroCuenta());
		trx.setAmount(new BigDecimal(req.montoTransaccion()).setScale(2, RoundingMode.HALF_UP));
		trx.setNewAvailableAccount(newBalance);
		
		trxRepo.save(trx);
		log.info("Transaccion guardada correctamente");
		return new RespTransaccionAccount(
				trx.getIdTransaction(), 
				trx.getTypeTransaction(), 
				trx.getNumberAccount(),
				req.montoTransaccion(), 
				trx.getNewAvailableAccount().toString(),
				trx.getDateTransaction().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
	}
	
	
	public List<RespTransaccionAccount> getTransaction(String account){
		log.info("consultando transacion");
		List<RespTransaccionAccount> trxAccount = new ArrayList<>();
		List<Transaction> trans = new ArrayList<>(); 
			trans =	trxRepo.findByNumberAccount(account);
		if(trans.isEmpty()) {
			throw new RespErrorModel("Error mostrar transacciones", "No posee transacciones");		}
		for (Transaction trx: trans) {
			RespTransaccionAccount rspTrans = 
					new RespTransaccionAccount(
							trx.getIdTransaction(), 
							trx.getTypeTransaction(), 
							trx.getNumberAccount(),
							trx.getAmount().toString(), 
							trx.getNewAvailableAccount().toString(), 
							trx.getDateTransaction().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
		
			trxAccount.add(rspTrans);
		}
		
		return trxAccount;
	}
	
	private boolean validatePassword(Account account,String pin) {
		UserApp user = userRepo.findByRoles(Arrays.asList(account));
		return encoder.matches(pin, user.getPassword());

	}
	
}
