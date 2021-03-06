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

import com.kumasi.journal.domain.BookPublisher;
import com.kumasi.journal.domain.jpa.BookPublisherEntity;
import com.kumasi.journal.domain.jpa.BookPublisherEntityKey;
import com.kumasi.journal.business.service.mapping.BookPublisherServiceMapper;
import com.kumasi.journal.persistence.services.jpa.BookPublisherPersistenceJPA;
import com.kumasi.journal.test.BookPublisherFactoryForTest;
import com.kumasi.journal.test.BookPublisherEntityFactoryForTest;
import com.kumasi.journal.test.MockValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test : Implementation of BookPublisherService
 */
@RunWith(MockitoJUnitRunner.class)
public class BookPublisherServiceImplTest {

	@InjectMocks
	private BookPublisherServiceImpl bookPublisherService;
	@Mock
	private BookPublisherPersistenceJPA bookPublisherPersistenceJPA;
	@Mock
	private BookPublisherServiceMapper bookPublisherServiceMapper;
	
	private BookPublisherFactoryForTest bookPublisherFactoryForTest = new BookPublisherFactoryForTest();

	private BookPublisherEntityFactoryForTest bookPublisherEntityFactoryForTest = new BookPublisherEntityFactoryForTest();

	private MockValues mockValues = new MockValues();
	
	@Test
	public void findById() {
		// Given
		Integer publisherId = mockValues.nextInteger();
		Integer bookId = mockValues.nextInteger();
		
		BookPublisherEntityKey bookPublisherEntityKey = new BookPublisherEntityKey(publisherId, bookId);
		bookPublisherEntityKey.setPublisherId(publisherId);
		bookPublisherEntityKey.setBookId(bookId);
		
		BookPublisherEntity bookPublisherEntity = bookPublisherPersistenceJPA.load(bookPublisherEntityKey);
		
		BookPublisher bookPublisher = bookPublisherFactoryForTest.newBookPublisher();
		when(bookPublisherServiceMapper.mapBookPublisherEntityToBookPublisher(bookPublisherEntity)).thenReturn(bookPublisher);

		// When
		BookPublisher bookPublisherFound = bookPublisherService.findById(publisherId, bookId);

		// Then
		assertEquals(bookPublisher.getPublisherId(),bookPublisherFound.getPublisherId());
		assertEquals(bookPublisher.getBookId(),bookPublisherFound.getBookId());
	}

	@Test
	public void findAll() {
		// Given
		List<BookPublisherEntity> bookPublisherEntitys = new ArrayList<BookPublisherEntity>();
		BookPublisherEntity bookPublisherEntity1 = bookPublisherEntityFactoryForTest.newBookPublisherEntity();
		bookPublisherEntitys.add(bookPublisherEntity1);
		BookPublisherEntity bookPublisherEntity2 = bookPublisherEntityFactoryForTest.newBookPublisherEntity();
		bookPublisherEntitys.add(bookPublisherEntity2);
		when(bookPublisherPersistenceJPA.loadAll()).thenReturn(bookPublisherEntitys);
		
		BookPublisher bookPublisher1 = bookPublisherFactoryForTest.newBookPublisher();
		when(bookPublisherServiceMapper.mapBookPublisherEntityToBookPublisher(bookPublisherEntity1)).thenReturn(bookPublisher1);
		BookPublisher bookPublisher2 = bookPublisherFactoryForTest.newBookPublisher();
		when(bookPublisherServiceMapper.mapBookPublisherEntityToBookPublisher(bookPublisherEntity2)).thenReturn(bookPublisher2);

		// When
		List<BookPublisher> bookPublishersFounds = bookPublisherService.findAll();

		// Then
		assertTrue(bookPublisher1 == bookPublishersFounds.get(0));
		assertTrue(bookPublisher2 == bookPublishersFounds.get(1));
	}

	@Test
	public void create() {
		// Given
		BookPublisher bookPublisher = bookPublisherFactoryForTest.newBookPublisher();

		BookPublisherEntity bookPublisherEntity = bookPublisherEntityFactoryForTest.newBookPublisherEntity();
		when(bookPublisherPersistenceJPA.load(bookPublisher.getPublisherId(), bookPublisher.getBookId())).thenReturn(null);
		
		bookPublisherEntity = new BookPublisherEntity();
		bookPublisherServiceMapper.mapBookPublisherToBookPublisherEntity(bookPublisher, bookPublisherEntity);
		BookPublisherEntity bookPublisherEntitySaved = bookPublisherPersistenceJPA.save(bookPublisherEntity);
		
		BookPublisher bookPublisherSaved = bookPublisherFactoryForTest.newBookPublisher();
		when(bookPublisherServiceMapper.mapBookPublisherEntityToBookPublisher(bookPublisherEntitySaved)).thenReturn(bookPublisherSaved);

		// When
		BookPublisher bookPublisherResult = bookPublisherService.create(bookPublisher);

		// Then
		assertTrue(bookPublisherResult == bookPublisherSaved);
	}

	@Test
	public void createKOExists() {
		// Given
		BookPublisher bookPublisher = bookPublisherFactoryForTest.newBookPublisher();

		BookPublisherEntity bookPublisherEntity = bookPublisherEntityFactoryForTest.newBookPublisherEntity();
		when(bookPublisherPersistenceJPA.load(bookPublisher.getPublisherId(), bookPublisher.getBookId())).thenReturn(bookPublisherEntity);

		// When
		Exception exception = null;
		try {
			bookPublisherService.create(bookPublisher);
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
		BookPublisher bookPublisher = bookPublisherFactoryForTest.newBookPublisher();

		BookPublisherEntity bookPublisherEntity = bookPublisherEntityFactoryForTest.newBookPublisherEntity();
		when(bookPublisherPersistenceJPA.load(bookPublisher.getPublisherId(), bookPublisher.getBookId())).thenReturn(bookPublisherEntity);
		
		BookPublisherEntity bookPublisherEntitySaved = bookPublisherEntityFactoryForTest.newBookPublisherEntity();
		when(bookPublisherPersistenceJPA.save(bookPublisherEntity)).thenReturn(bookPublisherEntitySaved);
		
		BookPublisher bookPublisherSaved = bookPublisherFactoryForTest.newBookPublisher();
		when(bookPublisherServiceMapper.mapBookPublisherEntityToBookPublisher(bookPublisherEntitySaved)).thenReturn(bookPublisherSaved);

		// When
		BookPublisher bookPublisherResult = bookPublisherService.update(bookPublisher);

		// Then
		verify(bookPublisherServiceMapper).mapBookPublisherToBookPublisherEntity(bookPublisher, bookPublisherEntity);
		assertTrue(bookPublisherResult == bookPublisherSaved);
	}

	@Test
	public void delete() {
		// Given
		Integer publisherId = mockValues.nextInteger();
		Integer bookId = mockValues.nextInteger();

		// When
		bookPublisherService.delete(publisherId, bookId);

		// Then
		verify(bookPublisherPersistenceJPA).delete(publisherId, bookId);
		
	}

}
