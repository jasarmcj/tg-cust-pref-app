package com.tg.cust.pref.retriever.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tg.cust.pref.retriever.feign.client.CustomerPrefStoreClient;
import com.tg.cust.pref.retriever.model.CustomerPrefResponse;
import com.tg.cust.pref.retriever.service.impl.CustomerPrefRetrieverServiceImpl;

/**
 * 
 * @author jasar
 *
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerPrefRetrieverServiceTest {

	@Mock
	private CustomerPrefStoreClient customerPreferenceStoreClient;

	@InjectMocks
	private CustomerPrefRetrieverServiceImpl customerPrefRetrieverService;

	private static final String POST = "post";
	private static final String EMAIL = "email";
	private static final String SMS = "sms";

	@Test
	public void testGetCustomerPreferences() {

		List<CustomerPrefResponse> customerPrefList = createMockCreateCustomerPrefList();
		when(customerPreferenceStoreClient.getCustomerPreferences(anyLong())).thenReturn(customerPrefList);

		List<CustomerPrefResponse> result = customerPrefRetrieverService.getCustomerPreferences(100L);
		assertNotNull(result);
		assertTrue(customerPrefList.size() == result.size());
	}

	@Test
	public void testGetCustomerPreferenceByType() {

		CustomerPrefResponse custPref = createMockCreateCustomerPref(100L, EMAIL, true);
		when(customerPreferenceStoreClient.getCustomerPreferenceByType(anyLong(), anyString())).thenReturn(custPref);

		CustomerPrefResponse result = customerPrefRetrieverService.getCustomerPreferenceByType(100L, EMAIL);
		assertNotNull(result);
		assertTrue(custPref.getPreferenceType().equals(result.getPreferenceType()));
	}

	private List<CustomerPrefResponse> createMockCreateCustomerPrefList() {
		return Arrays.asList(createMockCreateCustomerPref(100L, EMAIL, true),
				createMockCreateCustomerPref(100L, POST, true), createMockCreateCustomerPref(100L, SMS, true));
	}

	private CustomerPrefResponse createMockCreateCustomerPref(long customerId, String preferenceType, boolean optIn) {
		CustomerPrefResponse custPref = CustomerPrefResponse.builder().customerId(customerId)
				.preferenceType(preferenceType).optIn(optIn).createdDate(new Date()).build();
		return custPref;
	}
}
