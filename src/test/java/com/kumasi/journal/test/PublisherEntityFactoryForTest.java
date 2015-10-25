package com.kumasi.journal.test;

import com.kumasi.journal.domain.jpa.PublisherEntity;

public class PublisherEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public PublisherEntity newPublisherEntity() {

		Integer id = mockValues.nextInteger();

		PublisherEntity publisherEntity = new PublisherEntity();
		publisherEntity.setId(id);
		return publisherEntity;
	}
	
}
