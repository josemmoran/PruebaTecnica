package com.sv.app.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ReqLogin(
		@NotNull
		@NotEmpty
		@Email(message = "campo de tipo email")
		@JsonProperty("email")
		String email,
		@NotNull
		@NotEmpty
		@JsonProperty("pin")
		@Pattern(regexp = "^\\d{4}$",message = "El campo %s unicamente acepta numeros y longitud maxima 4")
		String pin
		) {

}
