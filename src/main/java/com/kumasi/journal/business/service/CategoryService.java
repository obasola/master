/*
 * Created on 24 Oct 2015 ( Time 23:23:56 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.journal.business.service;

import java.util.List;

import com.kumasi.journal.domain.Category;

/**
 * Business Service Interface for entity Category.
 */
public interface CategoryService { 

	/**
	 * Loads an entity from the database using its Primary Key
	 * @param id
	 * @return entity
	 */
	Category findById( Integer id  ) ;

	/**
	 * Loads all entities.
	 * @return all entities
	 */
	List<Category> findAll();

	/**
	 * Saves the given entity in the database (create or update)
	 * @param entity
	 * @return entity
	 */
	Category save(Category entity);

	/**
	 * Updates the given entity in the database
	 * @param entity
	 * @return
	 */
	Category update(Category entity);

	/**
	 * Creates the given entity in the database
	 * @param entity
	 * @return
	 */
	Category create(Category entity);

	/**
	 * Deletes an entity using its Primary Key
	 * @param id
	 */
	void delete( Integer id );


}
