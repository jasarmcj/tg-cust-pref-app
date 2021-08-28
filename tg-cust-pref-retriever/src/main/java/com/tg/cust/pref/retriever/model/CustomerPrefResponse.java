package com.tg.cust.pref.retriever.model;

import java.util.Date;

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
@ApiModel(value = "CustomerPrefResponse", description = "Customer preference response")
public class CustomerPrefResponse {

	@ApiModelProperty(notes = "Customer id", example = "10001")
	private Long customerId;
	
	@ApiModelProperty(notes = "Customer preference type. It can be post, email or sms.", example = "email")
	private String preferenceType;
	
	@ApiModelProperty(notes = "Customer preference for a give marketting type. It can be true or false", example = "true")
	private Boolean optIn;
	
	@ApiModelProperty(notes = "Customer preference create date.", example = "2021-08-27T07:35:11.226+00:00")
	private Date createdDate;
	
	@ApiModelProperty(notes = "Customer preference modified date.", example = "2021-08-27T07:35:11.226+00:00")
	private Date modifiedDate;
}
