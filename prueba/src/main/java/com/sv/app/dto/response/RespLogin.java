package com.sv.app.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RespLogin(
		@JsonProperty("token")
		String token
		) {

}
