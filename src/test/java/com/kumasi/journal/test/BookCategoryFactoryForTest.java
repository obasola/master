package com.kumasi.journal.test;

import com.kumasi.journal.domain.BookCategory;

public class BookCategoryFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public BookCategory newBookCategory() {

		Integer bookId = mockValues.nextInteger();
		Integer categoryId = mockValues.nextInteger();

		BookCategory bookCategory = new BookCategory();
		bookCategory.setBookId(bookId);
		bookCategory.setCategoryId(categoryId);
		return bookCategory;
	}
	
}
