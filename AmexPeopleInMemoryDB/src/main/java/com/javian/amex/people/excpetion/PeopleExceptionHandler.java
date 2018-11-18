package com.javian.amex.people.excpetion;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.javian.amex.people.constant.PeopleConstant;


public class PeopleExceptionHandler {//extends ResponseEntityExceptionHandler {

  public final static ResponseEntity<Object> PeopleUnprocessableEntityException(String message, String exception) {
		ExceptionResponse responseBody = new ExceptionResponse(new Date(), "422", PeopleConstant.UNPROCESSABLE_ENTITY,  message, exception);
		return new ResponseEntity<>(responseBody, HttpStatus.UNPROCESSABLE_ENTITY);

  }

}

