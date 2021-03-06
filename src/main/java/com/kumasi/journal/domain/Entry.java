/*
 * Created on 24 Oct 2015 ( Time 23:23:56 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.journal.domain;

import java.io.Serializable;

import javax.validation.constraints.*;

import java.util.Date;

public class Entry implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @NotNull
    private Integer identry;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    @NotNull
    @Size( min = 1, max = 100 )
    private String title;

    @NotNull
    @Size( min = 1, max = 2147483647 )
    private String storyDefinition;

    @NotNull
    @Size( min = 1, max = 16777215 )
    private String successFactory;


    private Date dateCreated;


    private Date dateModified;

    @NotNull
    private Integer entrytypeId;



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
    public void setTitle( String title ) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
    }

    public void setStoryDefinition( String storyDefinition ) {
        this.storyDefinition = storyDefinition;
    }
    public String getStoryDefinition() {
        return this.storyDefinition;
    }

    public void setSuccessFactory( String successFactory ) {
        this.successFactory = successFactory;
    }
    public String getSuccessFactory() {
        return this.successFactory;
    }

    public void setDateCreated( Date dateCreated ) {
        this.dateCreated = dateCreated;
    }
    public Date getDateCreated() {
        return this.dateCreated;
    }

    public void setDateModified( Date dateModified ) {
        this.dateModified = dateModified;
    }
    public Date getDateModified() {
        return this.dateModified;
    }

    public void setEntrytypeId( Integer entrytypeId ) {
        this.entrytypeId = entrytypeId;
    }
    public Integer getEntrytypeId() {
        return this.entrytypeId;
    }


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
 
        public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append(identry);
        sb.append("|");
        sb.append(title);
        // attribute 'storyDefinition' not usable (type = String Long Text)
        // attribute 'successFactory' not usable (type = String Long Text)
        sb.append("|");
        sb.append(dateCreated);
        sb.append("|");
        sb.append(dateModified);
        sb.append("|");
        sb.append(entrytypeId);
        return sb.toString(); 
    } 


}
