package com.kumasi.journal.test;

import com.kumasi.journal.domain.Category;

public class CategoryFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Category newCategory() {

		Integer id = mockValues.nextInteger();

		Category category = new Category();
		category.setId(id);
		return category;
	}
	
}
