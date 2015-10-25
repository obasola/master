package com.kumasi.journal.test;

import com.kumasi.journal.domain.jpa.PersonEntity;

public class PersonEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public PersonEntity newPersonEntity() {

		Integer idperson = mockValues.nextInteger();

		PersonEntity personEntity = new PersonEntity();
		personEntity.setIdperson(idperson);
		return personEntity;
	}
	
}
