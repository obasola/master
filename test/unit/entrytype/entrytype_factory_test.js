'use strict';

/* jasmine specs for controllers go here */

describe('services', function(){
  beforeEach(module('entrytype.module'));
  
  describe('Entrytype', function(){
	var $httpBackend, Entrytype, restURL;
	  
    beforeEach(inject(function($injector) {
    	$httpBackend = $injector.get('$httpBackend');
    	Entrytype = $injector.get('Entrytype');
        restURL = $injector.get('restURL');
    }));

    afterEach(function() {
    	$httpBackend.verifyNoOutstandingExpectation();
    	$httpBackend.verifyNoOutstandingRequest();
    });
    
	it('getAllAsListItems', function() {
		$httpBackend.when('GET', restURL+'/items/entrytype').respond("test");
    	Entrytype.getAllAsListItems().then(function(response) {
        	expect(response.status).toBe(200);
    		expect(response.data).toBe("test");
    	});
    	$httpBackend.flush();
	});

    it('getAll', function() {
    	$httpBackend.when('GET', restURL+'/entrytype').respond("test");
    	Entrytype.getAll().then(function(response) {
        	expect(response.status).toBe(200);
    		expect(response.data).toBe("test");
    	});
    	$httpBackend.flush();
    });
    
    it('get', function() {
    	$httpBackend.when('GET', restURL+'/entrytype/1').respond("test");
    	Entrytype.get('1').then(function(response) {
        	expect(response.status).toBe(200);
    		expect(response.data).toBe("test");
    	});
    	$httpBackend.flush();
    });
    
    it('create throws exception : id not defined', function() {
    	try{
    		Entrytype.create({id:null,name:'entrytype'});
    		$httpBackend.flush();
    	} catch(errors) {
    		expect(errors[0]).toBe('entrytype.id.not.defined');
    	}
    });
    
    it('create', function() {
    	$httpBackend.when('POST', restURL+'/entrytype').respond("test");
    	Entrytype.create({id:'1',name:'entrytype'}).then(function(response) {
        	expect(response.status).toBe(200);
    		expect(response.data).toBe("test");
    	});
    	$httpBackend.flush();
    });
    
    it('update throws exception : id not defined', function() {
    	try{
    		Entrytype.update({id:null,name:'entrytype'});
    		$httpBackend.flush();
    	} catch(errors) {
    		expect(errors[0]).toBe('entrytype.id.not.defined');
    	}
    });
    
    it('update', function() {
    	$httpBackend.when('PUT', restURL+'/entrytype/1').respond("test");
    	Entrytype.update({id:'1',name:'entrytype'}).then(function(response) {
        	expect(response.status).toBe(200);
    		expect(response.data).toBe("test");
    	});
    	$httpBackend.flush();
    });
    
    it('delete', function() {
    	$httpBackend.when('DELETE', restURL+'/entrytype/1').respond("test");
    	Entrytype.delete('1').then(function(response) {
        	expect(response.status).toBe(200);
    		expect(response.data).toBe("test");
    	});
    	$httpBackend.flush();
    });
  });
});