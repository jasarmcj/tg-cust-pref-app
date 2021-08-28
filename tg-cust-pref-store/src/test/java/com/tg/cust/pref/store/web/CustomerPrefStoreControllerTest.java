package com.tg.cust.pref.store.web;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tg.cust.pref.store.model.CustomerPrefResponse;
import com.tg.cust.pref.store.util.TestUtil;

/**
 * 
 * @author jasar
 *
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerPrefStoreControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webappCtx;
	
	@Value("classpath:testpayloads/createpreference.json")
	private Resource createPreferenceResource;
	
	@Value("classpath:testpayloads/updatepreference.json")
	private Resource updatePreferenceResource;
	
	@Before
	public void setup() {
		
	}
	
	/**
	 * @throws Exception
	 * 
	 * Test scenario: Adding customer preferences to database with valid request
	 * Expected result: All customer preferences added to database successfully 
	 * 
	 */
	@Test
	public void testCreateCustomerPreferences() throws Exception {

		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webappCtx).alwaysDo(MockMvcResultHandlers.print())
				.build();

		String createCustPrefRequest = TestUtil.getJSONArrayStringByJSONFileName(createPreferenceResource);

		MvcResult result = this.mockMvc.perform(post("/customers/1000/preferences").contentType(MediaType.APPLICATION_JSON).content(createCustPrefRequest))
				.andDo(print()).andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty()).andReturn();
		
		String responseString = result.getResponse().getContentAsString();
		TypeReference<List<CustomerPrefResponse>> typeRef = new TypeReference<List<CustomerPrefResponse>>() {};
		List<CustomerPrefResponse> custPreferences = TestUtil.mapResult(responseString, typeRef);
		
		assertTrue(custPreferences.size() > 0);
	}

	/**
	 * @throws Exception
	 * 
	 * Test scenario: Adding customer preferences to database with invalid request
	 * Expected result: Bad request error
	 * 
	 */
	@Test
	public void testCreateCustomerPreferences_emptyrequest() throws Exception {

		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webappCtx).alwaysDo(MockMvcResultHandlers.print())
				.build();

		this.mockMvc.perform(post("/customers/1000/preferences").contentType(MediaType.APPLICATION_JSON).content("[]")).andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").isNotEmpty());
	}

	/**
	 * @throws Exception
	 * 
	 * Test scenario: Getting all the customer preferences from database
	 * Expected result: All customer preferences are retrieved successfully 
	 * 
	 */
	@Test
	public void testGetCustomerPreferences() throws Exception {

		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webappCtx).alwaysDo(MockMvcResultHandlers.print())
				.build();
		addCustomerPreferencesForTest(1001);

		MvcResult result = this.mockMvc.perform(get("/customers/1001/preferences")).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty()).andReturn();
		
		String responseString = result.getResponse().getContentAsString();
		TypeReference<List<CustomerPrefResponse>> typeRef = new TypeReference<List<CustomerPrefResponse>>() {};
		List<CustomerPrefResponse> custPreferences = TestUtil.mapResult(responseString, typeRef);
		
		assertTrue(custPreferences.size() > 0);
	}

	/**
	 * @throws Exception
	 * 
	 * Test scenario: Updating a preference of given type for a customer
	 * Expected result: Preference value is updated successfully 
	 * 
	 */
	@Test
	public void testUpdateCustomerPreference() throws Exception {

		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webappCtx).alwaysDo(MockMvcResultHandlers.print())
				.build();

		List<CustomerPrefResponse>  customerPreferences = addCustomerPreferencesForTest(1002);
		String preferenceType = customerPreferences.get(0).getPreferenceType();
		
		String updatePreferenceRequest = TestUtil.getJSONObjectStringByJSONFileName(updatePreferenceResource);
		MvcResult result = this.mockMvc.perform(put("/customers/1002/preferences/" + preferenceType).contentType(MediaType.APPLICATION_JSON).content(updatePreferenceRequest))
				.andDo(print()).andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty()).andReturn();
		
		String responseString = result.getResponse().getContentAsString();
		CustomerPrefResponse customerPreference = TestUtil.mapResult(responseString, CustomerPrefResponse.class);
		
		assertTrue(customerPreference.getPreferenceType().equals(preferenceType));
	}

	/**
	 * @throws Exception
	 * 
	 * Test scenario: Updating a preference which is does not exist in database
	 * Expected result: Resource not found response
	 * 
	 */
	@Test
	public void testUpdateCustomerPreference_notfound() throws Exception {

		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webappCtx).alwaysDo(MockMvcResultHandlers.print())
				.build();

		String updateCustomerPrefRequest = TestUtil.getJSONObjectStringByJSONFileName(updatePreferenceResource);
		this.mockMvc.perform(put("/customers/1003/preferences/sms").contentType(MediaType.APPLICATION_JSON).content(updateCustomerPrefRequest))
				.andDo(print()).andExpect(status().isNotFound())
				.andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").isNotEmpty());
	}

	/**
	 * @throws Exception
	 * 
	 * Test scenario: Getting a preference of given type for a customer
	 * Expected result: Preference details retrieved successfully 
	 * 
	 */
	@Test
	public void testGetCustomerPreferenceByType() throws Exception {

		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webappCtx).alwaysDo(MockMvcResultHandlers.print())
				.build();

		List<CustomerPrefResponse>  customerPreferences = addCustomerPreferencesForTest(1004);
		String preferenceType = customerPreferences.get(0).getPreferenceType();

		MvcResult result = this.mockMvc.perform(get("/customers/1004/preferences/" + preferenceType)).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty()).andReturn();
		
		String responseString = result.getResponse().getContentAsString();
		CustomerPrefResponse custPreference = TestUtil.mapResult(responseString, CustomerPrefResponse.class);
		
		assertTrue(custPreference.getPreferenceType().equals(preferenceType));
	}

	/**
	 * @throws Exception
	 * 
	 * Test scenario: Getting a preference which does not exist in database
	 * Expected result: Resource not found response 
	 * 
	 */
	@Test
	public void testGetCustomerPreferenceByType_notfound() throws Exception {

		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webappCtx).alwaysDo(MockMvcResultHandlers.print())
				.build();

		this.mockMvc.perform(get("/customers/1004/preferences/sms")).andDo(print()).andExpect(status().isNotFound())
				.andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").isNotEmpty());
	}
	
	/**
	 * @throws Exception
	 * 
	 * Test scenario: Deleting a preference of given type for a customer
	 * Expected result: Preference removed from database successfully 
	 * 
	 */
	@Test
	public void testDeleteCustomerPreference() throws Exception {

		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webappCtx).alwaysDo(MockMvcResultHandlers.print())
				.build();

		List<CustomerPrefResponse>  customerPreferences = addCustomerPreferencesForTest(1005);
		String preferenceType = customerPreferences.get(0).getPreferenceType();
		
		this.mockMvc.perform(delete("/customers/1005/preferences/" + preferenceType)).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());
	}

	/**
	 * @throws Exception
	 * 
	 * Test scenario: Deleting a preference which does not exists in data
	 * Expected result: Resource not found response 
	 * 
	 */
	@Test
	public void testDeleteCustomerPreference_notfound() throws Exception {

		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webappCtx).alwaysDo(MockMvcResultHandlers.print())
				.build();

		this.mockMvc.perform(delete("/customers/1005/preferences/sms")).andDo(print()).andExpect(status().isNotFound())
				.andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").isNotEmpty());
	}
	
	//Common private methods
	private List<CustomerPrefResponse> addCustomerPreferencesForTest(long customerID) throws Exception {
		String createCustPreferencesRequest = TestUtil.getJSONArrayStringByJSONFileName(createPreferenceResource);
		MvcResult result = this.mockMvc.perform(post("/customers/" + customerID + "/preferences").contentType(MediaType.APPLICATION_JSON).content(createCustPreferencesRequest))
				.andDo(print()).andExpect(status().isCreated()).andReturn();

		String responseString = result.getResponse().getContentAsString();
		TypeReference<List<CustomerPrefResponse>> typeRef = new TypeReference<List<CustomerPrefResponse>>() {};
		List<CustomerPrefResponse> custPreferences = TestUtil.mapResult(responseString, typeRef);
		
		return custPreferences;
	}
}
