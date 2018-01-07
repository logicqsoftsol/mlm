
(function () {
	'use strict';
	angular.module('mlmlogicq').factory('UserDetailsService', ['$http','AppConstants' ,function ($http ,AppConstants){

		return {
			getUserProfileForNetwork: function ($scope) {
				return  $http({
					method: 'GET',
					url: AppConstants.hostName+AppConstants.getUserProfileURL+"/"+$scope.networkid,
					//url: AppConstants.hostName+AppConstants.hostPort+AppConstants.applicationName+AppConstants.getUserProfileURL+"/"+$scope.networkid,
					dataType :'json',
					data : ''
				})
		},
		
		getUserProfileDetails: function ($scope) {
			return  $http({
				method: 'GET',
				url: AppConstants.hostName+AppConstants.getUserProfileDetailsURL+"/"+$scope.viewuser,
				//url: AppConstants.hostName+AppConstants.hostPort+AppConstants.applicationName+AppConstants.getUserProfileURL+"/"+$scope.networkid,
				dataType :'json',
				data : ''
			})
	},
	
	reloadNetworkCount: function ($scope) {
		return  $http({
			method: 'GET',
			url: AppConstants.hostName+AppConstants.reloadNetworkCountURL,
			//url: AppConstants.hostName+AppConstants.hostPort+AppConstants.applicationName+AppConstants.getUserProfileURL+"/"+$scope.networkid,
			dataType :'json',
			data : ''
		})
},
		
		getUserNetwork: function ($scope) {
			return  $http({
				method: 'GET',
				url: AppConstants.hostName+AppConstants.fetchUserNetworkDetailsURL,
				//url: AppConstants.hostName+AppConstants.hostPort+AppConstants.applicationName+AppConstants.getUserProfileURL+"/"+$scope.networkid,
				dataType :'json',
				data : ''
			})
	},
		saveUserProfileDetails: function (request) {
			var url = AppConstants.hostName+AppConstants.saveUserProfileDetailsURL;
			//var url = AppConstants.hostName+AppConstants.hostPort+AppConstants.applicationName+AppConstants.saveUserProfileDetailsURL;
			return  $http.post(url,request.user)
		},
		sendOTP: function ($scope) {
			var url = AppConstants.hostName+AppConstants.sendOTPURL+"/"+$scope.approval.verificationtypelabel;
			//var url = AppConstants.hostName+AppConstants.hostPort+AppConstants.applicationName+AppConstants.sendOTPURL+"/"+$scope.approval.verificationtypelabel;
			return  $http.post(url," ")
		},
		validateOTP: function ($scope) {
			var url = AppConstants.hostName+AppConstants.validateOTPURL+"/"+$scope.approval.verificationtypelabel+"/"+$scope.approval.otpcode;
			//var url = AppConstants.hostName+AppConstants.hostPort+AppConstants.applicationName+AppConstants.validateOTPURL+"/"+$scope.approval.verificationtypelabel+"/"+$scope.approval.otpcode;
			return  $http.post(url," ")
		},
		createEncashRequest:function (request) {
			var url = AppConstants.hostName+AppConstants.createEncashRequestURL;
			//var url = AppConstants.hostName+AppConstants.hostPort+AppConstants.applicationName+AppConstants.createEncashRequestURL;
			return  $http.post(url,request.encashdetails)
		},
		resetPassword:function (request) {
			var url = AppConstants.hostName+AppConstants.resetPasswordURL;
			//var url = AppConstants.hostName+AppConstants.hostPort+AppConstants.applicationName+AppConstants.createEncashRequestURL;
			return  $http.post(url,request.passwordRequest)
		},
		uploadImages: function (request) {
			var url = AppConstants.hostName+AppConstants.uploadImageURL;
			//var url = AppConstants.hostName+AppConstants.hostPort+AppConstants.applicationName+AppConstants.saveUserProfileDetailsURL;
			return  $http.post(url,request.fd,{
            headers: {
              'Content-Type': undefined
            },
            transformRequest: angular.identity
			})
		},
		pollTaskDetails: function () {
			return  $http({
				method: 'GET',
				url: AppConstants.hostName+AppConstants.pollTaskListURL,
				//url: AppConstants.hostName+AppConstants.hostPort+AppConstants.applicationName+AppConstants.pollTaskListURL,
				dataType :'json',
				data : ''
			})
	},
	reloadUserWalletDetails: function ($scope) {
		return  $http({
			method: 'GET',
			url: AppConstants.hostName+AppConstants.reloadWalletStmntURL,
			//url: AppConstants.hostName+AppConstants.hostPort+AppConstants.applicationName+AppConstants.getUserProfileURL+"/"+$scope.networkid,
			dataType :'json',
			data : ''
		})
},
		}
}]);

}());