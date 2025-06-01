package com.sv.app.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

/**
 * Clase que ayuda a manejar la validacion de las url autenticadas
 *
 * 
 */

@Log4j2
@Component
public class JwtEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		log.info("Error en metodo commence authentication");
		log.info("error {} ",authException);
		
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Usuario Invalido, ingrese un usuario correcto");
		
	}

	
	
}
