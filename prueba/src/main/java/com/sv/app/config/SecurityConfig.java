package com.sv.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sv.app.security.JwtAuthFilter;
import com.sv.app.security.JwtEntryPoint;

/**
 * Clase de configuracion para realizar toda la configuracion de seguridad
 */

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	private JwtEntryPoint jwtEntryPoint; 
	
	@Autowired
	public SecurityConfig(JwtEntryPoint jwtEntryPoint) {
		this.jwtEntryPoint = jwtEntryPoint;
	}
	
	
	//Bean que verifica que la informacion es correcta para realizar la autenticacion del usuario
	@Bean
	AuthenticationManager AuthenticationManager(AuthenticationConfiguration authConfig) throws Exception{
		return authConfig.getAuthenticationManager();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//Se crea para incorporar el filto de seguridad de token
	@Bean
	JwtAuthFilter jwtAuthFilter() {
		return new JwtAuthFilter();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
			.cors(cors -> cors.disable())
			.authorizeHttpRequests(auth ->
					auth.requestMatchers("/bank/auth/**").permitAll()
					.requestMatchers("/swagger-ui/**").permitAll()
					.requestMatchers("/v3/api-docs/**").permitAll()
					.requestMatchers("/swagger-resources/**").permitAll()
					.requestMatchers("/configuration/**").permitAll()
					.anyRequest()
					.authenticated())
			.exceptionHandling(excep -> excep.authenticationEntryPoint(jwtEntryPoint))
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
			
				
	}
}

