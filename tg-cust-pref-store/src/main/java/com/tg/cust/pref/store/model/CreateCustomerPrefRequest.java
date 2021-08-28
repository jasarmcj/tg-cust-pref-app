package com.tg.cust.pref.store.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author jasar
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "CreateCustomerPrefRequest", description = "Create customer preference request")
public class CreateCustomerPrefRequest {

	@ApiModelProperty(notes = "Customer preference type. It can be post, email or sms.", example = "email", required = true)
	@NotEmpty
	private String preferenceType;

	@ApiModelProperty(notes = "Customer preference for a give marketting type. It can be true or false", example = "true", required = true)
	@NotNull
	private Boolean optIn;
}
