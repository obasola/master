package com.kumasi.journal.test;

import com.kumasi.journal.domain.Book;

public class BookFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Book newBook() {

		Integer id = mockValues.nextInteger();

		Book book = new Book();
		book.setId(id);
		return book;
	}
	
}
