package com.sv.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sv.app.dto.request.ReqLogin;
import com.sv.app.dto.request.ReqRegisterClient;
import com.sv.app.dto.response.RespBadRequest;
import com.sv.app.dto.response.RespRegisterClient;
import com.sv.app.service.UserService;
import com.sv.app.validation.ValidationRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.sv.app.dto.error.Error;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/bank/auth")
@Log4j2
@Tag(name="User authentication", description = "Uso para crear y logear usuario e iniciar con atm")
public class AuthController {

	private UserService service;
	
	public AuthController(UserService service) {
		this.service = service;
	}

	@Operation(summary = "Agregar nuevo cliente",description = "crea usuario y cuenta para poder realizar inicio de sesion ",
			tags = {"nuevoUsuario"},
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					required = true,
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
					schema = @Schema(implementation = ReqRegisterClient.class))
					),
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Respuesta correcta",
							content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
									schema = @Schema(implementation = RespRegisterClient.class))
							
							)
					
			}
			)
	@PostMapping(path = "/nuevoCliente", produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> createUser(@Valid @RequestBody ReqRegisterClient request,BindingResult validation){
		log.info("creando nuevo usuario");
			
		if(validation.hasErrors()) {
			log.info("iniciando validacion de cmapo");
			List<Error> errors = ValidationRequest.validationRequest(request);
			throw new RespBadRequest(errors);
		}
		RespRegisterClient client = service.registerUser(request);
		
		return new ResponseEntity<>(client,HttpStatus.OK);
		
	}
	
	@Operation(summary = "Inicio de nuevo cliente")
	@PostMapping(path = "/login",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getSaldo(@Valid @RequestBody ReqLogin req,BindingResult validation){
		log.info("");
		if(validation.hasErrors()) {
			List<Error> errors =ValidationRequest.validationRequest(req);
			throw new RespBadRequest(errors);
		}
		return new ResponseEntity<Object>(service.validateUserLogin(req.email(), req.pin()), HttpStatus.OK);
		
	}
	
	
	
	
}
