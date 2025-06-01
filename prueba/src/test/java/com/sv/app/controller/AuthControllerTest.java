package com.sv.app.controller;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sv.app.config.SecurityConfig;
import com.sv.app.dto.request.ReqRegisterClient;
import com.sv.app.dto.response.RespBadRequest;
import com.sv.app.dto.response.RespRegisterClient;
import com.sv.app.security.JwtAuthFilter;
import com.sv.app.security.JwtEntryPoint;
import com.sv.app.service.UserService;


@Import(SecurityConfig.class)
@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc
@WebAppConfiguration
class AuthControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService service;
	
	@MockBean
	private JwtEntryPoint entryPoint;
	
	@MockBean
	private JwtAuthFilter authFilter;
	
	private ObjectMapper map = new ObjectMapper();
	
	@InjectMocks
	private AuthController controller;
	
	@BeforeEach
	void setUp() {
		ReflectionTestUtils.setField(controller, "service", service);
	}
	
	void createUserError() throws Exception{
		ReqRegisterClient req = new ReqRegisterClient("Prueba ////dasd", "12345", null, "");
		 MvcResult result = mockMvc.perform(post("/bank/auth/nuevoCliente").content(map.writeValueAsString(req))
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isBadRequest()).andReturn();
		 RespBadRequest badRequest = (RespBadRequest) result.getResolvedException();
		 assertNotNull(badRequest);
	}
		
	@Test
	void createUser() throws Exception{
		ReqRegisterClient req = new ReqRegisterClient("Prueba dos", "1234", "prueba@gmail.com", "6000.00");
		when(service.registerUser(any())).thenReturn(new RespRegisterClient("Prueba dos", "123456", "600.30", "prueba@gmai.com"));
		mockMvc.perform(post("/bank/auth/nuevoCliente").content(map.writeValueAsString(req))
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk());
			
	}
}
