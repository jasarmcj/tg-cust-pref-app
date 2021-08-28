package com.tg.cust.pref.store.web;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tg.cust.pref.store.model.CommonResponse;
import com.tg.cust.pref.store.model.CreateCustomerPrefRequest;
import com.tg.cust.pref.store.model.CustomerPrefResponse;
import com.tg.cust.pref.store.model.UpdateCustomerPrefRequest;
import com.tg.cust.pref.store.service.CustomerPrefStoreService;
import com.tg.cust.pref.store.web.api.CustomerPrefStoreControllerApi;

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
public class CustomerPrefStoreController implements CustomerPrefStoreControllerApi {

	@Autowired
	private CustomerPrefStoreService customerPrefStoreService;

	@PostMapping("{customerId}/preferences")
	@Override
	public ResponseEntity<List<CustomerPrefResponse>> createCustomerPreferences(
			@PathVariable(value = "customerId", required = true) Long customerId,
			@NotEmpty(message = "Customer peferences list is empty.") @RequestBody List<@Valid CreateCustomerPrefRequest> customerPrefList) {

		log.info("Request received to add preferences");
		List<CustomerPrefResponse> customerPreferences = customerPrefStoreService.createCustomerPreferences(customerId,
				customerPrefList);
		return ResponseEntity.status(HttpStatus.CREATED).body(customerPreferences);
	}

	@PutMapping("{customerId}/preferences/{preferenceType}")
	@Override
	public ResponseEntity<CustomerPrefResponse> updateCustomerPreference(
			@PathVariable(value = "customerId", required = true) Long customerId,
			@PathVariable(value = "preferenceType", required = true) String preferenceType,
			@NotNull(message = "Customer peferences list is empty.") @RequestBody UpdateCustomerPrefRequest customerPref) {

		log.info("Request received to update preference of type - {}", preferenceType);
		CustomerPrefResponse customerPrefResponse = customerPrefStoreService.updateCustomerPreference(customerId,
				preferenceType, customerPref);
		return ResponseEntity.ok().body(customerPrefResponse);
	}

	@DeleteMapping("{customerId}/preferences/{preferenceType}")
	@Override
	public ResponseEntity<CommonResponse> deleteCustomerPreference(
			@PathVariable(value = "customerId", required = true) Long customerId,
			@PathVariable(value = "preferenceType", required = true) String preferenceType) {

		log.info("Request received to delete preference of type - {}", preferenceType);
		customerPrefStoreService.deleteCustomerPreference(customerId, preferenceType);
		return ResponseEntity.ok().body(CommonResponse.builder().message("Customer preference deleted").build());
	}

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
