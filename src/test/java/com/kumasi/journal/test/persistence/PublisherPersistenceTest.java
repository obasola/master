/*
 * Created on 24 Oct 2015 ( Time 23:20:18 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.journal.test.persistence;


import com.kumasi.journal.domain.jpa.PublisherEntity;
import com.kumasi.journal.mock.PublisherEntityMock;
import com.kumasi.journal.persistence.PersistenceServiceProvider;
import com.kumasi.journal.persistence.services.PublisherPersistence;

import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit test case for Publisher persistence service
 * 
 * @author Telosys Tools Generator
 *
 */
public class PublisherPersistenceTest 
{
	@Test
	public void test1() {
		
		System.out.println("Test count ..." );
		
		PublisherPersistence service = PersistenceServiceProvider.getService(PublisherPersistence.class);
		System.out.println("CountAll = " + service.countAll() );
	}
	
	@Test
	public void test2() {
		
		System.out.println("Test Publisher persistence : delete + load ..." );
		
		PublisherPersistence service = PersistenceServiceProvider.getService(PublisherPersistence.class);
		
		PublisherEntityMock mock = new PublisherEntityMock();
		
		// TODO : set primary key values here 
		process( service, mock, 0  );
		// process( service, mock, ... );
	}

	private void process(PublisherPersistence service, PublisherEntityMock mock, Integer id ) {
		System.out.println("----- "  );
		System.out.println(" . load : " );
		PublisherEntity entity = service.load( id );
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
			entity = mock.createInstance( id ) ;
			Assert.assertNotNull(entity);

			// No reference : insert is possible 
			// Try to insert the new instance
			System.out.println(" . insert : " + entity );
			service.insert(entity);
			System.out.println("   inserted : " + entity );

			System.out.println(" . delete : " );
			boolean deleted = service.delete( id );
			System.out.println("   deleted = " + deleted);
			Assert.assertTrue(deleted) ;
		}		
	}
}
