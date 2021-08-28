package com.tg.cust.pref.retriever.web;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tg.cust.pref.retriever.model.CustomerPrefResponse;
import com.tg.cust.pref.retriever.util.TestUtil;

/**
 * 
 * @author jasar
 *
 */
@Disabled
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerPrefRetrieverControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webappCtx;
	
	@Test
	public void testGetCustomerPreferences() throws Exception {

		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webappCtx).alwaysDo(MockMvcResultHandlers.print())
				.build();
		MvcResult result = this.mockMvc.perform(get("/customers/100/preferences")).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty()).andReturn();
		
		String responseString = result.getResponse().getContentAsString();
		TypeReference<List<CustomerPrefResponse>> typeRef = new TypeReference<List<CustomerPrefResponse>>() {};
		List<CustomerPrefResponse> custPreferences = TestUtil.mapResult(responseString, typeRef);
		
		assertTrue(custPreferences.size() > 0);
	}
	
	@Test
	public void testGetCustomerPreferenceByType() throws Exception {

		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webappCtx).alwaysDo(MockMvcResultHandlers.print())
				.build();

		MvcResult result = this.mockMvc.perform(get("/customers/100/preferences/email")).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty()).andReturn();
		
		String responseString = result.getResponse().getContentAsString();
		CustomerPrefResponse custPreference = TestUtil.mapResult(responseString, CustomerPrefResponse.class);
		
		assertTrue(custPreference.getPreferenceType().equals("email"));
	}

	@Test
	public void testGetCustomerPreferenceByType_notfound() throws Exception {

		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webappCtx).alwaysDo(MockMvcResultHandlers.print())
				.build();

		this.mockMvc.perform(get("/customers/100/preferences/invalidtype")).andDo(print()).andExpect(status().isNotFound())
				.andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").isNotEmpty());
	}
}
