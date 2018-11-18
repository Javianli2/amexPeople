package com.javian.amex.people.domain;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class People {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(nullable = false) 
    private Integer id;

    @Column(nullable = false) 
    private String name;

    @Column(nullable = false) 
    private int age;
    
    @Column(nullable = false) 
    private String birthdate;
    
    @Column(nullable = false, unique=true) 
    private String email;

    
//	public People(String name, int age, String birthdate, String email) {
//		super();
//		this.name = name;
//		this.age = age;
//		this.birthdate = birthdate;
//		this.email = email;
//	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.toLowerCase();
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}


}