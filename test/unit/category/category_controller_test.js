'use strict';

/* jasmine specs for controllers go here */

describe('controllers', function(){
  beforeEach(module('category.module'));
  
  describe('CategoryCtrl', function(){
    var CategoryCtrl, Category,$rootScope, $scope, $routeParams, $httpBackend, $location, MessageHandler, $q, $controller;
	  
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

    	// Category service
    	Category = {
    		getAll: function() {
    			var deferred = $q.defer();
    			deferred.resolve({data:'category1'});
    			return deferred.promise;
    		}
    	};
		
				CategoryCtrl = $controller('CategoryCtrl', {
    		'Category': Category,
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
    	expect($scope.category).toBeNull();
    	expect($scope.categorys).toBe('category1');
    	expect(Object.keys($scope.items).length).toBe(0);
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    });
    
    it('refreshCategoryList', function() {
    	// given
    	Category.getAll = function() {
			var deferred = $q.defer();
			deferred.resolve({data:'category2'});
			return deferred.promise;
		}
    	
    	// when
    	$scope.refreshCategoryList();
    	$scope.$apply();

    	// then
    	$rootScope.$apply();
    	expect($scope.categorys).toBe('category2');
    });
    
    it('refreshCategory', function() {
    	// given
    	Category.get = function(id) {
			var deferred = $q.defer();
			deferred.resolve({data:'category'+id});
			return deferred.promise;
		}
    	
    	// when
    	$scope.refreshCategory('1');
    	$scope.$apply();
    	
    	// then
    	expect($scope.category).toBe('category'+'1');
    });
    
	it('goToCategoryList', function() {
    	// given
    	spyOn($scope, "refreshCategoryList");
    	
    	// when
    	$scope.goToCategoryList();
    	$scope.$apply();
    	
    	// then
    	expect($scope.refreshCategoryList).toHaveBeenCalled();
    	expect($location.path).toHaveBeenCalledWith('/category');
    });
    
    it('goToCategory', function() {
    	// given
    	spyOn($scope, "refreshCategory");
    	var id = 1;
    	
    	// when
    	$scope.goToCategory(id);
    	$scope.$apply();
    	
    	// then
    	expect($scope.refreshCategory).toHaveBeenCalledWith(id);
    	expect($location.path).toHaveBeenCalledWith('/category'+'/'+id);
    });
    
    it('save : create', function() {
    	// given
    	$scope.category = {id:'1', name:'category'};
    	
    	$scope.mode = 'create';
    	Category.create = function() {
			var deferred = $q.defer();
			deferred.resolve({data:'categorySaved'});
			return deferred.promise;
		}
    	
    	// when
    	$scope.save();
    	$scope.$apply();
    	
    	// then
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    	expect($scope.category).toBe('categorySaved');
    	expect(MessageHandler.addSuccess).toHaveBeenCalledWith('save ok');
    });
    
    it('save : update', function() {
    	// given
    	$scope.category = {id:'1', name:'category'};
    	
    	$scope.mode = 'update';
    	Category.update = function() {
			var deferred = $q.defer();
			deferred.resolve({data:'categorySaved'});
			return deferred.promise;
		}
    	
    	// when
    	$scope.save();
    	$scope.$apply();
    	
    	// then
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    	expect($scope.category).toBe('categorySaved');
    	expect(MessageHandler.addSuccess).toHaveBeenCalledWith('save ok');
    });
    
    it('delete', function() {
    	// given
    	Category.delete = function() {
			var deferred = $q.defer();
			deferred.resolve(null);
			return deferred.promise;
		}
    	spyOn($scope, "goToCategoryList");
    	
    	// when
    	$scope.delete('1');
    	$scope.$apply();
    	
    	// then
    	expect($scope.goToCategoryList).toHaveBeenCalled();
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    });
    
    it('init : category create page', function() {
    	// given
		$location.path.andCallFake(function() {
        	return "/category/new";
       	});

		// when
		$scope.$apply();
		
		// then
    	expect($scope.mode).toBeNull();
    	expect($scope.category).toBeNull();
    	expect($scope.categorys).toBe('category1');
    	expect(Object.keys($scope.items).length).toBe(0);
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    });
	
  });
});