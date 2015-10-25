/*
 * Created on 24 Oct 2015 ( Time 23:23:56 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.journal.business.service;

import java.util.List;

import com.kumasi.journal.domain.Entry;

/**
 * Business Service Interface for entity Entry.
 */
public interface EntryService { 

	/**
	 * Loads an entity from the database using its Primary Key
	 * @param identry
	 * @return entity
	 */
	Entry findById( Integer identry  ) ;

	/**
	 * Loads all entities.
	 * @return all entities
	 */
	List<Entry> findAll();

	/**
	 * Saves the given entity in the database (create or update)
	 * @param entity
	 * @return entity
	 */
	Entry save(Entry entity);

	/**
	 * Updates the given entity in the database
	 * @param entity
	 * @return
	 */
	Entry update(Entry entity);

	/**
	 * Creates the given entity in the database
	 * @param entity
	 * @return
	 */
	Entry create(Entry entity);

	/**
	 * Deletes an entity using its Primary Key
	 * @param identry
	 */
	void delete( Integer identry );


}
