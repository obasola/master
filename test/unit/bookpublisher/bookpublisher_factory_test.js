'use strict';

/* jasmine specs for controllers go here */

describe('services', function(){
  beforeEach(module('bookPublisher.module'));
  
  describe('BookPublisher', function(){
	var $httpBackend, BookPublisher, restURL;
	  
    beforeEach(inject(function($injector) {
    	$httpBackend = $injector.get('$httpBackend');
    	BookPublisher = $injector.get('BookPublisher');
        restURL = $injector.get('restURL');
    }));

    afterEach(function() {
    	$httpBackend.verifyNoOutstandingExpectation();
    	$httpBackend.verifyNoOutstandingRequest();
    });
    
	it('getAllAsListItems', function() {
		$httpBackend.when('GET', restURL+'/items/bookPublisher').respond("test");
    	BookPublisher.getAllAsListItems().then(function(response) {
        	expect(response.status).toBe(200);
    		expect(response.data).toBe("test");
    	});
    	$httpBackend.flush();
	});

    it('getAll', function() {
    	$httpBackend.when('GET', restURL+'/bookPublisher').respond("test");
    	BookPublisher.getAll().then(function(response) {
        	expect(response.status).toBe(200);
    		expect(response.data).toBe("test");
    	});
    	$httpBackend.flush();
    });
    
    it('get', function() {
    	$httpBackend.when('GET', restURL+'/bookPublisher/1/2').respond("test");
    	BookPublisher.get('1', '2').then(function(response) {
        	expect(response.status).toBe(200);
    		expect(response.data).toBe("test");
    	});
    	$httpBackend.flush();
    });
    
    it('create throws exception : id not defined', function() {
    	try{
    		BookPublisher.create({publisherId:null, bookId:null,name:'bookPublisher'});
    		$httpBackend.flush();
    	} catch(errors) {
    		expect(errors[0]).toBe('bookPublisher.id.not.defined');
    	}
    });
    
    it('create', function() {
    	$httpBackend.when('POST', restURL+'/bookPublisher').respond("test");
    	BookPublisher.create({publisherId:'1', bookId:'2',name:'bookPublisher'}).then(function(response) {
        	expect(response.status).toBe(200);
    		expect(response.data).toBe("test");
    	});
    	$httpBackend.flush();
    });
    
    it('update throws exception : id not defined', function() {
    	try{
    		BookPublisher.update({publisherId:null, bookId:null,name:'bookPublisher'});
    		$httpBackend.flush();
    	} catch(errors) {
    		expect(errors[0]).toBe('bookPublisher.id.not.defined');
    	}
    });
    
    it('update', function() {
    	$httpBackend.when('PUT', restURL+'/bookPublisher/1/2').respond("test");
    	BookPublisher.update({publisherId:'1', bookId:'2',name:'bookPublisher'}).then(function(response) {
        	expect(response.status).toBe(200);
    		expect(response.data).toBe("test");
    	});
    	$httpBackend.flush();
    });
    
    it('delete', function() {
    	$httpBackend.when('DELETE', restURL+'/bookPublisher/1/2').respond("test");
    	BookPublisher.delete('1', '2').then(function(response) {
        	expect(response.status).toBe(200);
    		expect(response.data).toBe("test");
    	});
    	$httpBackend.flush();
    });
  });
});