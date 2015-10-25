
/*
 * Created on 24 Oct 2015 ( Time 23:20:17 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.journal.mock;

import java.util.LinkedList;
import java.util.List;

import com.kumasi.journal.domain.jpa.CategoryEntity;
import com.kumasi.journal.mock.tool.MockValues;

public class CategoryEntityMock {

	private MockValues mockValues = new MockValues();
	
	/**
	 * Creates an instance with random Primary Key
	 * @return
	 */
	public CategoryEntity createInstance() {
		// Primary Key values

		return createInstance( mockValues.nextInteger() );
	}
	
	/**
	 * Creates an instance with a specific Primary Key
	 * @param id1
	 * @return
	 */
	public CategoryEntity createInstance( Integer id ) {
		CategoryEntity entity = new CategoryEntity();
		// Init Primary Key fields
		entity.setId( id) ;
		// Init Data fields
		entity.setName( mockValues.nextString(150) ) ; // java.lang.String 
		// Init Link fields (if any)
		// setListOfBook( TODO ) ; // List<Book> 
		return entity ;
	}
	
	/**
	 * Creates a list of instances
	 * @param count number of instances to be created
	 * @return
	 */
	public List<CategoryEntity> createList(int count) {
		List<CategoryEntity> list = new LinkedList<CategoryEntity>();		
		for ( int i = 1 ; i <= count ; i++ ) {
			list.add( createInstance() );
		}
		return list;
	}
}