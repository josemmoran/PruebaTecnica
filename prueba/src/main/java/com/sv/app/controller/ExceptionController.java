package com.sv.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sv.app.dto.error.Error;
import com.sv.app.dto.request.RespErrorModel;
import com.sv.app.dto.response.RespBadRequest;



@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler(RespErrorModel.class)
	public ResponseEntity<Error> handlerErrorInternal(RespErrorModel resp){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new Error(resp.getTitle(), resp.getDescription()));
	}
	
	public ResponseEntity<Error> handlerErrorInternal(Exception e){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
				new Error("Error en el sistema", e.getMessage()));
	}
	
	@ExceptionHandler(RespBadRequest.class)
	public ResponseEntity<List<Error>> badRequestEntity(RespBadRequest resp){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp.getErrors());
	}
	
}
