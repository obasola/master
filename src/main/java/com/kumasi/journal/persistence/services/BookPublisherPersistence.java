/*
 * Created on 24 Oct 2015 ( Time 23:20:17 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.journal.persistence.services;

import java.util.List;
import java.util.Map;

import com.kumasi.journal.domain.jpa.BookPublisherEntity;

/**
 * Basic persistence operations for entity "BookPublisher"
 * 
 * This Bean has a composite Primary Key : BookPublisherEntityKey
 *
 * @author Telosys Tools Generator
 *
 */
public interface BookPublisherPersistence {

	/**
	 * Deletes the given entity <br>
	 * Transactional operation ( begin transaction and commit )
	 * @param bookPublisher
	 * @return true if found and deleted, false if not found
	 */
	public boolean delete(BookPublisherEntity bookPublisher) ;

	/**
	 * Deletes the entity by its Primary Key <br>
	 * Transactional operation ( begin transaction and commit )
	 * @param publisherId
	 * @param bookId
	 * @return true if found and deleted, false if not found
	 */
	public boolean delete(Integer publisherId, Integer bookId) ;

	/**
	 * Inserts the given entity and commit <br>
	 * Transactional operation ( begin transaction and commit )
	 * @param bookPublisher
	 */
	public void insert(BookPublisherEntity bookPublisher) ;

	/**
	 * Loads the entity for the given Primary Key <br>
	 * @param publisherId
	 * @param bookId
	 * @return the entity loaded (or null if not found)
	 */
	public BookPublisherEntity load(Integer publisherId, Integer bookId) ;

	/**
	 * Loads ALL the entities (use with caution)
	 * @return
	 */
	public List<BookPublisherEntity> loadAll() ;

	/**
	 * Loads a list of entities using the given "named query" without parameter 
	 * @param queryName
	 * @return
	 */
	public List<BookPublisherEntity> loadByNamedQuery(String queryName) ;

	/**
	 * Loads a list of entities using the given "named query" with parameters 
	 * @param queryName
	 * @param queryParameters
	 * @return
	 */
	public List<BookPublisherEntity> loadByNamedQuery(String queryName, Map<String, Object> queryParameters) ;

	/**
	 * Saves (create or update) the given entity <br>
	 * Transactional operation ( begin transaction and commit )
	 * @param bookPublisher
	 * @return
	 */
	public BookPublisherEntity save(BookPublisherEntity bookPublisher) ;

	/**
	 * Search the entities matching the given search criteria
	 * @param criteria
	 * @return
	 */
	public List<BookPublisherEntity> search( Map<String, Object> criteria ) ;

	/**
	 * Count all the occurrences
	 * @return
	 */
	public long countAll();
	
}
