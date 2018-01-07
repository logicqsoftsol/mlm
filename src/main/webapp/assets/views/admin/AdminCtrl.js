(function() {
	'use strict';
	angular.module('mlmlogicq').controller(
			'AdminCtrl',
			[
			 '$scope',
			 '$rootScope',
			 '$http',
			 '$location',
			 '$localStorage',
			 '$sessionStorage',
			 '$exceptionHandler',
			 '$state',
			 'UserDetailsService',
			 'UserHelper',
			 function($scope,$rootScope,$http,$location,$localStorage,$sessionStorage,$exceptionHandler,$state,UserDetailsService,UserHelper) {
				   //Variable declare
				    $scope.$state = $state;
				    $scope.menuItems = [];
				    $scope.networkjson={};
					$scope.networkid='';
					$scope.networkcreated='false';	
					$scope.request={};
					$scope.password={};
					$scope.userprofile={};
				    angular.forEach($state.get(), function (item) {
				        if (item.data && item.data.visible) {
				            $scope.menuItems.push({name: item.name, text: item.data.text});
				        }
						
				    });
				    if(null!=$localStorage.profile && null!=$localStorage.profile.userprofile){
						 $scope.userprofile.firstname=$localStorage.profile.userprofile.firstname;
						 $scope.userprofile.lastname=$localStorage.profile.userprofile.lastname;
				    	 $scope.userprofile.username=$localStorage.profile.userprofile.logindetails.username;
						 $scope.userprofile.mobilenumber=$localStorage.profile.userprofile.conatctDetails.mobilenumber;
				    }
					$('#networkmember-chart').on('click', function(event) {
							var networkid = $('#networkmembernodeid').val();
							$scope.networkid=networkid;
							UserDetailsService.getUserProfileForNetwork( $scope).success(function(data, status) {
								$localStorage.profile=data;
								$scope.networkcreated='true';
								$scope.displayProfile();
									}).error(function(data, status) {
									   var errormsg='Unable to Populate for Calnder event details : '+status;
										$rootScope.$emit("callAddAlert", {type:'danger',msg:errormsg});
										$exceptionHandler(errormsg);
									});
					 });
					 
				$scope.displayNetworkProfie= function(networkjson){
					$('#networkmember-chart').orgchart({
				      'data' : $scope.networkjson,
				      'depth': 2,
				      'nodeContent': 'title',
				      'exportButton': false,
				      'exportFilename': 'MyOrgChart'
				    });
					
				};
			$scope.cancalePayment=function(){
                 $location.path('/dashboard/overview');
				}
			$scope.proceedPayment=function(){
				}
				$scope.requestPasswordChange=function(){
					$scope.request.passwordRequest={
							oldpassword:$scope.password.currentPassword,
							newPassword:$scope.password.newPassword,
							confirmPasword:$scope.password.confirmPassword,
							date:new Date()
					},
					UserDetailsService.resetPassword($scope.request).success(function(data, status){
					     	$location.path('/login');
							}).error(function(data, status) {
							   var errormsg='Unable to Populate for Calnder event details : '+status;
								$rootScope.$emit("callAddAlert", {type:'danger',msg:errormsg});
								$exceptionHandler(errormsg);
							});
					}
				
					
			 } ]);
}());
