/*
 * Created on 24 Oct 2015 ( Time 23:20:17 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.journal.domain.jpa;
import java.io.Serializable;

import javax.persistence.*;

/**
 * Composite primary key for entity "BookCategoryEntity" ( stored in table "Book_Category" )
 *
 * @author Telosys Tools Generator
 *
 */
 @Embeddable
public class BookCategoryEntityKey implements Serializable {
    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY KEY ATTRIBUTES 
    //----------------------------------------------------------------------
    @Column(name="Book_id", nullable=false)
    private Integer    bookId       ;
    
    @Column(name="Category_id", nullable=false)
    private Integer    categoryId   ;
    

    //----------------------------------------------------------------------
    // CONSTRUCTORS
    //----------------------------------------------------------------------
    public BookCategoryEntityKey() {
        super();
    }

    public BookCategoryEntityKey( Integer bookId, Integer categoryId ) {
        super();
        this.bookId = bookId ;
        this.categoryId = categoryId ;
    }
    
    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR KEY FIELDS
    //----------------------------------------------------------------------
    public void setBookId( Integer value ) {
        this.bookId = value;
    }
    public Integer getBookId() {
        return this.bookId;
    }

    public void setCategoryId( Integer value ) {
        this.categoryId = value;
    }
    public Integer getCategoryId() {
        return this.categoryId;
    }


    //----------------------------------------------------------------------
    // equals METHOD
    //----------------------------------------------------------------------
	public boolean equals(Object obj) { 
		if ( this == obj ) return true ; 
		if ( obj == null ) return false ;
		if ( this.getClass() != obj.getClass() ) return false ; 
		BookCategoryEntityKey other = (BookCategoryEntityKey) obj; 
		//--- Attribute bookId
		if ( bookId == null ) { 
			if ( other.bookId != null ) 
				return false ; 
		} else if ( ! bookId.equals(other.bookId) ) 
			return false ; 
		//--- Attribute categoryId
		if ( categoryId == null ) { 
			if ( other.categoryId != null ) 
				return false ; 
		} else if ( ! categoryId.equals(other.categoryId) ) 
			return false ; 
		return true; 
	} 


    //----------------------------------------------------------------------
    // hashCode METHOD
    //----------------------------------------------------------------------
	public int hashCode() { 
		final int prime = 31; 
		int result = 1; 
		
		//--- Attribute bookId
		result = prime * result + ((bookId == null) ? 0 : bookId.hashCode() ) ; 
		//--- Attribute categoryId
		result = prime * result + ((categoryId == null) ? 0 : categoryId.hashCode() ) ; 
		
		return result; 
	} 


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    public String toString() {
		StringBuffer sb = new StringBuffer(); 
		sb.append(bookId); 
		sb.append("|"); 
		sb.append(categoryId); 
        return sb.toString();
    }
}
