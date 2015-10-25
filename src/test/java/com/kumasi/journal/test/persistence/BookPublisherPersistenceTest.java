/*
 * Created on 24 Oct 2015 ( Time 23:20:17 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.journal.test.persistence;


import com.kumasi.journal.domain.jpa.BookPublisherEntity;
import com.kumasi.journal.mock.BookPublisherEntityMock;
import com.kumasi.journal.persistence.PersistenceServiceProvider;
import com.kumasi.journal.persistence.services.BookPublisherPersistence;

import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit test case for BookPublisher persistence service
 * 
 * @author Telosys Tools Generator
 *
 */
public class BookPublisherPersistenceTest 
{
	@Test
	public void test1() {
		
		System.out.println("Test count ..." );
		
		BookPublisherPersistence service = PersistenceServiceProvider.getService(BookPublisherPersistence.class);
		System.out.println("CountAll = " + service.countAll() );
	}
	
	@Test
	public void test2() {
		
		System.out.println("Test BookPublisher persistence : delete + load ..." );
		
		BookPublisherPersistence service = PersistenceServiceProvider.getService(BookPublisherPersistence.class);
		
		BookPublisherEntityMock mock = new BookPublisherEntityMock();
		
		// TODO : set primary key values here 
		process( service, mock, 0 , 0  );
		// process( service, mock, ... );
	}

	private void process(BookPublisherPersistence service, BookPublisherEntityMock mock, Integer publisherId, Integer bookId ) {
		System.out.println("----- "  );
		System.out.println(" . load : " );
		BookPublisherEntity entity = service.load( publisherId, bookId );
		if ( entity != null ) {
			// Found 
			System.out.println("   FOUND : " + entity );
			
			// Save (update) with the same values to avoid database integrity errors  
			System.out.println(" . save : " + entity );
			service.save(entity);
			System.out.println("   saved : " + entity );
		}
		else {
			// Not found 
			System.out.println("   NOT FOUND" );
			// Create a new instance 
			entity = mock.createInstance( publisherId, bookId ) ;
			Assert.assertNotNull(entity);

			/* NB : this entity is a "Join Table" 
			System.out.println(" . insert : " + entity );
			service.insert(entity);
			System.out.println("   inserted : " + entity );
			*/

			System.out.println(" . delete : " );
			boolean deleted = service.delete( publisherId, bookId );
			System.out.println("   deleted = " + deleted);
		}		
	}
}
