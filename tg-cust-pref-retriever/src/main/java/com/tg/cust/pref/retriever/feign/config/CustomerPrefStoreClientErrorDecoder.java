package com.tg.cust.pref.retriever.feign.config;

import com.tg.cust.pref.retriever.exception.PreferenceNotFoundException;

import feign.Response;
import feign.codec.ErrorDecoder;

/**
 * Class to handle error responses from store microservice
 * @author jasar
 *
 */
public class CustomerPrefStoreClientErrorDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {
		
		if (404 == response.status()) {
            return new PreferenceNotFoundException("Preference not found");
        }
        
		return new Exception("Internal server error");
	}

}
