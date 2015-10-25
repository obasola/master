package com.kumasi.journal.test;

import com.kumasi.journal.domain.jpa.EntrytypeEntity;

public class EntrytypeEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public EntrytypeEntity newEntrytypeEntity() {

		Integer id = mockValues.nextInteger();

		EntrytypeEntity entrytypeEntity = new EntrytypeEntity();
		entrytypeEntity.setId(id);
		return entrytypeEntity;
	}
	
}
