package com.sv.app.security;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev")
class JwtProviderTest {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtProvider provider;
	
    @Test
	void testToken() {
    	String token = provider.generateTkn(authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken("josemoran1994@outlook.com","1234")));
    	String tokenBad = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqb3NlbW9yYW4xOTk0QG91dGxvb2suY29tIiwiaWF0IjoxNzQ4NTM3OTIyLCJleHAiOjE3NDg1MzgxMjJ9.0fnCHgB5rf2WtvJTrssgJEivFul-Rj_p5gRmjCgdUifK5M_2zotNKOxhcpb8CQ1dFlpw_IzVn8IwZI9-iSwIRw";
		String tokenBadTwo = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTc0ODY1NDU4OH0.QA3JLZaPNWTn75F1sVJrF4D13dLDJx-VQlmRlqW08sGUeyQQlRY_RGKeeKfJqYPLUg4sd54aNR8Pzp9e7BqPZw";
    	
		assertNotNull(token);
		assertNotNull(provider.getInfoTkn(token));
		assertTrue(provider.validateTkn(token));
		assertFalse(provider.validateTkn(tokenBad));
		assertFalse(provider.validateTkn(tokenBadTwo));
		assertFalse(provider.validateTkn(null));
    }
    
    

}
