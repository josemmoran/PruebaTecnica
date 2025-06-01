package com.sv.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;	
import org.springframework.web.bind.annotation.RestController;

import com.sv.app.dto.request.ReqTransactionAccount;
import com.sv.app.dto.request.RespErrorModel;
import com.sv.app.dto.response.RespBadRequest;
import com.sv.app.service.TransactionService;
import com.sv.app.validation.ValidationRequest;
import com.sv.app.dto.error.Error;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;





@RestController
@RequestMapping("/bank/atm")
@Log4j2
public class AtmController {
	
	@Autowired
	private TransactionService service;

	@PreAuthorize("@validationAccount.preValidate(#account, authentication)")
	@GetMapping(path = "/mostrarSaldo/{account}")
	public ResponseEntity<Object> getBalance(@PathVariable("account") String account){
			return new ResponseEntity<Object>(service.getBalanceAccount(account), HttpStatus.OK);
			
		
	}
	
	@PreAuthorize("@validationAccount.preValidate(#account, authentication)")
	@PostMapping(path = "/transaccionAccount/{account}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> withdrawCash(@Valid @RequestBody ReqTransactionAccount req,@PathVariable("account") String account,BindingResult validation){
		if(validation.hasErrors()){
			List<Error> errors = ValidationRequest.validationRequest(req);
			throw new RespBadRequest(errors);
		}
		if(!account.equalsIgnoreCase(req.numeroCuenta())) {
			throw new RespErrorModel("error en validacion de cuenta", "error en la cuenta contactar al administrador");
		}
		return new ResponseEntity<>(service.createTransaction(req), HttpStatus.OK);
	}
	
	@PreAuthorize("@validationAccount.preValidate(#account, authentication)")
	@GetMapping(path = "/mostrarTransaccion/{account}")
	public ResponseEntity<Object> getTransaction(@PathVariable("account") String account){
		return new ResponseEntity<Object>(service.getTransaction(account), HttpStatus.OK);
	}
	
	
}
