/*
 * Created on 24 Oct 2015 ( Time 23:20:17 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */

package com.kumasi.journal.persistence.services.jpa;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.kumasi.journal.domain.jpa.EntryEntity;
import com.kumasi.journal.persistence.commons.jpa.GenericJpaService;
import com.kumasi.journal.persistence.commons.jpa.JpaOperation;
import com.kumasi.journal.persistence.services.EntryPersistence;

/**
 * JPA implementation for basic persistence operations ( entity "Entry" )
 * 
 * @author Telosys Tools Generator
 *
 */
public class EntryPersistenceJPA extends GenericJpaService<EntryEntity, Integer> implements EntryPersistence {

	/**
	 * Constructor
	 */
	public EntryPersistenceJPA() {
		super(EntryEntity.class);
	}

	@Override
	public EntryEntity load( Integer identry ) {
		return super.load( identry );
	}

	@Override
	public boolean delete( Integer identry ) {
		return super.delete( identry );
	}

	@Override
	public boolean delete(EntryEntity entity) {
		if ( entity != null ) {
			return super.delete( entity.getIdentry() );
		}
		return false ;
	}

	@Override
	public long countAll() {
		// JPA operation definition 
		JpaOperation operation = new JpaOperation() {
			@Override
			public Object exectue(EntityManager em) throws PersistenceException {
				Query query = em.createNamedQuery("EntryEntity.countAll");
				return query.getSingleResult() ;
			}
		} ;
		// JPA operation execution 
		return (Long) execute(operation);
	}

}
