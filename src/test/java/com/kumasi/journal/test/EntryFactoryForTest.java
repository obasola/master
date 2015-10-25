package com.kumasi.journal.test;

import com.kumasi.journal.domain.Entry;

public class EntryFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Entry newEntry() {

		Integer identry = mockValues.nextInteger();

		Entry entry = new Entry();
		entry.setIdentry(identry);
		return entry;
	}
	
}
