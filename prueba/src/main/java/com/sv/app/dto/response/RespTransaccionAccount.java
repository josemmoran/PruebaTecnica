package com.sv.app.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public record RespTransaccionAccount(
		@JsonProperty("numeroTransaccion")
		String numeroTransaccion,
		@JsonProperty("tipoTransaccion")
		String tipoTransaccion,
		@JsonProperty("numeroCuenta")
		String numeroCuenta,
		@JsonInclude(Include.NON_NULL)
		@JsonProperty("montoTransaccion")
		String montoTransaccion,
		@JsonProperty("saldoCuenta")
		String saldoCuenta,
		@JsonProperty("fechaHora")
		String fechaHora
		) {

}
