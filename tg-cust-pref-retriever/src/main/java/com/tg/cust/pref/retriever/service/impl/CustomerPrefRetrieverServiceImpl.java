package com.tg.cust.pref.retriever.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tg.cust.pref.retriever.feign.client.CustomerPrefStoreClient;
import com.tg.cust.pref.retriever.model.CustomerPrefResponse;
import com.tg.cust.pref.retriever.service.CustomerPrefRetrieverService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author jasar
 *
 */
@Slf4j
@Service
public class CustomerPrefRetrieverServiceImpl implements CustomerPrefRetrieverService {

	@Autowired
	private CustomerPrefStoreClient customerPreferenceStoreClient;

	@Override
	public List<CustomerPrefResponse> getCustomerPreferences(Long customerId) {

		log.debug("Calling store microservice to get the preferences of customer - {}", customerId);
		return customerPreferenceStoreClient.getCustomerPreferences(customerId);
	}

	@Override
	public CustomerPrefResponse getCustomerPreferenceByType(Long customerId, String preferenceType) {

		log.debug("Calling store microservice to get the preferences of customer - {}, and type - {}", customerId,
				preferenceType);
		return customerPreferenceStoreClient.getCustomerPreferenceByType(customerId, preferenceType);
	}
}
