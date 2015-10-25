'use strict';

/* jasmine specs for controllers go here */

describe('controllers', function(){
  beforeEach(module('entry.module'));
  
  describe('EntryCtrl', function(){
    var EntryCtrl, Entry, Entrytype, $rootScope, $scope, $routeParams, $httpBackend, $location, MessageHandler, $q, $controller;
	  
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

    	// Entry service
    	Entry = {
    		getAll: function() {
    			var deferred = $q.defer();
    			deferred.resolve({data:'entry1'});
    			return deferred.promise;
    		}
    	};
		
				Entrytype = {
			getAllAsListItems: jasmine.createSpy("getAllAsListItems").andCallFake(function() {
				return [];
			})
		};

				EntryCtrl = $controller('EntryCtrl', {
    		'Entry': Entry,
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
    	expect($scope.entry).toBeNull();
    	expect($scope.entrys).toBe('entry1');
    	expect(Object.keys($scope.items).length).toBe(1);
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    });
    
    it('refreshEntryList', function() {
    	// given
    	Entry.getAll = function() {
			var deferred = $q.defer();
			deferred.resolve({data:'entry2'});
			return deferred.promise;
		}
    	
    	// when
    	$scope.refreshEntryList();
    	$scope.$apply();

    	// then
    	$rootScope.$apply();
    	expect($scope.entrys).toBe('entry2');
    });
    
    it('refreshEntry', function() {
    	// given
    	Entry.get = function(identry) {
			var deferred = $q.defer();
			deferred.resolve({data:'entry'+identry});
			return deferred.promise;
		}
    	
    	// when
    	$scope.refreshEntry('1');
    	$scope.$apply();
    	
    	// then
    	expect($scope.entry).toBe('entry'+'1');
    });
    
	it('goToEntryList', function() {
    	// given
    	spyOn($scope, "refreshEntryList");
    	
    	// when
    	$scope.goToEntryList();
    	$scope.$apply();
    	
    	// then
    	expect($scope.refreshEntryList).toHaveBeenCalled();
    	expect($location.path).toHaveBeenCalledWith('/entry');
    });
    
    it('goToEntry', function() {
    	// given
    	spyOn($scope, "refreshEntry");
    	var identry = 1;
    	
    	// when
    	$scope.goToEntry(identry);
    	$scope.$apply();
    	
    	// then
    	expect($scope.refreshEntry).toHaveBeenCalledWith(identry);
    	expect($location.path).toHaveBeenCalledWith('/entry'+'/'+identry);
    });
    
    it('save : create', function() {
    	// given
    	$scope.entry = {identry:'1', name:'entry'};
    	
    	$scope.mode = 'create';
    	Entry.create = function() {
			var deferred = $q.defer();
			deferred.resolve({data:'entrySaved'});
			return deferred.promise;
		}
    	
    	// when
    	$scope.save();
    	$scope.$apply();
    	
    	// then
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    	expect($scope.entry).toBe('entrySaved');
    	expect(MessageHandler.addSuccess).toHaveBeenCalledWith('save ok');
    });
    
    it('save : update', function() {
    	// given
    	$scope.entry = {identry:'1', name:'entry'};
    	
    	$scope.mode = 'update';
    	Entry.update = function() {
			var deferred = $q.defer();
			deferred.resolve({data:'entrySaved'});
			return deferred.promise;
		}
    	
    	// when
    	$scope.save();
    	$scope.$apply();
    	
    	// then
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    	expect($scope.entry).toBe('entrySaved');
    	expect(MessageHandler.addSuccess).toHaveBeenCalledWith('save ok');
    });
    
    it('delete', function() {
    	// given
    	Entry.delete = function() {
			var deferred = $q.defer();
			deferred.resolve(null);
			return deferred.promise;
		}
    	spyOn($scope, "goToEntryList");
    	
    	// when
    	$scope.delete('1');
    	$scope.$apply();
    	
    	// then
    	expect($scope.goToEntryList).toHaveBeenCalled();
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    });
    
    it('init : entry create page', function() {
    	// given
		$location.path.andCallFake(function() {
        	return "/entry/new";
       	});

		// when
		$scope.$apply();
		
		// then
    	expect($scope.mode).toBeNull();
    	expect($scope.entry).toBeNull();
    	expect($scope.entrys).toBe('entry1');
    	expect(Object.keys($scope.items).length).toBe(1);
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    });
	
  });
});