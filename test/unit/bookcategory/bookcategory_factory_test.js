'use strict';

/* jasmine specs for controllers go here */

describe('services', function(){
  beforeEach(module('bookCategory.module'));
  
  describe('BookCategory', function(){
	var $httpBackend, BookCategory, restURL;
	  
    beforeEach(inject(function($injector) {
    	$httpBackend = $injector.get('$httpBackend');
    	BookCategory = $injector.get('BookCategory');
        restURL = $injector.get('restURL');
    }));

    afterEach(function() {
    	$httpBackend.verifyNoOutstandingExpectation();
    	$httpBackend.verifyNoOutstandingRequest();
    });
    
	it('getAllAsListItems', function() {
		$httpBackend.when('GET', restURL+'/items/bookCategory').respond("test");
    	BookCategory.getAllAsListItems().then(function(response) {
        	expect(response.status).toBe(200);
    		expect(response.data).toBe("test");
    	});
    	$httpBackend.flush();
	});

    it('getAll', function() {
    	$httpBackend.when('GET', restURL+'/bookCategory').respond("test");
    	BookCategory.getAll().then(function(response) {
        	expect(response.status).toBe(200);
    		expect(response.data).toBe("test");
    	});
    	$httpBackend.flush();
    });
    
    it('get', function() {
    	$httpBackend.when('GET', restURL+'/bookCategory/1/2').respond("test");
    	BookCategory.get('1', '2').then(function(response) {
        	expect(response.status).toBe(200);
    		expect(response.data).toBe("test");
    	});
    	$httpBackend.flush();
    });
    
    it('create throws exception : id not defined', function() {
    	try{
    		BookCategory.create({bookId:null, categoryId:null,name:'bookCategory'});
    		$httpBackend.flush();
    	} catch(errors) {
    		expect(errors[0]).toBe('bookCategory.id.not.defined');
    	}
    });
    
    it('create', function() {
    	$httpBackend.when('POST', restURL+'/bookCategory').respond("test");
    	BookCategory.create({bookId:'1', categoryId:'2',name:'bookCategory'}).then(function(response) {
        	expect(response.status).toBe(200);
    		expect(response.data).toBe("test");
    	});
    	$httpBackend.flush();
    });
    
    it('update throws exception : id not defined', function() {
    	try{
    		BookCategory.update({bookId:null, categoryId:null,name:'bookCategory'});
    		$httpBackend.flush();
    	} catch(errors) {
    		expect(errors[0]).toBe('bookCategory.id.not.defined');
    	}
    });
    
    it('update', function() {
    	$httpBackend.when('PUT', restURL+'/bookCategory/1/2').respond("test");
    	BookCategory.update({bookId:'1', categoryId:'2',name:'bookCategory'}).then(function(response) {
        	expect(response.status).toBe(200);
    		expect(response.data).toBe("test");
    	});
    	$httpBackend.flush();
    });
    
    it('delete', function() {
    	$httpBackend.when('DELETE', restURL+'/bookCategory/1/2').respond("test");
    	BookCategory.delete('1', '2').then(function(response) {
        	expect(response.status).toBe(200);
    		expect(response.data).toBe("test");
    	});
    	$httpBackend.flush();
    });
  });
});