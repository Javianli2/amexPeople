package com.javian.amex.people.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.javian.amex.people.domain.People;

public interface PeopleRepository extends CrudRepository<People, Integer> {

}