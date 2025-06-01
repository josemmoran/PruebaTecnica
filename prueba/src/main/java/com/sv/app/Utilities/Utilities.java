package com.sv.app.Utilities;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class Utilities {
	
	public static String getToken(String token) {
		if(token !=null && token.startsWith("Bearer")) {
			return token.replace("Bearer ", "");
		}
		return null;
	}
	
	public static String createAccountNumber() {
		Random random = new Random();
        int numeroAleatorio = random.nextInt(900000) + 100000;
        return String.valueOf(numeroAleatorio);
	}
}
