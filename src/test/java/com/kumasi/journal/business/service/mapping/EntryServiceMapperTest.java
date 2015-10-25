/*
 * Created on 24 Oct 2015 ( Time 23:20:29 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.journal.business.service.mapping;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import com.kumasi.journal.domain.Entry;
import com.kumasi.journal.domain.jpa.EntryEntity;
import com.kumasi.journal.domain.jpa.EntrytypeEntity;
import com.kumasi.journal.test.MockValues;

/**
 * Test : Mapping between entity beans and display beans.
 */
public class EntryServiceMapperTest {

	private EntryServiceMapper entryServiceMapper;

	private static ModelMapper modelMapper = new ModelMapper();

	private MockValues mockValues = new MockValues();
	
	
	@BeforeClass
	public static void setUp() {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}
	
	@Before
	public void before() {
		entryServiceMapper = new EntryServiceMapper();
		entryServiceMapper.setModelMapper(modelMapper);
	}
	
	/**
	 * Mapping from 'EntryEntity' to 'Entry'
	 * @param entryEntity
	 */
	@Test
	public void testMapEntryEntityToEntry() {
		// Given
		EntryEntity entryEntity = new EntryEntity();
		entryEntity.setTitle(mockValues.nextString(100));
		entryEntity.setStoryDefinition(mockValues.nextString(2147483647));
		entryEntity.setSuccessFactory(mockValues.nextString(16777215));
		entryEntity.setDateCreated(mockValues.nextDate());
		entryEntity.setDateModified(mockValues.nextDate());
		entryEntity.setEntrytype(new EntrytypeEntity());
		entryEntity.getEntrytype().setId(mockValues.nextInteger());
		
		// When
		Entry entry = entryServiceMapper.mapEntryEntityToEntry(entryEntity);
		
		// Then
		assertEquals(entryEntity.getTitle(), entry.getTitle());
		assertEquals(entryEntity.getStoryDefinition(), entry.getStoryDefinition());
		assertEquals(entryEntity.getSuccessFactory(), entry.getSuccessFactory());
		assertEquals(entryEntity.getDateCreated(), entry.getDateCreated());
		assertEquals(entryEntity.getDateModified(), entry.getDateModified());
		assertEquals(entryEntity.getEntrytype().getId(), entry.getEntrytypeId());
	}
	
	/**
	 * Test : Mapping from 'Entry' to 'EntryEntity'
	 */
	@Test
	public void testMapEntryToEntryEntity() {
		// Given
		Entry entry = new Entry();
		entry.setTitle(mockValues.nextString(100));
		entry.setStoryDefinition(mockValues.nextString(2147483647));
		entry.setSuccessFactory(mockValues.nextString(16777215));
		entry.setDateCreated(mockValues.nextDate());
		entry.setDateModified(mockValues.nextDate());
		entry.setEntrytypeId(mockValues.nextInteger());

		EntryEntity entryEntity = new EntryEntity();
		
		// When
		entryServiceMapper.mapEntryToEntryEntity(entry, entryEntity);
		
		// Then
		assertEquals(entry.getTitle(), entryEntity.getTitle());
		assertEquals(entry.getStoryDefinition(), entryEntity.getStoryDefinition());
		assertEquals(entry.getSuccessFactory(), entryEntity.getSuccessFactory());
		assertEquals(entry.getDateCreated(), entryEntity.getDateCreated());
		assertEquals(entry.getDateModified(), entryEntity.getDateModified());
		assertEquals(entry.getEntrytypeId(), entryEntity.getEntrytype().getId());
	}

}