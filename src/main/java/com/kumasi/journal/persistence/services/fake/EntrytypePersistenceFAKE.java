/*
 * Created on 24 Oct 2015 ( Time 23:20:17 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.journal.persistence.services.fake;

import java.util.List;
import java.util.Map;

import com.kumasi.journal.domain.jpa.EntrytypeEntity;
import com.kumasi.journal.persistence.commons.fake.GenericFakeService;
import com.kumasi.journal.persistence.services.EntrytypePersistence;

/**
 * Fake persistence service implementation ( entity "Entrytype" )
 *
 * @author Telosys Tools Generator
 */
public class EntrytypePersistenceFAKE extends GenericFakeService<EntrytypeEntity> implements EntrytypePersistence {

	public EntrytypePersistenceFAKE () {
		super(EntrytypeEntity.class);
	}
	
	protected EntrytypeEntity buildEntity(int index) {
		EntrytypeEntity entity = new EntrytypeEntity();
		// Init fields with mock values
		entity.setId( nextInteger() ) ;
		entity.setDescription( nextString() ) ;
		return entity ;
	}
	
	
	public boolean delete(EntrytypeEntity entity) {
		log("delete ( EntrytypeEntity : " + entity + ")" ) ;
		return true;
	}

	public boolean delete( Integer id ) {
		log("delete ( PK )") ;
		return true;
	}

	public void insert(EntrytypeEntity entity) {
		log("insert ( EntrytypeEntity : " + entity + ")" ) ;
	}

	public EntrytypeEntity load( Integer id ) {
		log("load ( PK )") ;
		return buildEntity(1) ; 
	}

	public List<EntrytypeEntity> loadAll() {
		log("loadAll()") ;
		return buildList(40) ;
	}

	public List<EntrytypeEntity> loadByNamedQuery(String queryName) {
		log("loadByNamedQuery ( '" + queryName + "' )") ;
		return buildList(20) ;
	}

	public List<EntrytypeEntity> loadByNamedQuery(String queryName, Map<String, Object> queryParameters) {
		log("loadByNamedQuery ( '" + queryName + "', parameters )") ;
		return buildList(10) ;
	}

	public EntrytypeEntity save(EntrytypeEntity entity) {
		log("insert ( EntrytypeEntity : " + entity + ")" ) ;
		return entity;
	}

	public List<EntrytypeEntity> search(Map<String, Object> criteria) {
		log("search (criteria)" ) ;
		return buildList(15) ;
	}

	@Override
	public long countAll() {
		return 0 ;
	}

}
