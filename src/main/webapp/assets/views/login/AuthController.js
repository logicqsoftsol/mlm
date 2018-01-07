(function() {
	'use strict';
	angular.module('mlmlogicq').controller(
			'AuthController',
			[
			 '$scope',
			 '$http',
			 '$location',
			 'AuthenticationService',
			 function($scope, $http, $location,AuthenticationService) {
				// AuthenticationService.loadDefaultUsers().success(
					//	 function(response) {
							 $location.path('/login');
						// });
			 } ]);
}());
