/*
 * Created on 24 Oct 2015 ( Time 23:20:17 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
// This Bean has a basic Primary Key (not composite) 

package com.kumasi.journal.domain.jpa;

import java.io.Serializable;

//import javax.validation.constraints.* ;
//import org.hibernate.validator.constraints.* ;

import java.util.Date;

import javax.persistence.*;

/**
 * Persistent class for entity stored in table "Entry"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name="Entry", catalog="Journal" )
// Define named queries here
@NamedQueries ( {
  @NamedQuery ( name="EntryEntity.countAll", query="SELECT COUNT(x) FROM EntryEntity x" )
} )
public class EntryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="idEntry", nullable=false)
    private Integer    identry      ;


    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    @Column(name="title", nullable=false, length=100)
    private String     title        ;

    @Column(name="story_definition", nullable=false)
    private String     storyDefinition ;

    @Column(name="success_factory", nullable=false)
    private String     successFactory ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="date_created")
    private Date       dateCreated  ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="date_modified")
    private Date       dateModified ;

	// "entrytypeId" (column "EntryType_id") is not defined by itself because used as FK in a link 


    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------
    @ManyToOne
    @JoinColumn(name="EntryType_id", referencedColumnName="id")
    private EntrytypeEntity entrytype   ;


    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public EntryEntity() {
		super();
    }
    
    //----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    //----------------------------------------------------------------------
    public void setIdentry( Integer identry ) {
        this.identry = identry ;
    }
    public Integer getIdentry() {
        return this.identry;
    }

    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    //----------------------------------------------------------------------
    //--- DATABASE MAPPING : title ( VARCHAR ) 
    public void setTitle( String title ) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
    }

    //--- DATABASE MAPPING : story_definition ( LONGTEXT ) 
    public void setStoryDefinition( String storyDefinition ) {
        this.storyDefinition = storyDefinition;
    }
    public String getStoryDefinition() {
        return this.storyDefinition;
    }

    //--- DATABASE MAPPING : success_factory ( MEDIUMTEXT ) 
    public void setSuccessFactory( String successFactory ) {
        this.successFactory = successFactory;
    }
    public String getSuccessFactory() {
        return this.successFactory;
    }

    //--- DATABASE MAPPING : date_created ( DATETIME ) 
    public void setDateCreated( Date dateCreated ) {
        this.dateCreated = dateCreated;
    }
    public Date getDateCreated() {
        return this.dateCreated;
    }

    //--- DATABASE MAPPING : date_modified ( DATETIME ) 
    public void setDateModified( Date dateModified ) {
        this.dateModified = dateModified;
    }
    public Date getDateModified() {
        return this.dateModified;
    }


    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    //----------------------------------------------------------------------
    public void setEntrytype( EntrytypeEntity entrytype ) {
        this.entrytype = entrytype;
    }
    public EntrytypeEntity getEntrytype() {
        return this.entrytype;
    }


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append("["); 
        sb.append(identry);
        sb.append("]:"); 
        sb.append(title);
        // attribute 'storyDefinition' not usable (type = String Long Text)
        // attribute 'successFactory' not usable (type = String Long Text)
        sb.append("|");
        sb.append(dateCreated);
        sb.append("|");
        sb.append(dateModified);
        return sb.toString(); 
    } 

}
