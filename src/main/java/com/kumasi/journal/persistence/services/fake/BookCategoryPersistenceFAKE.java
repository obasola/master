/*
 * Created on 24 Oct 2015 ( Time 23:20:17 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.journal.persistence.services.fake;

import java.util.List;
import java.util.Map;

import com.kumasi.journal.domain.jpa.BookCategoryEntity;
import com.kumasi.journal.persistence.commons.fake.GenericFakeService;
import com.kumasi.journal.persistence.services.BookCategoryPersistence;

/**
 * Fake persistence service implementation ( entity "BookCategory" )
 *
 * @author Telosys Tools Generator
 */
public class BookCategoryPersistenceFAKE extends GenericFakeService<BookCategoryEntity> implements BookCategoryPersistence {

	public BookCategoryPersistenceFAKE () {
		super(BookCategoryEntity.class);
	}
	
	protected BookCategoryEntity buildEntity(int index) {
		BookCategoryEntity entity = new BookCategoryEntity();
		// Init fields with mock values
		entity.setBookId( nextInteger() ) ;
		entity.setCategoryId( nextInteger() ) ;
		return entity ;
	}
	
	
	public boolean delete(BookCategoryEntity entity) {
		log("delete ( BookCategoryEntity : " + entity + ")" ) ;
		return true;
	}

	public boolean delete( Integer bookId, Integer categoryId ) {
		log("delete ( PK )") ;
		return true;
	}

	public void insert(BookCategoryEntity entity) {
		log("insert ( BookCategoryEntity : " + entity + ")" ) ;
	}

	public BookCategoryEntity load( Integer bookId, Integer categoryId ) {
		log("load ( PK )") ;
		return buildEntity(1) ; 
	}

	public List<BookCategoryEntity> loadAll() {
		log("loadAll()") ;
		return buildList(40) ;
	}

	public List<BookCategoryEntity> loadByNamedQuery(String queryName) {
		log("loadByNamedQuery ( '" + queryName + "' )") ;
		return buildList(20) ;
	}

	public List<BookCategoryEntity> loadByNamedQuery(String queryName, Map<String, Object> queryParameters) {
		log("loadByNamedQuery ( '" + queryName + "', parameters )") ;
		return buildList(10) ;
	}

	public BookCategoryEntity save(BookCategoryEntity entity) {
		log("insert ( BookCategoryEntity : " + entity + ")" ) ;
		return entity;
	}

	public List<BookCategoryEntity> search(Map<String, Object> criteria) {
		log("search (criteria)" ) ;
		return buildList(15) ;
	}

	@Override
	public long countAll() {
		return 0 ;
	}

}
