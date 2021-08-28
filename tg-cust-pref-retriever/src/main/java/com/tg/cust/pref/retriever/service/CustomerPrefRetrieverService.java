package com.tg.cust.pref.retriever.service;

import java.util.List;

import com.tg.cust.pref.retriever.model.CustomerPrefResponse;

/**
 * 
 * @author jasar
 *
 */
public interface CustomerPrefRetrieverService {

	public List<CustomerPrefResponse> getCustomerPreferences(Long customerId);

	public CustomerPrefResponse getCustomerPreferenceByType(Long customerId, String preferenceType);
}
