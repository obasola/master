package com.kumasi.journal.test;

import com.kumasi.journal.domain.jpa.CategoryEntity;

public class CategoryEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public CategoryEntity newCategoryEntity() {

		Integer id = mockValues.nextInteger();

		CategoryEntity categoryEntity = new CategoryEntity();
		categoryEntity.setId(id);
		return categoryEntity;
	}
	
}
