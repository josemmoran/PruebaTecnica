package com.sv.app.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RespBadRequest extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private List<com.sv.app.dto.error.Error> errors;
	
}
