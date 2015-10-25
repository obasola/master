package com.kumasi.journal.test;

import com.kumasi.journal.domain.Publisher;

public class PublisherFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Publisher newPublisher() {

		Integer id = mockValues.nextInteger();

		Publisher publisher = new Publisher();
		publisher.setId(id);
		return publisher;
	}
	
}
