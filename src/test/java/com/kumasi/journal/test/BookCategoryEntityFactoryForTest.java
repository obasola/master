package com.kumasi.journal.test;

import com.kumasi.journal.domain.jpa.BookCategoryEntity;

public class BookCategoryEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public BookCategoryEntity newBookCategoryEntity() {

		Integer bookId = mockValues.nextInteger();
		Integer categoryId = mockValues.nextInteger();

		BookCategoryEntity bookCategoryEntity = new BookCategoryEntity();
		bookCategoryEntity.setBookId(bookId);
		bookCategoryEntity.setCategoryId(categoryId);
		return bookCategoryEntity;
	}
	
}
