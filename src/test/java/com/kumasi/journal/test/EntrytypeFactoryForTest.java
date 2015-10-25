package com.kumasi.journal.test;

import com.kumasi.journal.domain.Entrytype;

public class EntrytypeFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Entrytype newEntrytype() {

		Integer id = mockValues.nextInteger();

		Entrytype entrytype = new Entrytype();
		entrytype.setId(id);
		return entrytype;
	}
	
}
