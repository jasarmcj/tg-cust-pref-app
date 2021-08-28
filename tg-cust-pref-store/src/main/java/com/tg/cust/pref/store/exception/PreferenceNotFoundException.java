package com.tg.cust.pref.store.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author jasar
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreferenceNotFoundException extends RuntimeException {

	private String message;
}
