package com.kumasi.journal.test;

import com.kumasi.journal.domain.Person;

public class PersonFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Person newPerson() {

		Integer idperson = mockValues.nextInteger();

		Person person = new Person();
		person.setIdperson(idperson);
		return person;
	}
	
}
