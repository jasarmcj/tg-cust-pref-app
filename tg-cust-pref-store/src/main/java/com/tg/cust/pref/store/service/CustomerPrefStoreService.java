package com.tg.cust.pref.store.service;

import java.util.List;

import javax.validation.Valid;

import com.tg.cust.pref.store.model.CreateCustomerPrefRequest;
import com.tg.cust.pref.store.model.CustomerPrefResponse;
import com.tg.cust.pref.store.model.UpdateCustomerPrefRequest;

/**
 * 
 * @author jasar
 *
 */
public interface CustomerPrefStoreService {

	public List<CustomerPrefResponse> createCustomerPreferences(Long customerId,
			List<@Valid CreateCustomerPrefRequest> customerPrefList);

	public CustomerPrefResponse updateCustomerPreference(Long customerId, String preferenceType,
			UpdateCustomerPrefRequest customerPref);

	public boolean deleteCustomerPreference(Long customerId, String preferenceType);

	public List<CustomerPrefResponse> getCustomerPreferences(Long customerId);

	public CustomerPrefResponse getCustomerPreferenceByType(Long customerId, String preferenceType);
}
