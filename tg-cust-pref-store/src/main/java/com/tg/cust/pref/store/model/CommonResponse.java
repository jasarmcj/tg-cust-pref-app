package com.tg.cust.pref.store.model;

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
@ApiModel(value = "CommonResponse", description = "Generic success response")
public class CommonResponse {

	@ApiModelProperty(notes = "Message", example = "Successfully completed the operation.")
	private String message;
}
