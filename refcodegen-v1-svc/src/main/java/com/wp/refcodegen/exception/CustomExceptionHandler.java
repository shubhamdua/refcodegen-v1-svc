package com.wp.refcodegen.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex){
		ErrorResponse errorResponse=new ErrorResponse(1000, ex.getMessage());
		return new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(RecordNotFoundException.class)
	public final ResponseEntity<Object> handleRecordNotFoundException(Exception ex){
		ErrorResponse errorResponse=new ErrorResponse(1001, ex.getMessage());
		return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UserAlreadyExistsException.class)
	public final ResponseEntity<Object> handleuserAlreadyExistsException(Exception ex){
		ErrorResponse errorResponse=new ErrorResponse(1003, ex.getMessage());
		return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(InvalidAgentIdException.class)
	public final ResponseEntity<Object> handleInvalidAgentIdException(Exception ex){
		ErrorResponse errorResponse=new ErrorResponse(1004, ex.getMessage());
		return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
	}
}
