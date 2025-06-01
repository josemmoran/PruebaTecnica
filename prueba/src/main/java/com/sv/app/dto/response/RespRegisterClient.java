package com.sv.app.dto.response;


import com.fasterxml.jackson.annotation.JsonProperty;

public record RespRegisterClient(
		@JsonProperty("nombreCompleto")
		String nombreCompleto,
		@JsonProperty("numeroCuenta")
		String numeroCuenta,
		@JsonProperty("saldoInicial")
		String saldoInicial,
		@JsonProperty("email")
		String email
		
		) {

}
