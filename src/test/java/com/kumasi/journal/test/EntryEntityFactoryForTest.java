package com.kumasi.journal.test;

import com.kumasi.journal.domain.jpa.EntryEntity;

public class EntryEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public EntryEntity newEntryEntity() {

		Integer identry = mockValues.nextInteger();

		EntryEntity entryEntity = new EntryEntity();
		entryEntity.setIdentry(identry);
		return entryEntity;
	}
	
}
