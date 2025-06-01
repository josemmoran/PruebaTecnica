package com.sv.app.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ReqTransactionAccount(
		@NotEmpty
		@NotNull
		@JsonProperty("numeroCuenta")
		@Pattern(regexp = "^\\d{6}$", message = "El campo %s solo acepta numeros")
		String numeroCuenta,
		@NotEmpty
		@NotNull
		@JsonProperty("montoTransaccion")
		@Pattern(regexp = "^(\\d{1,10}).(\\d{2})$",message = "El campo %s es tipo moneda con dos decimales")
		String montoTransaccion,
		@NotEmpty
		@NotNull
		@JsonProperty("tipoTransaccion")
		@Pattern(regexp = "^(Debito|Credito)$", message="El campo %s solo acepta palabra Debito y Credito")
		String tipoTransaccion,
		@NotEmpty
		@NotNull
		@JsonProperty("pin")
		@Pattern(regexp = "^\\d{4}$",message = "El campo %s unicamente acepta numeros y longitud maxima 4")
		String pin
		
		
		) {

}
