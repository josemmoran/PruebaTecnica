package com.sv.app.security;


import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.log4j.Log4j2;


/***
 * logica de creacion de token y validaciones que se le realizan al token
 */

@Log4j2
@Component
public class JwtProvider {
	
	@Value("${jwt.expiration}")
	private Long jwtExpiration;
	
	@Value("${jwt.key.secret}")
	private String keySignature; 

	
	public String generateTkn(Authentication auth) {
		
		
		return Jwts.builder()
				.setSubject(auth.getName())
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + jwtExpiration * 60 * 1000))
				.signWith(SignatureAlgorithm.HS512,keySignature.getBytes())
				.compact();
	}
	
	/**
	 * Extrae la informacion de cuenta sobre el usuario del token
	 * @param token
	 * @return
	 */
	public String getInfoTkn(String token) {
		return Jwts.parser()
				.setSigningKey(keySignature.getBytes())
				.parseClaimsJws(token)
				.getBody().getSubject();
	}
	
	public boolean validateTkn(String token) {
		try {
			Jwts.parser().setSigningKey(keySignature.getBytes()).parseClaimsJws(token);
			return true;
		}  catch (MalformedJwtException e) {
			log.error("El formato de token no es correcto {} ", e.getMessage());
		}
		catch (UnsupportedJwtException  e) {
			log.error("Token no soportado {} ", e.getMessage() );
		}
		catch(ExpiredJwtException e) {
			log.error("Toke expiro {}", e.getMessage());
		}
		catch(IllegalArgumentException e) {
			log.error("Token vacio {} ",e.getMessage());
		}
		catch (SignatureException e) {
			log.error("Firma no correcta {}",e.getMessage());
		}
		
		return false;
	}
	
	
}
