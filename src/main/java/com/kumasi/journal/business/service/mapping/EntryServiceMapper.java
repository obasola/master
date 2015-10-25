/*
 * Created on 24 Oct 2015 ( Time 23:20:29 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.journal.business.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import com.kumasi.journal.domain.Entry;
import com.kumasi.journal.domain.jpa.EntryEntity;
import com.kumasi.journal.domain.jpa.EntrytypeEntity;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class EntryServiceMapper extends AbstractServiceMapper {

	/**
	 * ModelMapper : bean to bean mapping library.
	 */
	private ModelMapper modelMapper;
	
	/**
	 * Constructor.
	 */
	public EntryServiceMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * Mapping from 'EntryEntity' to 'Entry'
	 * @param entryEntity
	 */
	public Entry mapEntryEntityToEntry(EntryEntity entryEntity) {
		if(entryEntity == null) {
			return null;
		}

		//--- Generic mapping 
		Entry entry = map(entryEntity, Entry.class);

		//--- Link mapping ( link to Entrytype )
		if(entryEntity.getEntrytype() != null) {
			entry.setEntrytypeId(entryEntity.getEntrytype().getId());
		}
		return entry;
	}
	
	/**
	 * Mapping from 'Entry' to 'EntryEntity'
	 * @param entry
	 * @param entryEntity
	 */
	public void mapEntryToEntryEntity(Entry entry, EntryEntity entryEntity) {
		if(entry == null) {
			return;
		}

		//--- Generic mapping 
		map(entry, entryEntity);

		//--- Link mapping ( link : entry )
		if( hasLinkToEntrytype(entry) ) {
			EntrytypeEntity entrytype1 = new EntrytypeEntity();
			entrytype1.setId( entry.getEntrytypeId() );
			entryEntity.setEntrytype( entrytype1 );
		} else {
			entryEntity.setEntrytype( null );
		}

	}
	
	/**
	 * Verify that Entrytype id is valid.
	 * @param Entrytype Entrytype
	 * @return boolean
	 */
	private boolean hasLinkToEntrytype(Entry entry) {
		if(entry.getEntrytypeId() != null) {
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ModelMapper getModelMapper() {
		return modelMapper;
	}

	protected void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

}