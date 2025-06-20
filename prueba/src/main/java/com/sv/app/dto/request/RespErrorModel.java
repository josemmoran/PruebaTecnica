package com.sv.app.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RespErrorModel extends RuntimeException {
	
	
	private static final long serialVersionUID = 1L;
	
	private String title;
	private String description;
	
}
