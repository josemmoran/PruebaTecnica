package com.sv.app.validation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.sv.app.dto.request.ReqRegisterClient;

@SpringBootTest
@ActiveProfiles("dev")
class ValidationAccountTest {

	@Test
	void testValidation() {
		assertNotNull(ValidationRequest.validationRequest(new ReqRegisterClient("Prueba ////", "", "prueanba", null)));
	}
}
