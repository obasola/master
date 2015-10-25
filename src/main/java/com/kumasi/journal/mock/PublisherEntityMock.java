
/*
 * Created on 24 Oct 2015 ( Time 23:20:18 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.journal.mock;

import java.util.LinkedList;
import java.util.List;

import com.kumasi.journal.domain.jpa.PublisherEntity;
import com.kumasi.journal.mock.tool.MockValues;

public class PublisherEntityMock {

	private MockValues mockValues = new MockValues();
	
	/**
	 * Creates an instance with random Primary Key
	 * @return
	 */
	public PublisherEntity createInstance() {
		// Primary Key values

		return createInstance( mockValues.nextInteger() );
	}
	
	/**
	 * Creates an instance with a specific Primary Key
	 * @param id1
	 * @return
	 */
	public PublisherEntity createInstance( Integer id ) {
		PublisherEntity entity = new PublisherEntity();
		// Init Primary Key fields
		entity.setId( id) ;
		// Init Data fields
		entity.setName( mockValues.nextString(45) ) ; // java.lang.String 
		entity.setUrl( mockValues.nextString(75) ) ; // java.lang.String 
		// Init Link fields (if any)
		// setListOfBook( TODO ) ; // List<Book> 
		return entity ;
	}
	
	/**
	 * Creates a list of instances
	 * @param count number of instances to be created
	 * @return
	 */
	public List<PublisherEntity> createList(int count) {
		List<PublisherEntity> list = new LinkedList<PublisherEntity>();		
		for ( int i = 1 ; i <= count ; i++ ) {
			list.add( createInstance() );
		}
		return list;
	}
}