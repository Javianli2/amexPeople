package com.javian.amex.people;

import static org.junit.Assert.*;

import org.springframework.transaction.annotation.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.javian.amex.people.constant.PeopleConstant;
import com.javian.amex.people.domain.People;
import com.javian.amex.people.excpetion.ExceptionResponse;
import com.javian.amex.people.handler.PeopleHandler;

@Transactional
@ContextConfiguration(classes = App.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class PeopleHandlerTest {
	 
	private MockMvc mockMvc;
	private People people;
	
    @Autowired
    private PeopleHandler peopleHandler;
    
    @Before
    public void setupMock(){
    	MockitoAnnotations.initMocks(this);
    	mockMvc = MockMvcBuilders.standaloneSetup(peopleHandler).build();
    	people = new People();
    }
    
    @Test
    public void testMockCretion(){
    	assertNotNull(peopleHandler);
    }
    
    //@Rollback(false)
    @Test
    public void testGetAll(){
    	people.setName("John");
    	people.setBirthdate("2011-10-10");
    	people.setEmail("john@gmail.com");
    	ResponseEntity<Object> result2 = peopleHandler.add(people);
    	assertNotNull(result2);
    	ResponseEntity<Object> result = peopleHandler.getAll();
    	int counter=0;
    	for (Object i : (Iterable<People>)result.getBody()) {
    	    counter++;
    	}
    	assertTrue(counter>0);       	
    }
    @Test
    public void testAdd_Success(){
    	people.setName("John");
    	people.setBirthdate("2011-10-10");
    	people.setEmail("john@gmail.com");
    	ResponseEntity<Object> result = peopleHandler.add(people);
    	assertNotNull(result);
    	assertTrue("201".equals(result.getStatusCode().toString()));   
    }
  
    @Test
    public void testAdd_PeopleObjMissing(){
    	people = null;
    	ResponseEntity<Object> result = peopleHandler.add(people);
    	assertNotNull(result);
    	assertTrue("422".equals(result.getStatusCode().toString()));  
    	ExceptionResponse exceptionResponse = (ExceptionResponse) result.getBody();	
    	assertTrue(PeopleConstant.PEOPLE_MISSING.equals(exceptionResponse.getMessage().toString()));    
    }
    
    @Test
    public void testAdd_BirthdateMissing(){
    	people.setName("John");
    	people.setEmail("john@gmail.com");
    	ResponseEntity<Object> result = peopleHandler.add(people);
    	assertNotNull(result);
    	assertTrue("422".equals(result.getStatusCode().toString()));  
    	ExceptionResponse exceptionResponse = (ExceptionResponse) result.getBody();	
    	assertTrue(PeopleConstant.BIRTHDATE_MISSING.equals(exceptionResponse.getMessage().toString()));    
    }
    
    @Test
    public void testAdd_EmailMissing(){
    	people.setName("John");
    	people.setBirthdate("2011-10-10");
    	ResponseEntity<Object> result = peopleHandler.add(people);
    	assertNotNull(result);
    	assertTrue("422".equals(result.getStatusCode().toString()));  
    	ExceptionResponse exceptionResponse = (ExceptionResponse) result.getBody();	
    	assertTrue(PeopleConstant.EMAIL_MISSING.equals(exceptionResponse.getMessage().toString()));    
    }
    
    @Test
    public void testAdd_NameMissing(){
    	people.setBirthdate("2011-10-10");
    	people.setEmail("john@gmail.com");
    	ResponseEntity<Object> result = peopleHandler.add(people);
    	assertNotNull(result);
    	assertTrue("422".equals(result.getStatusCode().toString()));  
    	ExceptionResponse exceptionResponse = (ExceptionResponse) result.getBody();	
    	assertTrue(PeopleConstant.NAME_MISSING.equals(exceptionResponse.getMessage().toString()));    
    }

    @Test
    public void testAdd_EmailInvalid(){
    	people.setName("John");
    	people.setBirthdate("2011-10-11");
    	people.setEmail("johnl.com");
    	ResponseEntity<Object> result = peopleHandler.add(people);
    	assertNotNull(result);
    	assertTrue("422".equals(result.getStatusCode().toString()));  
    	ExceptionResponse exceptionResponse = (ExceptionResponse) result.getBody();	
    	assertTrue(PeopleConstant.EMAIL_INVALID.equals(exceptionResponse.getMessage().toString()));    
    }
    //@Rollback(false)
    @Test
    public void testAdd_EmailConstraint(){
    	people.setName("John");
    	people.setBirthdate("2011-10-11");
    	people.setEmail("john@gmail.com");
    	ResponseEntity<Object> result = peopleHandler.add(people);
    	assertNotNull(result);
    	
    	People people2 = new People();
    	people2.setName("John2");
    	people2.setBirthdate("2011-10-11");
    	people2.setEmail("john@gmail.com");
    	ResponseEntity<Object> result2 = peopleHandler.add(people2);
    	assertNotNull(result2);

    	assertTrue("422".equals(result2.getStatusCode().toString()));  
    	ExceptionResponse exceptionResponse = (ExceptionResponse) result2.getBody();	
    	assertTrue(exceptionResponse.getMessage().toString().contains("ConstraintViolationException"));    
    }
    
    @Test
    public void testGetOne(){
    	people.setName("John");
    	people.setBirthdate("2011-10-10");
    	people.setEmail("john@gmail.com");
    	ResponseEntity<Object> result = peopleHandler.add(people);
    	assertNotNull(result);
    	assertTrue("201".equals(result.getStatusCode().toString()));   
    	
    	ResponseEntity<Object> result2 = peopleHandler.getOne(people.getId());
    	People p2 = (People) result2.getBody();
    	assertTrue(p2.getName().equalsIgnoreCase(people.getName()));
    	assertTrue(p2.getId()==people.getId());

    }
    
    @Test
    public void testGetOne_RequestdDataNotFound(){
    	ResponseEntity<Object> result2 = peopleHandler.getOne(Integer.MAX_VALUE);
    	assertTrue("422".equals(result2.getStatusCode().toString()));  
    	ExceptionResponse exceptionResponse = (ExceptionResponse) result2.getBody();	
    	assertTrue(exceptionResponse.getMessage().toString().contains(PeopleConstant.REQUESTED_DATA_NOT_FOUND));    
    }
    
    @Test
    public void testUpdate(){
    	people.setName("John");
    	people.setBirthdate("2011-10-10");
    	people.setEmail("john@gmail.com");
    	ResponseEntity<Object> result = peopleHandler.add(people);
    	assertNotNull(result);
    	assertTrue("201".equals(result.getStatusCode().toString()));    
    	
    	People newPerson = new People();
    	newPerson.setName("John2");
    	newPerson.setBirthdate("2011-10-10");
    	newPerson.setEmail("john2@gmail.com");
    	
    	ResponseEntity<Object> result2 = peopleHandler.update(newPerson, people.getId());
    	People p2 = (People) result2.getBody();
    	assertTrue(p2.getName().equalsIgnoreCase(newPerson.getName()));
    	assertTrue(p2.getId()==people.getId());
    }
 
    @Test
    public void testUpdate_RequestdDataNotFound(){
    	ResponseEntity<Object> result2 = peopleHandler.update(people,Integer.MAX_VALUE);
    	assertTrue("422".equals(result2.getStatusCode().toString()));  
    	ExceptionResponse exceptionResponse = (ExceptionResponse) result2.getBody();	
    	assertTrue(exceptionResponse.getMessage().toString().contains(PeopleConstant.REQUESTED_DATA_NOT_FOUND));  
    }
    
    @Test
    public void testUpdate_DataValidation(){
    	people.setName("John");
    	people.setBirthdate("2011-10-10");
    	people.setEmail("john@gmail.com");
    	ResponseEntity<Object> result = peopleHandler.add(people);
    	assertNotNull(result);
    	assertTrue("201".equals(result.getStatusCode().toString()));    
     
    	
    	People newPerson = new People();
    	newPerson.setName("John2");
    	newPerson.setBirthdate("2011-10-10");
    	newPerson.setEmail("john@gm@ail.com");
    	
    	ResponseEntity<Object> result3 = peopleHandler.update(newPerson, people.getId());
    	ExceptionResponse exceptionResponse = (ExceptionResponse) result3.getBody();	
    	assertTrue(PeopleConstant.EMAIL_INVALID.equals(exceptionResponse.getMessage().toString()));    
    }

    @Test
    public void testDelete(){
    	people.setName("John");
    	people.setBirthdate("2011-10-10");
    	people.setEmail("john@gmail.com");
    	ResponseEntity<Object> result = peopleHandler.add(people);
    	assertNotNull(result);
    	assertTrue("201".equals(result.getStatusCode().toString()));    

    	
    	ResponseEntity<Object> result2 = peopleHandler.delete(people.getId());
    	assertTrue("204".equals(result2.getStatusCode().toString()));  

    }
    
    @Test
    public void testDelete_RequestdDataNotFound(){

    	ResponseEntity<Object> result2 = peopleHandler.delete(Integer.MAX_VALUE);
    	assertTrue("422".equals(result2.getStatusCode().toString()));  
    	ExceptionResponse exceptionResponse = (ExceptionResponse) result2.getBody();	
    	assertTrue(exceptionResponse.getException().toString().contains("EmptyResultDataAccessException"));   

    }
}
