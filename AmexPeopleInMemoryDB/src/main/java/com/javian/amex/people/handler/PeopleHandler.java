package com.javian.amex.people.handler;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.javian.amex.people.constant.PeopleConstant;
import com.javian.amex.people.domain.People;
import com.javian.amex.people.excpetion.ExceptionResponse;
import com.javian.amex.people.excpetion.PeopleExceptionHandler;
import com.javian.amex.people.repository.PeopleRepository;
import com.javian.amex.people.util.PeopleUtil;

@Component
public class PeopleHandler {

	@Autowired
	PeopleRepository peopleRepository;

	public ResponseEntity<Object> getAll() {
		return new ResponseEntity<>(peopleRepository.findAll(), HttpStatus.OK);
	}

	public ResponseEntity<Object> add(People people) {

		try {
			
			//Data Validation
			String validationMsg = validPeopleRequestMsg(people);
			if (!validationMsg.equals(PeopleConstant.SUCCESS)) {
				return PeopleExceptionHandler.PeopleUnprocessableEntityException(validationMsg, PeopleConstant.REQUESTED_DATA_INVALID);
			}
			
			//Save Into DB
			people.setAge(PeopleUtil.getAge(people.getBirthdate()));
			peopleRepository.save(people);

		} catch (DataIntegrityViolationException e) { // email unique constraint exception
			return PeopleExceptionHandler.PeopleUnprocessableEntityException(e.getMessage(), e.getClass().getName());
		}
		return new ResponseEntity<>(people, HttpStatus.CREATED);

	}

	public ResponseEntity<Object> getOne(Integer id) {

		People people = peopleRepository.findOne(id);

		//Record Not found in DB
		if (people == null) {
			return PeopleExceptionHandler.PeopleUnprocessableEntityException(PeopleConstant.REQUESTED_DATA_NOT_FOUND, "");
		}
		return new ResponseEntity<>(people, HttpStatus.OK);
	}

	public ResponseEntity<Object> update(People peopleNew, Integer id) {
		People peopleDB;
		try {
			 peopleDB = peopleRepository.findOne(id);
			 
			//Record Not found in DB
			if (peopleDB == null) {
				return PeopleExceptionHandler.PeopleUnprocessableEntityException(PeopleConstant.REQUESTED_DATA_NOT_FOUND, "");
			}
			
			//Data Validation
			String validationMsg = validPeopleRequestMsg(peopleNew);
			if (!validationMsg.equals(PeopleConstant.SUCCESS)) {
				return PeopleExceptionHandler.PeopleUnprocessableEntityException(validationMsg, PeopleConstant.REQUESTED_DATA_INVALID);

			}
			
			//Save into DB
			peopleNew.setId(id);
			peopleNew.setAge(PeopleUtil.getAge(peopleNew.getBirthdate()));
			peopleRepository.save(peopleNew);
			
		} catch (DataIntegrityViolationException e) { // email unique constraint exception
			return PeopleExceptionHandler.PeopleUnprocessableEntityException(e.getMessage(), e.getClass().getName());
		}
		return new ResponseEntity<>(peopleNew, HttpStatus.OK);
	}

	public ResponseEntity<Object> delete(Integer id) {
		try {
			peopleRepository.delete(id);
		} catch (EmptyResultDataAccessException e) { //Record Not found in DB
			return PeopleExceptionHandler.PeopleUnprocessableEntityException(e.getMessage(), e.getClass().getName());
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	// Check for null value & invalid birthday and email format
	public String validPeopleRequestMsg(People people) {
		
		if (people == null) {
			return PeopleConstant.PEOPLE_MISSING;
		} else if (StringUtils.isEmpty(people.getBirthdate())) {
			return PeopleConstant.BIRTHDATE_MISSING;
		} else if (StringUtils.isEmpty(people.getEmail()) ) {
			return PeopleConstant.EMAIL_MISSING;
		} else if (StringUtils.isEmpty(people.getName())) {
			return PeopleConstant.NAME_MISSING;
		} else if (!PeopleUtil.isValidBirthdate(people.getBirthdate())) {
			return PeopleConstant.BIRTHDATE_INVALID;
		} else if (!PeopleUtil.isValidEmail(people.getEmail())) {
			return PeopleConstant.EMAIL_INVALID;
		}
		return PeopleConstant.SUCCESS;
	}
}
