package com.kumasi.journal.test;

import com.kumasi.journal.domain.jpa.BookPublisherEntity;

public class BookPublisherEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public BookPublisherEntity newBookPublisherEntity() {

		Integer publisherId = mockValues.nextInteger();
		Integer bookId = mockValues.nextInteger();

		BookPublisherEntity bookPublisherEntity = new BookPublisherEntity();
		bookPublisherEntity.setPublisherId(publisherId);
		bookPublisherEntity.setBookId(bookId);
		return bookPublisherEntity;
	}
	
}
