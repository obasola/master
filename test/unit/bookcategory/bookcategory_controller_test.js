'use strict';

/* jasmine specs for controllers go here */

describe('controllers', function(){
  beforeEach(module('bookCategory.module'));
  
  describe('BookCategoryCtrl', function(){
    var BookCategoryCtrl, BookCategory,$rootScope, $scope, $routeParams, $httpBackend, $location, MessageHandler, $q, $controller;
	  
    beforeEach(inject(function($injector) {
    	$controller = $injector.get('$controller');
    	$q = $injector.get('$q');
    	$rootScope = $injector.get('$rootScope');
    	$scope = $rootScope.$new();
    	$routeParams = $injector.get('$routeParams');
    	$httpBackend = $injector.get('$httpBackend');
    	
    	// location is mocked due to redirection in browser : karma does not support it
    	$location = {
    		path: jasmine.createSpy("path").andCallFake(function() {
        	    return "";
        	})
    	};
    	
    	// Messages
    	MessageHandler = {
    		cleanMessage: jasmine.createSpy("cleanMessage"),
    		addSuccess: jasmine.createSpy("addSuccess"),
    		manageError: jasmine.createSpy("manageError"),
    		manageException: jasmine.createSpy("manageException"),
    	};

    	// BookCategory service
    	BookCategory = {
    		getAll: function() {
    			var deferred = $q.defer();
    			deferred.resolve({data:'bookCategory1'});
    			return deferred.promise;
    		}
    	};
		
				BookCategoryCtrl = $controller('BookCategoryCtrl', {
    		'BookCategory': BookCategory,
			    		'$scope': $scope,
    		'$routeParams': $routeParams,
    		'$http': $httpBackend,
    		'$location': $location,
    		'MessageHandler': MessageHandler
    	});
    }));

    afterEach(function() {
    	$httpBackend.verifyNoOutstandingExpectation();
    	$httpBackend.verifyNoOutstandingRequest();
    });
    
    it('init', function() {
    	$rootScope.$apply();
    	expect($scope.mode).toBeNull();
    	expect($scope.bookCategory).toBeNull();
    	expect($scope.bookCategorys).toBe('bookCategory1');
    	expect(Object.keys($scope.items).length).toBe(0);
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    });
    
    it('refreshBookCategoryList', function() {
    	// given
    	BookCategory.getAll = function() {
			var deferred = $q.defer();
			deferred.resolve({data:'bookCategory2'});
			return deferred.promise;
		}
    	
    	// when
    	$scope.refreshBookCategoryList();
    	$scope.$apply();

    	// then
    	$rootScope.$apply();
    	expect($scope.bookCategorys).toBe('bookCategory2');
    });
    
    it('refreshBookCategory', function() {
    	// given
    	BookCategory.get = function(bookId, categoryId) {
			var deferred = $q.defer();
			deferred.resolve({data:'bookCategory'+bookId+categoryId});
			return deferred.promise;
		}
    	
    	// when
    	$scope.refreshBookCategory('1', '2');
    	$scope.$apply();
    	
    	// then
    	expect($scope.bookCategory).toBe('bookCategory'+'1'+'2');
    });
    
	it('goToBookCategoryList', function() {
    	// given
    	spyOn($scope, "refreshBookCategoryList");
    	
    	// when
    	$scope.goToBookCategoryList();
    	$scope.$apply();
    	
    	// then
    	expect($scope.refreshBookCategoryList).toHaveBeenCalled();
    	expect($location.path).toHaveBeenCalledWith('/bookCategory');
    });
    
    it('goToBookCategory', function() {
    	// given
    	spyOn($scope, "refreshBookCategory");
    	var bookId = 1;
    	var categoryId = 2;
    	
    	// when
    	$scope.goToBookCategory(bookId, categoryId);
    	$scope.$apply();
    	
    	// then
    	expect($scope.refreshBookCategory).toHaveBeenCalledWith(bookId, categoryId);
    	expect($location.path).toHaveBeenCalledWith('/bookCategory'+'/'+bookId+'/'+categoryId);
    });
    
    it('save : create', function() {
    	// given
    	$scope.bookCategory = {bookId:'1', categoryId:'2', name:'bookCategory'};
    	
    	$scope.mode = 'create';
    	BookCategory.create = function() {
			var deferred = $q.defer();
			deferred.resolve({data:'bookCategorySaved'});
			return deferred.promise;
		}
    	
    	// when
    	$scope.save();
    	$scope.$apply();
    	
    	// then
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    	expect($scope.bookCategory).toBe('bookCategorySaved');
    	expect(MessageHandler.addSuccess).toHaveBeenCalledWith('save ok');
    });
    
    it('save : update', function() {
    	// given
    	$scope.bookCategory = {bookId:'1', categoryId:'2', name:'bookCategory'};
    	
    	$scope.mode = 'update';
    	BookCategory.update = function() {
			var deferred = $q.defer();
			deferred.resolve({data:'bookCategorySaved'});
			return deferred.promise;
		}
    	
    	// when
    	$scope.save();
    	$scope.$apply();
    	
    	// then
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    	expect($scope.bookCategory).toBe('bookCategorySaved');
    	expect(MessageHandler.addSuccess).toHaveBeenCalledWith('save ok');
    });
    
    it('delete', function() {
    	// given
    	BookCategory.delete = function() {
			var deferred = $q.defer();
			deferred.resolve(null);
			return deferred.promise;
		}
    	spyOn($scope, "goToBookCategoryList");
    	
    	// when
    	$scope.delete('1', '2');
    	$scope.$apply();
    	
    	// then
    	expect($scope.goToBookCategoryList).toHaveBeenCalled();
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    });
    
    it('init : bookCategory create page', function() {
    	// given
		$location.path.andCallFake(function() {
        	return "/bookCategory/new";
       	});

		// when
		$scope.$apply();
		
		// then
    	expect($scope.mode).toBeNull();
    	expect($scope.bookCategory).toBeNull();
    	expect($scope.bookCategorys).toBe('bookCategory1');
    	expect(Object.keys($scope.items).length).toBe(0);
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    });
	
  });
});