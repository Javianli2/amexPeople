package com.javian.amex.people.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Pattern;
import com.javian.amex.people.domain.People;

public class PeopleUtil {

	public static boolean isValidBirthdate(String birthdate) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");			
			if (sdf.parse(birthdate, new ParsePosition(0)) != null){ //valid date format, check if it's >= today
				 
				Date birthday = sdf.parse(birthdate);
				Date today = new Date();
				return birthday.before(today) || birthday.equals(today);
		         
			}else{
				return false; //not a valid date format
			}
		} catch (ParseException e) {
			return false;
		}
	}

	public static boolean isValidEmail(String email) {

		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";
		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pat.matcher(email).matches();
	}

//	// Check for null value & invalid birthday and email format
//	public static boolean isValidPeopleRequest(People people) {
//
//		if (people == null || people.getBirthdate() == null || people.getEmail() == null || people.getName() == null
//				|| !PeopleUtil.isValidBirthdate(people.getBirthdate()) || !PeopleUtil.isValidEmail(people.getEmail())) {
//			return false;
//		}
//		return true;
//	}

	public static Integer getAge(String birthdateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date birthdate= new Date();
		Period period = null;
		try {
			birthdate = sdf.parse(birthdateStr);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			period = Period.between(birthdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return period.getYears();
	}

}
