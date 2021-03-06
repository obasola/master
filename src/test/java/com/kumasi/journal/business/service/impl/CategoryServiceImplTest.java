/*
 * Created on 24 Oct 2015 ( Time 23:20:28 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.journal.business.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.kumasi.journal.domain.Category;
import com.kumasi.journal.domain.jpa.CategoryEntity;
import java.util.List;
import com.kumasi.journal.business.service.mapping.CategoryServiceMapper;
import com.kumasi.journal.persistence.services.jpa.CategoryPersistenceJPA;
import com.kumasi.journal.test.CategoryFactoryForTest;
import com.kumasi.journal.test.CategoryEntityFactoryForTest;
import com.kumasi.journal.test.MockValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test : Implementation of CategoryService
 */
@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceImplTest {

	@InjectMocks
	private CategoryServiceImpl categoryService;
	@Mock
	private CategoryPersistenceJPA categoryPersistenceJPA;
	@Mock
	private CategoryServiceMapper categoryServiceMapper;
	
	private CategoryFactoryForTest categoryFactoryForTest = new CategoryFactoryForTest();

	private CategoryEntityFactoryForTest categoryEntityFactoryForTest = new CategoryEntityFactoryForTest();

	private MockValues mockValues = new MockValues();
	
	@Test
	public void findById() {
		// Given
		Integer id = mockValues.nextInteger();
		
		CategoryEntity categoryEntity = categoryPersistenceJPA.load(id);
		
		Category category = categoryFactoryForTest.newCategory();
		when(categoryServiceMapper.mapCategoryEntityToCategory(categoryEntity)).thenReturn(category);

		// When
		Category categoryFound = categoryService.findById(id);

		// Then
		assertEquals(category.getId(),categoryFound.getId());
	}

	@Test
	public void findAll() {
		// Given
		List<CategoryEntity> categoryEntitys = new ArrayList<CategoryEntity>();
		CategoryEntity categoryEntity1 = categoryEntityFactoryForTest.newCategoryEntity();
		categoryEntitys.add(categoryEntity1);
		CategoryEntity categoryEntity2 = categoryEntityFactoryForTest.newCategoryEntity();
		categoryEntitys.add(categoryEntity2);
		when(categoryPersistenceJPA.loadAll()).thenReturn(categoryEntitys);
		
		Category category1 = categoryFactoryForTest.newCategory();
		when(categoryServiceMapper.mapCategoryEntityToCategory(categoryEntity1)).thenReturn(category1);
		Category category2 = categoryFactoryForTest.newCategory();
		when(categoryServiceMapper.mapCategoryEntityToCategory(categoryEntity2)).thenReturn(category2);

		// When
		List<Category> categorysFounds = categoryService.findAll();

		// Then
		assertTrue(category1 == categorysFounds.get(0));
		assertTrue(category2 == categorysFounds.get(1));
	}

	@Test
	public void create() {
		// Given
		Category category = categoryFactoryForTest.newCategory();

		CategoryEntity categoryEntity = categoryEntityFactoryForTest.newCategoryEntity();
		when(categoryPersistenceJPA.load(category.getId())).thenReturn(null);
		
		categoryEntity = new CategoryEntity();
		categoryServiceMapper.mapCategoryToCategoryEntity(category, categoryEntity);
		CategoryEntity categoryEntitySaved = categoryPersistenceJPA.save(categoryEntity);
		
		Category categorySaved = categoryFactoryForTest.newCategory();
		when(categoryServiceMapper.mapCategoryEntityToCategory(categoryEntitySaved)).thenReturn(categorySaved);

		// When
		Category categoryResult = categoryService.create(category);

		// Then
		assertTrue(categoryResult == categorySaved);
	}

	@Test
	public void createKOExists() {
		// Given
		Category category = categoryFactoryForTest.newCategory();

		CategoryEntity categoryEntity = categoryEntityFactoryForTest.newCategoryEntity();
		when(categoryPersistenceJPA.load(category.getId())).thenReturn(categoryEntity);

		// When
		Exception exception = null;
		try {
			categoryService.create(category);
		} catch(Exception e) {
			exception = e;
		}

		// Then
		assertTrue(exception instanceof IllegalStateException);
		assertEquals("already.exists", exception.getMessage());
	}

	@Test
	public void update() {
		// Given
		Category category = categoryFactoryForTest.newCategory();

		CategoryEntity categoryEntity = categoryEntityFactoryForTest.newCategoryEntity();
		when(categoryPersistenceJPA.load(category.getId())).thenReturn(categoryEntity);
		
		CategoryEntity categoryEntitySaved = categoryEntityFactoryForTest.newCategoryEntity();
		when(categoryPersistenceJPA.save(categoryEntity)).thenReturn(categoryEntitySaved);
		
		Category categorySaved = categoryFactoryForTest.newCategory();
		when(categoryServiceMapper.mapCategoryEntityToCategory(categoryEntitySaved)).thenReturn(categorySaved);

		// When
		Category categoryResult = categoryService.update(category);

		// Then
		verify(categoryServiceMapper).mapCategoryToCategoryEntity(category, categoryEntity);
		assertTrue(categoryResult == categorySaved);
	}

	@Test
	public void delete() {
		// Given
		Integer id = mockValues.nextInteger();

		// When
		categoryService.delete(id);

		// Then
		verify(categoryPersistenceJPA).delete(id);
		
	}

}
