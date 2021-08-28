package com.tg.cust.pref.retriever.feign.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tg.cust.pref.retriever.model.CustomerPrefResponse;

/**
 * Feign client to call apis in Store mictoservice
 * @author jasar
 *
 */
@FeignClient(value = "customerPrefRetrieverClient", url = "${custPrefStore.baseurl}")
public interface CustomerPrefStoreClient {

	/**
	 * Feign client to call customer preferences GET api in Store microservice
	 * 
	 * @param customerId
	 * @return List<CustomerPrefResponse>
	 */
	@RequestMapping(method = RequestMethod.GET, value = "${custPrefStore.preferences}")
	public List<CustomerPrefResponse> getCustomerPreferences(@PathVariable("customerId") Long customerId);

	/**
	 * Feign client to call customer preferences by type GET api in Store microservice
	 * 
	 * @param customerId
	 * @param preferenceType
	 * @return CustomerPrefResponse
	 */
	@RequestMapping(method = RequestMethod.GET, value = "${custPrefStore.preferencesByType}")
	public CustomerPrefResponse getCustomerPreferenceByType(@PathVariable("customerId") Long customerId,
			@PathVariable("preferenceType") String preferenceType);
}
