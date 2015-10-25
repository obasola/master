package com.kumasi.journal.test;

import com.kumasi.journal.domain.jpa.BookEntity;

public class BookEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public BookEntity newBookEntity() {

		Integer id = mockValues.nextInteger();

		BookEntity bookEntity = new BookEntity();
		bookEntity.setId(id);
		return bookEntity;
	}
	
}
