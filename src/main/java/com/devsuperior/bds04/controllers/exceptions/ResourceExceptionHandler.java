package com.devsuperior.bds04.controllers.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> validation(
			MethodArgumentNotValidException e,
			HttpServletRequest request) {
		
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		ValidationError error = new ValidationError();
		
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError("Erro de validação!");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
		
		for (FieldError obj : e.getBindingResult().getFieldErrors()) {
			error.addError(obj.getField(), obj.getDefaultMessage());
		}
		
		return ResponseEntity.status(status).body(error);
	}
	
}