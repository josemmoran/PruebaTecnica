package com.sv.app.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ReqRegisterClient(
		@NotNull
		@NotEmpty
		@JsonProperty("nombreCompleto")
		@Pattern(regexp = "^[a-zA-z\\s+]*{0,50}$", message = "El campo %s solo acepta caracteres")
		String nombreCompleto,
		@NotNull
		@NotEmpty
		@JsonProperty("pin")
		@Pattern(regexp = "^\\d{4}$",message = "El campo %s unicamente acepta numeros y longitud maxima 4")
		String pin,
		@NotNull
		@NotEmpty
		@Email
		@JsonProperty("email")
		String email,
		@NotNull
		@NotEmpty
		@JsonProperty("saldoInicial")
		@Pattern(regexp = "^(\\d{1,10}).(\\d{2})$",message = "El campo %s es tipo moneda con dos decimales")
		String saldoInicial
		) {

}
