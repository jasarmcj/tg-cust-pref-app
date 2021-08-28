package com.tg.cust.pref.store.web.api;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.tg.cust.pref.store.model.CommonResponse;
import com.tg.cust.pref.store.model.ErrorResponse;
import com.tg.cust.pref.store.model.UpdateCustomerPrefRequest;
import com.tg.cust.pref.store.model.CreateCustomerPrefRequest;
import com.tg.cust.pref.store.model.CustomerPrefResponse;

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
public interface CustomerPrefStoreControllerApi {

	@ApiOperation(value = "Add customer peferences", notes = "Api to add customer peferences")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Added customer peferences", response = CustomerPrefResponse.class, responseContainer = "List"),
			@ApiResponse(code = 500, response = ErrorResponse.class, message = "Unexpected error") })
	public ResponseEntity<List<CustomerPrefResponse>> createCustomerPreferences(
			@ApiParam(value = "Customer ID", required = true, example = "1000") @PathVariable(value = "customerId", required = true) Long customerId,
			@ApiParam(value = "Customer Prefrence List", required = true) @NotEmpty(message = "Customer peferences list is empty.") @RequestBody List<@Valid CreateCustomerPrefRequest> customerPrefList);

	@ApiOperation(value = "Update customer peference", notes = "Api to update customer peference")
	@ApiResponses({
			@ApiResponse(code = 200, response = CustomerPrefResponse.class, message = "ustomer peference updated"),
			@ApiResponse(code = 500, response = ErrorResponse.class, message = "Unexpected error") })
	public ResponseEntity<CustomerPrefResponse> updateCustomerPreference(
			@ApiParam(value = "Customer ID", required = true, example = "1000") @PathVariable(value = "customerId", required = true) Long customerId,
			@ApiParam(value = "Preference Type", required = true) @PathVariable(value = "preferenceType", required = true) String preferenceType,
			@ApiParam(value = "Customer Prefrence", required = true) @NotNull(message = "Customer peferences list is empty.") @RequestBody UpdateCustomerPrefRequest customerPref);

	@ApiOperation(value = "Delete customer peference", notes = "Api to update customer peference")
	@ApiResponses({ @ApiResponse(code = 200, response = CommonResponse.class, message = "Customer peference deleted"),
			@ApiResponse(code = 500, response = ErrorResponse.class, message = "Unexpected error") })
	public ResponseEntity<CommonResponse> deleteCustomerPreference(
			@ApiParam(value = "Customer ID", required = true, example = "1000") @PathVariable(value = "customerId", required = true) Long customerId,
			@ApiParam(value = "Preference Type", required = true) @PathVariable(value = "preferenceType", required = true) String preferenceType);

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
