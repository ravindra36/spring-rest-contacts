package com.ssc.demo.error.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler 
  extends ResponseEntityExceptionHandler {
	
	   // @Validate For Validating Path Variables and Request Parameters
    @ExceptionHandler(ConstraintViolationException.class)
    public void constraintViolationException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    
    @ExceptionHandler(ContactNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(ContactNotFoundException ex, WebRequest request) {
    	ApiError errorDetails = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(),
          request.getDescription(false));
      return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
    	ApiError errorDetails = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(),
          request.getDescription(false));
      return new ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
	
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";
        JsonMappingException jme = (JsonMappingException) ex.getCause();
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, error, jme.getOriginalMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
    
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
	  MethodArgumentNotValidException ex, 
	  HttpHeaders headers, 
	  HttpStatus status, 
	  WebRequest request) {
	    List<String> errors = new ArrayList<String>();
	    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
	        errors.add(error.getField() + ": " + error.getDefaultMessage());
	    }
	    for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
	        errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
	    }
	     
	    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Error processing JSON", errors);
	    return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
	}
	
	static class ApiError {
		 
	    private HttpStatus status;
	    private String message;
	    private List<String> errors;
	    private Date timestamp;

	    public ApiError(HttpStatus status, String message, List<String> errors) {
	        super();
	        this.status = status;
	        this.message = message;
	        this.errors = errors;
	        this.timestamp=new Date();
	    }
	 
	    public ApiError(HttpStatus status, String message, String error) {
	        super();
	        this.status = status;
	        this.message = message;
	        errors = Arrays.asList(error);
	        this.timestamp=new Date();

	    }

		public HttpStatus getStatus() {
			return status;
		}

		public void setStatus(HttpStatus status) {
			this.status = status;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public List<String> getErrors() {
			return errors;
		}

		public void setErrors(List<String> errors) {
			this.errors = errors;
		}   
	}
}

