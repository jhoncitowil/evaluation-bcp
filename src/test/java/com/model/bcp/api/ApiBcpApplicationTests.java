package com.model.bcp.api;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.bcp.api.response.TipoCambioResponse;
import com.model.bcp.api.response.factory.ResponseCode;
import com.model.bcp.domain.TipoCambio;

@SpringBootTest
class ApiBcpApplicationTests {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	private ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void saveTest() throws Exception {
		TipoCambio tipoCambio = new TipoCambio(BigDecimal.TEN, "PEN", "USD");
		String jsonRequest = objectMapper.writeValueAsString(tipoCambio);

		MvcResult result = mockMvc
				.perform(post("/tipoCambio/").content(jsonRequest).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		String resultContent = result.getResponse().getContentAsString();
		TipoCambioResponse response = objectMapper.readValue(resultContent, TipoCambioResponse.class);
		assertTrue(response.getHeader().getCode().equals(ResponseCode.OK.getCode()));
	}

	@Test
	public void findAllTest() throws Exception {
		MvcResult result = mockMvc.perform(get("/tipoCambio/findAll").content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		String resultContent = result.getResponse().getContentAsString();
		TipoCambio[] response = objectMapper.readValue(resultContent, TipoCambio[].class);
		assertTrue(response.length > 0);

	}

}
