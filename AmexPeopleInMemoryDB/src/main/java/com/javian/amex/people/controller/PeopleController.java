package com.javian.amex.people.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.javian.amex.people.domain.People;
import com.javian.amex.people.handler.PeopleHandler;


@RestController
public class PeopleController {

	@Autowired
	PeopleHandler peopleHandler;
	
	@GetMapping("/people")
	public ResponseEntity<Object> getAll(){
		return peopleHandler.getAll();
	}
	
	@PostMapping("/people")
	public ResponseEntity<Object> add(@RequestBody People people) {
		return peopleHandler.add(people);
	}
	
	@GetMapping("/people/{id}")
	public ResponseEntity<Object> getOne(@PathVariable("id") Integer id){
		return peopleHandler.getOne(id);
	}
	
	@PutMapping("/people/{id}")
	public  ResponseEntity<Object> update(@RequestBody People people, @PathVariable("id") Integer id){
		return peopleHandler.update( people, id );
	}
	
	@DeleteMapping("/people/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") Integer id){
		return peopleHandler.delete(id);
	}
}
