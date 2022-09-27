package com.iacsd.demo.exception;


import com.iacsd.demo.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;


@Slf4j
@ControllerAdvice
@ResponseBody
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ConstraintViolationException.class)
	public final ApiResponse<String> handleConstraintViolation(ConstraintViolationException ex) {
		log.error("Error Occurred {}", ex);
		List<String> errorCodes= ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).toList();
		return ApiResponse.<String>builder().success(false).body("Validation errors found").messageCode(errorCodes.get(0)).build();
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		log.error("Error Occurred {}", ex);
		List<String> errCodes = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
		return new ResponseEntity<>(ApiResponse.<String>builder().success(false).body("Validation errors found").messageCode(errCodes.get(0)).build(), HttpStatus.OK);
	}

	@ExceptionHandler(GenericException.class)
	protected ApiResponse<?> handleGenericException(GenericException ex) {
		log.error("Error Occurred {}", ex);
		return ApiResponse.builder().success(false).body(ex.getData()).message(ex.getMessage()).messageCode(ex.getMsgCode()).build();
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ApiResponse<String>> handleAnyOtherException(Exception ex) {
		log.error("Error Occurred {}", ex);
		return new ResponseEntity<>(ApiResponse.<String>builder().success(false).messageCode("ss.server.err").build(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
