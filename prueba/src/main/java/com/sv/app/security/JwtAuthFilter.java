package com.sv.app.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sv.app.Utilities.Utilities;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

/**
 * Clase que ayuda a filtrar todas las peticiones que tengan seguridad se valida
 * el toquen y se procede a validar si el token es correcto o no
 */
@Log4j2
public class JwtAuthFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailService usrDetailService;
	@Autowired
	private JwtProvider provider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = getToken(request);
		if (token != null && provider.validateTkn(token)) {
			String email = provider.getInfoTkn(token);
			UserDetails usuarioDetalle = usrDetailService.loadUserByUsername(email);
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(usuarioDetalle, null,
					usuarioDetalle.getAuthorities());
			auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(auth);
		}

		filterChain.doFilter(request, response);

	}

	private String getToken(HttpServletRequest req) {
		String header = req.getHeader("Authorization");
		return Utilities.getToken(header);
	}

}
