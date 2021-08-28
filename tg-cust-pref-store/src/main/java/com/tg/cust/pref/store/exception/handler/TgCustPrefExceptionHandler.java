package com.tg.cust.pref.store.exception.handler;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.tg.cust.pref.store.exception.PreferenceNotFoundException;
import com.tg.cust.pref.store.model.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author jasar
 *
 */
@Slf4j
@ControllerAdvice
public class TgCustPrefExceptionHandler extends ResponseEntityExceptionHandler {

	// @ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(PreferenceNotFoundException.class)
	public ResponseEntity<Object> handlePreferenceNotFoundException(PreferenceNotFoundException exception) {

		log.error("Handling PreferenceNotFound exception - {}", exception.getMessage());
		return handleExceptionInternal(exception, ErrorResponse.builder().errorMessage(exception.getMessage()).build(),
				null, HttpStatus.NOT_FOUND, null);
		// return ErrorResponse.builder().errorMessage(exception.getMessage()).build();
	}
	/*
	 * @ExceptionHandler(MethodArgumentNotValidException.class) public
	 * ResponseEntity<Object>
	 * handleMethodArgumentNotValidException(MethodArgumentNotValidException
	 * exception) { log.error("Handling exception - {}", exception.getMessage());
	 * Map<String, String> errors = new HashMap<>();
	 * exception.getBindingResult().getAllErrors().forEach((error) -> { String
	 * fieldName = ((FieldError) error).getField(); String errorMessage =
	 * error.getDefaultMessage(); errors.put(fieldName, errorMessage); }); return
	 * handleExceptionInternal(exception, errors, null, HttpStatus.BAD_REQUEST,
	 * null); }
	 */

	@Override
	// @ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("Handling MethodArgumentNotValid exception - {}", exception.getMessage());
		Map<String, String> errors = new HashMap<>();
		exception.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		/*
		 * return handleExceptionInternal(exception, errors, null,
		 * HttpStatus.BAD_REQUEST, null);
		 */

		return handleExceptionInternal(
				exception, ErrorResponse.builder()
						.errorMessage("These fields are mandatory - " + errors.keySet().toString()).build(),
				null, HttpStatus.BAD_REQUEST, null);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException exception) {

		log.error("Handling ConstraintViolation exception - {}", exception.getMessage());
		return handleExceptionInternal(exception, ErrorResponse.builder().errorMessage(exception.getMessage()).build(),
				null, HttpStatus.BAD_REQUEST, null);
	}

	// @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleException(Exception exception) {

		log.error("Handling geric exception - {}", exception.getMessage());
		// return
		// ResponseEntity.ok().body(ErrorResponse.builder().errorMessage(exception.getMessage()).build());
		return handleExceptionInternal(exception, ErrorResponse.builder().errorMessage(exception.getMessage()).build(),
				null, HttpStatus.INTERNAL_SERVER_ERROR, null);
	}
}
