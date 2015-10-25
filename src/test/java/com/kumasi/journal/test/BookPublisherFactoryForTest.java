package com.kumasi.journal.test;

import com.kumasi.journal.domain.BookPublisher;

public class BookPublisherFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public BookPublisher newBookPublisher() {

		Integer publisherId = mockValues.nextInteger();
		Integer bookId = mockValues.nextInteger();

		BookPublisher bookPublisher = new BookPublisher();
		bookPublisher.setPublisherId(publisherId);
		bookPublisher.setBookId(bookId);
		return bookPublisher;
	}
	
}
