(function() {
	'use strict';
	angular.module('mlmlogicq').controller('AlertController',
			       [ '$scope', '$http','$rootScope','AppConstants',
					function($scope, $http,$rootScope,AppConstants) {
			    	   
			    	$scope.alerts = [];
			    	  $rootScope.$on("callAddAlert", function(type,msg){
			    		  $scope.addAlert(msg);
			           });
			    	
			   		$scope.addAlert = function(msg) {
			   			$scope.alerts.push({
			   				type : msg.type,
			   				msg : msg.msg
			   			});
			   		};
			   		$scope.closeAlert = function(index) {
			   			$scope.alerts.splice(index, 1);
			   		}; 
					} ]);
}());