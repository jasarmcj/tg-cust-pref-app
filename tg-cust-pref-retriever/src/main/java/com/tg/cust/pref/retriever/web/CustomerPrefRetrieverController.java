package com.tg.cust.pref.retriever.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tg.cust.pref.retriever.model.CustomerPrefResponse;
import com.tg.cust.pref.retriever.web.api.CustomerPrefRetrieverControllerApi;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author jasar
 *
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/customers")
public class CustomerPrefRetrieverController implements CustomerPrefRetrieverControllerApi {
	
	@Autowired
	private com.tg.cust.pref.retriever.service.CustomerPrefRetrieverService customerPrefStoreService;

	@GetMapping("{customerId}/preferences")
	@Override
	public ResponseEntity<List<CustomerPrefResponse>> getCustomerPreferences(
			@PathVariable(value = "customerId", required = true) Long customerId) {

		log.info("Request received to retrieve all preferences");
		List<CustomerPrefResponse> customerPreferences = customerPrefStoreService.getCustomerPreferences(customerId);
		return ResponseEntity.ok().body(customerPreferences);
	}

	@GetMapping("{customerId}/preferences/{preferenceType}")
	@Override
	public ResponseEntity<CustomerPrefResponse> getCustomerPreferenceByType(
			@PathVariable(value = "customerId", required = true) Long customerId,
			@PathVariable(value = "preferenceType", required = true) String preferenceType) {

		log.info("Request received to retrieve preference of type - {}", preferenceType);
		CustomerPrefResponse customerPrefResponse = customerPrefStoreService.getCustomerPreferenceByType(customerId,
				preferenceType);
		return ResponseEntity.ok().body(customerPrefResponse);
	}

}
