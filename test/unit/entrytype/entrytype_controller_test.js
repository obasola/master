'use strict';

/* jasmine specs for controllers go here */

describe('controllers', function(){
  beforeEach(module('entrytype.module'));
  
  describe('EntrytypeCtrl', function(){
    var EntrytypeCtrl, Entrytype,$rootScope, $scope, $routeParams, $httpBackend, $location, MessageHandler, $q, $controller;
	  
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

    	// Entrytype service
    	Entrytype = {
    		getAll: function() {
    			var deferred = $q.defer();
    			deferred.resolve({data:'entrytype1'});
    			return deferred.promise;
    		}
    	};
		
				EntrytypeCtrl = $controller('EntrytypeCtrl', {
    		'Entrytype': Entrytype,
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
    	expect($scope.entrytype).toBeNull();
    	expect($scope.entrytypes).toBe('entrytype1');
    	expect(Object.keys($scope.items).length).toBe(0);
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    });
    
    it('refreshEntrytypeList', function() {
    	// given
    	Entrytype.getAll = function() {
			var deferred = $q.defer();
			deferred.resolve({data:'entrytype2'});
			return deferred.promise;
		}
    	
    	// when
    	$scope.refreshEntrytypeList();
    	$scope.$apply();

    	// then
    	$rootScope.$apply();
    	expect($scope.entrytypes).toBe('entrytype2');
    });
    
    it('refreshEntrytype', function() {
    	// given
    	Entrytype.get = function(id) {
			var deferred = $q.defer();
			deferred.resolve({data:'entrytype'+id});
			return deferred.promise;
		}
    	
    	// when
    	$scope.refreshEntrytype('1');
    	$scope.$apply();
    	
    	// then
    	expect($scope.entrytype).toBe('entrytype'+'1');
    });
    
	it('goToEntrytypeList', function() {
    	// given
    	spyOn($scope, "refreshEntrytypeList");
    	
    	// when
    	$scope.goToEntrytypeList();
    	$scope.$apply();
    	
    	// then
    	expect($scope.refreshEntrytypeList).toHaveBeenCalled();
    	expect($location.path).toHaveBeenCalledWith('/entrytype');
    });
    
    it('goToEntrytype', function() {
    	// given
    	spyOn($scope, "refreshEntrytype");
    	var id = 1;
    	
    	// when
    	$scope.goToEntrytype(id);
    	$scope.$apply();
    	
    	// then
    	expect($scope.refreshEntrytype).toHaveBeenCalledWith(id);
    	expect($location.path).toHaveBeenCalledWith('/entrytype'+'/'+id);
    });
    
    it('save : create', function() {
    	// given
    	$scope.entrytype = {id:'1', name:'entrytype'};
    	
    	$scope.mode = 'create';
    	Entrytype.create = function() {
			var deferred = $q.defer();
			deferred.resolve({data:'entrytypeSaved'});
			return deferred.promise;
		}
    	
    	// when
    	$scope.save();
    	$scope.$apply();
    	
    	// then
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    	expect($scope.entrytype).toBe('entrytypeSaved');
    	expect(MessageHandler.addSuccess).toHaveBeenCalledWith('save ok');
    });
    
    it('save : update', function() {
    	// given
    	$scope.entrytype = {id:'1', name:'entrytype'};
    	
    	$scope.mode = 'update';
    	Entrytype.update = function() {
			var deferred = $q.defer();
			deferred.resolve({data:'entrytypeSaved'});
			return deferred.promise;
		}
    	
    	// when
    	$scope.save();
    	$scope.$apply();
    	
    	// then
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    	expect($scope.entrytype).toBe('entrytypeSaved');
    	expect(MessageHandler.addSuccess).toHaveBeenCalledWith('save ok');
    });
    
    it('delete', function() {
    	// given
    	Entrytype.delete = function() {
			var deferred = $q.defer();
			deferred.resolve(null);
			return deferred.promise;
		}
    	spyOn($scope, "goToEntrytypeList");
    	
    	// when
    	$scope.delete('1');
    	$scope.$apply();
    	
    	// then
    	expect($scope.goToEntrytypeList).toHaveBeenCalled();
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    });
    
    it('init : entrytype create page', function() {
    	// given
		$location.path.andCallFake(function() {
        	return "/entrytype/new";
       	});

		// when
		$scope.$apply();
		
		// then
    	expect($scope.mode).toBeNull();
    	expect($scope.entrytype).toBeNull();
    	expect($scope.entrytypes).toBe('entrytype1');
    	expect(Object.keys($scope.items).length).toBe(0);
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    });
	
  });
});