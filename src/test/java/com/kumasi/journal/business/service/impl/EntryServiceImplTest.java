/*
 * Created on 24 Oct 2015 ( Time 23:20:29 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.journal.business.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.kumasi.journal.domain.Entry;
import com.kumasi.journal.domain.jpa.EntryEntity;
import java.util.Date;
import com.kumasi.journal.business.service.mapping.EntryServiceMapper;
import com.kumasi.journal.persistence.services.jpa.EntryPersistenceJPA;
import com.kumasi.journal.test.EntryFactoryForTest;
import com.kumasi.journal.test.EntryEntityFactoryForTest;
import com.kumasi.journal.test.MockValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test : Implementation of EntryService
 */
@RunWith(MockitoJUnitRunner.class)
public class EntryServiceImplTest {

	@InjectMocks
	private EntryServiceImpl entryService;
	@Mock
	private EntryPersistenceJPA entryPersistenceJPA;
	@Mock
	private EntryServiceMapper entryServiceMapper;
	
	private EntryFactoryForTest entryFactoryForTest = new EntryFactoryForTest();

	private EntryEntityFactoryForTest entryEntityFactoryForTest = new EntryEntityFactoryForTest();

	private MockValues mockValues = new MockValues();
	
	@Test
	public void findById() {
		// Given
		Integer identry = mockValues.nextInteger();
		
		EntryEntity entryEntity = entryPersistenceJPA.load(identry);
		
		Entry entry = entryFactoryForTest.newEntry();
		when(entryServiceMapper.mapEntryEntityToEntry(entryEntity)).thenReturn(entry);

		// When
		Entry entryFound = entryService.findById(identry);

		// Then
		assertEquals(entry.getIdentry(),entryFound.getIdentry());
	}

	@Test
	public void findAll() {
		// Given
		List<EntryEntity> entryEntitys = new ArrayList<EntryEntity>();
		EntryEntity entryEntity1 = entryEntityFactoryForTest.newEntryEntity();
		entryEntitys.add(entryEntity1);
		EntryEntity entryEntity2 = entryEntityFactoryForTest.newEntryEntity();
		entryEntitys.add(entryEntity2);
		when(entryPersistenceJPA.loadAll()).thenReturn(entryEntitys);
		
		Entry entry1 = entryFactoryForTest.newEntry();
		when(entryServiceMapper.mapEntryEntityToEntry(entryEntity1)).thenReturn(entry1);
		Entry entry2 = entryFactoryForTest.newEntry();
		when(entryServiceMapper.mapEntryEntityToEntry(entryEntity2)).thenReturn(entry2);

		// When
		List<Entry> entrysFounds = entryService.findAll();

		// Then
		assertTrue(entry1 == entrysFounds.get(0));
		assertTrue(entry2 == entrysFounds.get(1));
	}

	@Test
	public void create() {
		// Given
		Entry entry = entryFactoryForTest.newEntry();

		EntryEntity entryEntity = entryEntityFactoryForTest.newEntryEntity();
		when(entryPersistenceJPA.load(entry.getIdentry())).thenReturn(null);
		
		entryEntity = new EntryEntity();
		entryServiceMapper.mapEntryToEntryEntity(entry, entryEntity);
		EntryEntity entryEntitySaved = entryPersistenceJPA.save(entryEntity);
		
		Entry entrySaved = entryFactoryForTest.newEntry();
		when(entryServiceMapper.mapEntryEntityToEntry(entryEntitySaved)).thenReturn(entrySaved);

		// When
		Entry entryResult = entryService.create(entry);

		// Then
		assertTrue(entryResult == entrySaved);
	}

	@Test
	public void createKOExists() {
		// Given
		Entry entry = entryFactoryForTest.newEntry();

		EntryEntity entryEntity = entryEntityFactoryForTest.newEntryEntity();
		when(entryPersistenceJPA.load(entry.getIdentry())).thenReturn(entryEntity);

		// When
		Exception exception = null;
		try {
			entryService.create(entry);
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
		Entry entry = entryFactoryForTest.newEntry();

		EntryEntity entryEntity = entryEntityFactoryForTest.newEntryEntity();
		when(entryPersistenceJPA.load(entry.getIdentry())).thenReturn(entryEntity);
		
		EntryEntity entryEntitySaved = entryEntityFactoryForTest.newEntryEntity();
		when(entryPersistenceJPA.save(entryEntity)).thenReturn(entryEntitySaved);
		
		Entry entrySaved = entryFactoryForTest.newEntry();
		when(entryServiceMapper.mapEntryEntityToEntry(entryEntitySaved)).thenReturn(entrySaved);

		// When
		Entry entryResult = entryService.update(entry);

		// Then
		verify(entryServiceMapper).mapEntryToEntryEntity(entry, entryEntity);
		assertTrue(entryResult == entrySaved);
	}

	@Test
	public void delete() {
		// Given
		Integer identry = mockValues.nextInteger();

		// When
		entryService.delete(identry);

		// Then
		verify(entryPersistenceJPA).delete(identry);
		
	}

}
