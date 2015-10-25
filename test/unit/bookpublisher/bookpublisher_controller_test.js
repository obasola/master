'use strict';

/* jasmine specs for controllers go here */

describe('controllers', function(){
  beforeEach(module('bookPublisher.module'));
  
  describe('BookPublisherCtrl', function(){
    var BookPublisherCtrl, BookPublisher,$rootScope, $scope, $routeParams, $httpBackend, $location, MessageHandler, $q, $controller;
	  
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

    	// BookPublisher service
    	BookPublisher = {
    		getAll: function() {
    			var deferred = $q.defer();
    			deferred.resolve({data:'bookPublisher1'});
    			return deferred.promise;
    		}
    	};
		
				BookPublisherCtrl = $controller('BookPublisherCtrl', {
    		'BookPublisher': BookPublisher,
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
    	expect($scope.bookPublisher).toBeNull();
    	expect($scope.bookPublishers).toBe('bookPublisher1');
    	expect(Object.keys($scope.items).length).toBe(0);
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    });
    
    it('refreshBookPublisherList', function() {
    	// given
    	BookPublisher.getAll = function() {
			var deferred = $q.defer();
			deferred.resolve({data:'bookPublisher2'});
			return deferred.promise;
		}
    	
    	// when
    	$scope.refreshBookPublisherList();
    	$scope.$apply();

    	// then
    	$rootScope.$apply();
    	expect($scope.bookPublishers).toBe('bookPublisher2');
    });
    
    it('refreshBookPublisher', function() {
    	// given
    	BookPublisher.get = function(publisherId, bookId) {
			var deferred = $q.defer();
			deferred.resolve({data:'bookPublisher'+publisherId+bookId});
			return deferred.promise;
		}
    	
    	// when
    	$scope.refreshBookPublisher('1', '2');
    	$scope.$apply();
    	
    	// then
    	expect($scope.bookPublisher).toBe('bookPublisher'+'1'+'2');
    });
    
	it('goToBookPublisherList', function() {
    	// given
    	spyOn($scope, "refreshBookPublisherList");
    	
    	// when
    	$scope.goToBookPublisherList();
    	$scope.$apply();
    	
    	// then
    	expect($scope.refreshBookPublisherList).toHaveBeenCalled();
    	expect($location.path).toHaveBeenCalledWith('/bookPublisher');
    });
    
    it('goToBookPublisher', function() {
    	// given
    	spyOn($scope, "refreshBookPublisher");
    	var publisherId = 1;
    	var bookId = 2;
    	
    	// when
    	$scope.goToBookPublisher(publisherId, bookId);
    	$scope.$apply();
    	
    	// then
    	expect($scope.refreshBookPublisher).toHaveBeenCalledWith(publisherId, bookId);
    	expect($location.path).toHaveBeenCalledWith('/bookPublisher'+'/'+publisherId+'/'+bookId);
    });
    
    it('save : create', function() {
    	// given
    	$scope.bookPublisher = {publisherId:'1', bookId:'2', name:'bookPublisher'};
    	
    	$scope.mode = 'create';
    	BookPublisher.create = function() {
			var deferred = $q.defer();
			deferred.resolve({data:'bookPublisherSaved'});
			return deferred.promise;
		}
    	
    	// when
    	$scope.save();
    	$scope.$apply();
    	
    	// then
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    	expect($scope.bookPublisher).toBe('bookPublisherSaved');
    	expect(MessageHandler.addSuccess).toHaveBeenCalledWith('save ok');
    });
    
    it('save : update', function() {
    	// given
    	$scope.bookPublisher = {publisherId:'1', bookId:'2', name:'bookPublisher'};
    	
    	$scope.mode = 'update';
    	BookPublisher.update = function() {
			var deferred = $q.defer();
			deferred.resolve({data:'bookPublisherSaved'});
			return deferred.promise;
		}
    	
    	// when
    	$scope.save();
    	$scope.$apply();
    	
    	// then
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    	expect($scope.bookPublisher).toBe('bookPublisherSaved');
    	expect(MessageHandler.addSuccess).toHaveBeenCalledWith('save ok');
    });
    
    it('delete', function() {
    	// given
    	BookPublisher.delete = function() {
			var deferred = $q.defer();
			deferred.resolve(null);
			return deferred.promise;
		}
    	spyOn($scope, "goToBookPublisherList");
    	
    	// when
    	$scope.delete('1', '2');
    	$scope.$apply();
    	
    	// then
    	expect($scope.goToBookPublisherList).toHaveBeenCalled();
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    });
    
    it('init : bookPublisher create page', function() {
    	// given
		$location.path.andCallFake(function() {
        	return "/bookPublisher/new";
       	});

		// when
		$scope.$apply();
		
		// then
    	expect($scope.mode).toBeNull();
    	expect($scope.bookPublisher).toBeNull();
    	expect($scope.bookPublishers).toBe('bookPublisher1');
    	expect(Object.keys($scope.items).length).toBe(0);
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    });
	
  });
});