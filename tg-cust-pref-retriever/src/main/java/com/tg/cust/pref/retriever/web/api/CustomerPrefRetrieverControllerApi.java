package com.tg.cust.pref.retriever.web.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.tg.cust.pref.retriever.model.CustomerPrefResponse;
import com.tg.cust.pref.retriever.model.ErrorResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * @author jasar
 *
 */
@Api(value = "Customer Preference Store", description = "Apis to store customer peferences", tags = "Customer Preference Store")
public interface CustomerPrefRetrieverControllerApi {

	@ApiOperation(value = "Get customer peferences", notes = "Api to get customer peferences")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Successfully retrieved all peferences", response = CustomerPrefResponse.class, responseContainer = "List"),
			@ApiResponse(code = 500, response = ErrorResponse.class, message = "Unexpected error") })
	public ResponseEntity<List<CustomerPrefResponse>> getCustomerPreferences(
			@ApiParam(value = "Customer ID", required = true, example = "1000") @PathVariable(value = "customerId", required = true) Long customerId);

	@ApiOperation(value = "Get customer peference", notes = "Api to get customer peference by type")
	@ApiResponses({
			@ApiResponse(code = 200, response = CustomerPrefResponse.class, message = "Successfully retrieved customer peference"),
			@ApiResponse(code = 500, response = ErrorResponse.class, message = "Unexpected error") })
	public ResponseEntity<CustomerPrefResponse> getCustomerPreferenceByType(
			@ApiParam(value = "Customer ID", required = true, example = "1000") @PathVariable(value = "customerId", required = true) Long customerId,
			@ApiParam(value = "Preference Type", required = true) @PathVariable(value = "preferenceType", required = true) String preferenceType);
}
