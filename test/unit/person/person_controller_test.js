'use strict';

/* jasmine specs for controllers go here */

describe('controllers', function(){
  beforeEach(module('person.module'));
  
  describe('PersonCtrl', function(){
    var PersonCtrl, Person,$rootScope, $scope, $routeParams, $httpBackend, $location, MessageHandler, $q, $controller;
	  
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

    	// Person service
    	Person = {
    		getAll: function() {
    			var deferred = $q.defer();
    			deferred.resolve({data:'person1'});
    			return deferred.promise;
    		}
    	};
		
				PersonCtrl = $controller('PersonCtrl', {
    		'Person': Person,
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
    	expect($scope.person).toBeNull();
    	expect($scope.persons).toBe('person1');
    	expect(Object.keys($scope.items).length).toBe(0);
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    });
    
    it('refreshPersonList', function() {
    	// given
    	Person.getAll = function() {
			var deferred = $q.defer();
			deferred.resolve({data:'person2'});
			return deferred.promise;
		}
    	
    	// when
    	$scope.refreshPersonList();
    	$scope.$apply();

    	// then
    	$rootScope.$apply();
    	expect($scope.persons).toBe('person2');
    });
    
    it('refreshPerson', function() {
    	// given
    	Person.get = function(idperson) {
			var deferred = $q.defer();
			deferred.resolve({data:'person'+idperson});
			return deferred.promise;
		}
    	
    	// when
    	$scope.refreshPerson('1');
    	$scope.$apply();
    	
    	// then
    	expect($scope.person).toBe('person'+'1');
    });
    
	it('goToPersonList', function() {
    	// given
    	spyOn($scope, "refreshPersonList");
    	
    	// when
    	$scope.goToPersonList();
    	$scope.$apply();
    	
    	// then
    	expect($scope.refreshPersonList).toHaveBeenCalled();
    	expect($location.path).toHaveBeenCalledWith('/person');
    });
    
    it('goToPerson', function() {
    	// given
    	spyOn($scope, "refreshPerson");
    	var idperson = 1;
    	
    	// when
    	$scope.goToPerson(idperson);
    	$scope.$apply();
    	
    	// then
    	expect($scope.refreshPerson).toHaveBeenCalledWith(idperson);
    	expect($location.path).toHaveBeenCalledWith('/person'+'/'+idperson);
    });
    
    it('save : create', function() {
    	// given
    	$scope.person = {idperson:'1', name:'person'};
    	
    	$scope.mode = 'create';
    	Person.create = function() {
			var deferred = $q.defer();
			deferred.resolve({data:'personSaved'});
			return deferred.promise;
		}
    	
    	// when
    	$scope.save();
    	$scope.$apply();
    	
    	// then
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    	expect($scope.person).toBe('personSaved');
    	expect(MessageHandler.addSuccess).toHaveBeenCalledWith('save ok');
    });
    
    it('save : update', function() {
    	// given
    	$scope.person = {idperson:'1', name:'person'};
    	
    	$scope.mode = 'update';
    	Person.update = function() {
			var deferred = $q.defer();
			deferred.resolve({data:'personSaved'});
			return deferred.promise;
		}
    	
    	// when
    	$scope.save();
    	$scope.$apply();
    	
    	// then
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    	expect($scope.person).toBe('personSaved');
    	expect(MessageHandler.addSuccess).toHaveBeenCalledWith('save ok');
    });
    
    it('delete', function() {
    	// given
    	Person.delete = function() {
			var deferred = $q.defer();
			deferred.resolve(null);
			return deferred.promise;
		}
    	spyOn($scope, "goToPersonList");
    	
    	// when
    	$scope.delete('1');
    	$scope.$apply();
    	
    	// then
    	expect($scope.goToPersonList).toHaveBeenCalled();
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    });
    
    it('init : person create page', function() {
    	// given
		$location.path.andCallFake(function() {
        	return "/person/new";
       	});

		// when
		$scope.$apply();
		
		// then
    	expect($scope.mode).toBeNull();
    	expect($scope.person).toBeNull();
    	expect($scope.persons).toBe('person1');
    	expect(Object.keys($scope.items).length).toBe(0);
    	expect(MessageHandler.cleanMessage).toHaveBeenCalled();
    });
	
  });
});