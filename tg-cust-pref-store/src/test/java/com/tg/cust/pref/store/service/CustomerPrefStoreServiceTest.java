package com.tg.cust.pref.store.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tg.cust.pref.store.entity.TgCustomerPreference;
import com.tg.cust.pref.store.exception.PreferenceNotFoundException;
import com.tg.cust.pref.store.model.CreateCustomerPrefRequest;
import com.tg.cust.pref.store.model.CustomerPrefResponse;
import com.tg.cust.pref.store.model.UpdateCustomerPrefRequest;
import com.tg.cust.pref.store.repository.CustomerPreferenceRepository;
import com.tg.cust.pref.store.service.impl.CustomerPrefStoreServiceImpl;

/**
 * 
 * @author jasar
 *
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerPrefStoreServiceTest {

	@Mock
	private CustomerPreferenceRepository customerPreferenceRepository;

	@InjectMocks
	private CustomerPrefStoreServiceImpl customerPrefStoreService;

	private static final String POST = "post";
	private static final String EMAIL = "email";
	private static final String SMS = "sms";

	/**
	 * Test scenario: Adding a list of customer preferences
	 * 
	 * Expected result: Customer preferences returned after adding to database.
	 * 
	 */
	@Test
	public void testCreateCustomerPreferences() {

		CreateCustomerPrefRequest createRequest1 = CreateCustomerPrefRequest.builder().optIn(true).preferenceType(EMAIL)
				.build();
		CreateCustomerPrefRequest createRequest2 = CreateCustomerPrefRequest.builder().optIn(true).preferenceType(POST)
				.build();
		CreateCustomerPrefRequest createRequest3 = CreateCustomerPrefRequest.builder().optIn(true).preferenceType(SMS)
				.build();
		List<CreateCustomerPrefRequest> customerPrefList = Arrays.asList(createRequest1, createRequest2,
				createRequest3);

		List<TgCustomerPreference> customerPrefEntityList = createMockCreateCustomerPrefEntityList();
		when(customerPreferenceRepository.saveAll(Mockito.anyIterable())).thenReturn(customerPrefEntityList);

		List<CustomerPrefResponse> createdcustomerPrefList = customerPrefStoreService.createCustomerPreferences(100L,
				customerPrefList);
		assertNotNull(createdcustomerPrefList);
	}
	
	/**
	 * Test scenario: 
	 * Retrieving all the customer preferences for a customer
	 * 
	 * Expected result: All customer preferences for a customer retrieved
	 * 
	 */
	@Test
	public void testGetCustomerPreferences() {
		
		List<TgCustomerPreference> customerPrefEntityList = createMockCreateCustomerPrefEntityList();
		when(customerPreferenceRepository.findByCustomerId(anyLong())).thenReturn(customerPrefEntityList);
		
		List<CustomerPrefResponse> createdcustomerPrefList = customerPrefStoreService.getCustomerPreferences(100L);
		assertNotNull(createdcustomerPrefList);
		assertTrue(customerPrefEntityList.size() == createdcustomerPrefList.size());
	}
	
	@Test
	public void testGetCustomerPreferenceByType() {
		
		Optional<TgCustomerPreference> optionalCustPrefEntity = Optional.of(createMockCreateCustomerPrefEntity(100L, EMAIL, true));
		when(customerPreferenceRepository.findByCustomerIdAndPreferenceType(anyLong(), anyString())).thenReturn(optionalCustPrefEntity);
		
		CustomerPrefResponse createdcustomerPref = customerPrefStoreService.getCustomerPreferenceByType(100L, EMAIL);
		assertNotNull(createdcustomerPref);
		assertTrue(createdcustomerPref.getOptIn());
	}
	
	@Test
	public void testGetCustomerPreferenceByType_not_found() {
		
		Optional<TgCustomerPreference> optionalCustPrefEntity = Optional.empty();
		when(customerPreferenceRepository.findByCustomerIdAndPreferenceType(anyLong(), anyString())).thenReturn(optionalCustPrefEntity);
		
		Assertions.assertThrows(PreferenceNotFoundException.class, () -> {
			customerPrefStoreService.getCustomerPreferenceByType(100L, EMAIL);
		});
	}
	
	@Test
	public void testUpdateCustomerPreference() {
		
		Optional<TgCustomerPreference> optionalCustPrefEntity = Optional.of(createMockCreateCustomerPrefEntity(100L, EMAIL, true));
		when(customerPreferenceRepository.findByCustomerIdAndPreferenceType(anyLong(), anyString())).thenReturn(optionalCustPrefEntity);
		
		when(customerPreferenceRepository.save(any(TgCustomerPreference.class))).thenReturn(createMockCreateCustomerPrefEntity(100L, EMAIL, false));
		
		UpdateCustomerPrefRequest customerPref = UpdateCustomerPrefRequest.builder().optIn(false).build();
		CustomerPrefResponse createdcustomerPref = customerPrefStoreService.updateCustomerPreference(100L, EMAIL, customerPref);
		assertNotNull(createdcustomerPref);
		assertFalse(createdcustomerPref.getOptIn());
	}
	
	@Test
	public void testUpdateCustomerPreference_not_found() {
		
		Optional<TgCustomerPreference> optionalCustPrefEntity = Optional.empty();
		when(customerPreferenceRepository.findByCustomerIdAndPreferenceType(anyLong(), anyString())).thenReturn(optionalCustPrefEntity);
		
		UpdateCustomerPrefRequest customerPref = UpdateCustomerPrefRequest.builder().optIn(false).build();
		Assertions.assertThrows(PreferenceNotFoundException.class, () -> {
			customerPrefStoreService.updateCustomerPreference(100L, EMAIL, customerPref);
		});
	}
	
	@Test
	public void testDleteCustomerPreference() {
		
		Optional<TgCustomerPreference> optionalCustPrefEntity = Optional.of(createMockCreateCustomerPrefEntity(100L, EMAIL, true));
		when(customerPreferenceRepository.findByCustomerIdAndPreferenceType(anyLong(), anyString())).thenReturn(optionalCustPrefEntity);
		doNothing().when(customerPreferenceRepository).delete(optionalCustPrefEntity.get());	

		boolean result = customerPrefStoreService.deleteCustomerPreference(100L, EMAIL);
		assertTrue(result);
	}
	
	@Test
	public void testDleteCustomerPreference_not_found() {
		
		Optional<TgCustomerPreference> optionalCustPrefEntity = Optional.empty();
		when(customerPreferenceRepository.findByCustomerIdAndPreferenceType(anyLong(), anyString())).thenReturn(optionalCustPrefEntity);
		
		Assertions.assertThrows(PreferenceNotFoundException.class, () -> {
			customerPrefStoreService.deleteCustomerPreference(100L, EMAIL);
		});
	}

	private List<TgCustomerPreference> createMockCreateCustomerPrefEntityList() {
		return Arrays.asList(createMockCreateCustomerPrefEntity(100L, EMAIL, true),
				createMockCreateCustomerPrefEntity(100L, POST, true),
				createMockCreateCustomerPrefEntity(100L, SMS, true));
	}

	private TgCustomerPreference createMockCreateCustomerPrefEntity(long customerId, String preferenceType,
			boolean optIn) {
		TgCustomerPreference custPrefEntity = TgCustomerPreference.builder().customerId(customerId)
				.preferenceType(preferenceType).optIn(optIn).createdDate(new Date()).build();
		return custPrefEntity;
	}
}
