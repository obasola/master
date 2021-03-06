/*
 * Created on 24 Oct 2015 ( Time 23:23:56 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.journal.domain;

import java.io.Serializable;

import javax.validation.constraints.*;


public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @NotNull
    private Integer idperson;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    @NotNull
    @Size( min = 1, max = 45 )
    private String emailAddress;

    @NotNull
    @Size( min = 1, max = 45 )
    private String password;

    @NotNull
    @Size( min = 1, max = 45 )
    private String firstName;

    @NotNull
    @Size( min = 1, max = 45 )
    private String lastName;



    //----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    //----------------------------------------------------------------------
    public void setIdperson( Integer idperson ) {
        this.idperson = idperson ;
    }

    public Integer getIdperson() {
        return this.idperson;
    }


    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    //----------------------------------------------------------------------
    public void setEmailAddress( String emailAddress ) {
        this.emailAddress = emailAddress;
    }
    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setPassword( String password ) {
        this.password = password;
    }
    public String getPassword() {
        return this.password;
    }

    public void setFirstName( String firstName ) {
        this.firstName = firstName;
    }
    public String getFirstName() {
        return this.firstName;
    }

    public void setLastName( String lastName ) {
        this.lastName = lastName;
    }
    public String getLastName() {
        return this.lastName;
    }


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
 
        public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append(idperson);
        sb.append("|");
        sb.append(emailAddress);
        sb.append("|");
        sb.append(password);
        sb.append("|");
        sb.append(firstName);
        sb.append("|");
        sb.append(lastName);
        return sb.toString(); 
    } 


}
