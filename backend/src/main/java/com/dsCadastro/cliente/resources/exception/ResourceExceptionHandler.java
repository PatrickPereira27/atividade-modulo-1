package com.dsCadastro.cliente.resources.exception;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dsCadastro.cliente.service.exception.DatabaseException;
import com.dsCadastro.cliente.service.exception.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	/**
	 * classe que verifica se existiu algum erro na camada de controller
	 * */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException error, HttpServletRequest request){
		StandardError standarError = new StandardError();
		standarError.setTimestamp(Instant.now());
		standarError.setStatus(HttpStatus.NOT_FOUND.value());
		standarError.setError(error.getMessage());
		standarError.setMessage(error.getMessage());
		standarError.setPath(request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body( standarError);
		
	}
	
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> database(DatabaseException error, HttpServletRequest request){
		StandardError standarError = new StandardError();
		standarError.setTimestamp(Instant.now());
		standarError.setStatus(HttpStatus.BAD_REQUEST.value());
		standarError.setError(error.getMessage());
		standarError.setMessage(error.getMessage());
		standarError.setPath(request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standarError);
		
	}
	
	
	

}
