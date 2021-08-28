package com.tg.cust.pref.retriever.model;

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
@ApiModel(value = "ErrorResponse", description = "Generic error response")
public class ErrorResponse {

	@ApiModelProperty(notes = "Error message", example = "Exception occured while executing the request.")
	private String errorMessage;
}
