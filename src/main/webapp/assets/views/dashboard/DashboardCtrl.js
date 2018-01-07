(function() {
	'use strict';
	angular.module('mlmlogicq').controller(
			'DashboardCtrl',
			[
			 '$scope',
			 '$rootScope',
			 '$http',
			 '$timeout',
			 '$location',
			 '$localStorage',
			 '$sessionStorage',
			 '$exceptionHandler',
			 '$state',
			 'UserDetailsService',
			 'UserHelper',
			 'AdminService',
			 function($scope,$rootScope,$http,$timeout,$location,$localStorage,$sessionStorage,$exceptionHandler,$state,UserDetailsService,UserHelper,AdminService) {
				   //Variable declare
				    $scope.$state = $state;
				    $scope.menuItems = [];
					//User profile Details 
					$scope.userdetails={};
					$scope.userprofile={};
					$scope.user={};
					$scope.user.logindetails={};
				    $scope.user.conatctDetails={};
					$scope.user.networkinfo={};
					$scope.user.walletdetails={};
					$scope.user.walletdetails.walletStatement={};
					$scope.user.userperformance={};
					$scope.user.bankdetails={};
					$scope.user.socialdetails={};
				    $scope.networkjson={};
					$scope.networkid='';
					$scope.networkcreated='false';	
					$scope.approval={};
				    $scope.otp={};
					$scope.approvalpendinglist=[];
					$scope.request={};
					$scope.tasklist={};
					$scope.tasklist.count={};
					$scope.request.task={};
					$scope.encashdetails={};
					$scope.documentid={};
					$scope.user.image="assets/images/dummyuser.jpg";
					$scope.taskPoller=[];
					$scope.profileDisplayName={};
					$scope.maxFileUploadSize=3000000;
					$scope.networkinfolist=[];
					$scope.viewuserprofile={};
					$scope.paymentdetails={};
					$scope.addMoney={};
					$scope.networkcountlist=[];
					$scope.paymentrollback=false;
					$scope.txnrollback={};
					
					
					angular.forEach($state.get(), function (item) {
				        if (item.data && item.data.visible) {
				            $scope.menuItems.push({name: item.name, text: item.data.text});
				        }
						
				    });
				    
					
					$('#networkmember-chart').on('click', function(event) {
							var networkid = $('#networkmembernodeid').val();
							$scope.networkid=networkid;
							UserDetailsService.getUserProfileForNetwork( $scope).success(function(data, status) {
								$localStorage.profile=data;
								$scope.displayProfile();
									}).error(function(data, status) {
									   var errormsg='Unable to Populate for Calnder event details : '+status;
										//$rootScope.$emit("callAddAlert", {type:'danger',msg:errormsg});
										alert(errormsg);
									   $exceptionHandler(errormsg);
									});
					 });
					 
				$scope.displayNetworkProfie= function(networkjson){
					$('#networkmember-chart').orgchart({
				      'data' : networkjson,
				      'depth': 2,
				      'nodeContent': 'title',
				      'exportButton': false,
				      'exportFilename': 'MyOrgChart'
				    });
					
				};
				$scope.addMoneyToWallet=function(){
                   $location.path('/dashboard/payment');
				}

				
				$scope.onClickViewSupportHands=function(){
					document.getElementById('networkmember-chart').innerHTML=null;
					UserDetailsService.getUserNetwork( $scope).success(function(data, status) {
						$scope.displayNetworkProfie(data.networkjson);
					$scope.user.walletdetails.walletStatement.payout=data.walletStatement.payout;
					$scope.user.walletdetails.walletStatement.maxencashable=data.walletStatement.maxencashable;
					$scope.user.walletdetails.walletStatement.currentbalance=data.walletStatement.currentbalance;
					$scope.user.walletdetails.walletStatement.walletlastupdate=new Date(data.walletStatement.walletlastupdate);
							}).error(function(data, status) {
							   var errormsg='Unable to display network: '+status;
							//	$rootScope.$emit("callAddAlert", {type:'danger',msg:errormsg});
							   alert(errormsg);
								$exceptionHandler(errormsg);
							});
				
				}
				
					if($localStorage.profile.document.documentPath!=null){
						$scope.user.image=$localStorage.profile.document.documentPath;
					}
				
				if(!$localStorage.profile.mobilenoVerified){
			    $scope.approval.mobilenoVerified='Pending';	
				$scope.approvalpendinglist.push({id:1, name:'MobileNumber'});
				}else{
					$scope.approval.mobilenoVerified='Approved';
				}
				/*if(!$localStorage.profile.emailVerified){
			    $scope.approval.emailVerified='Pending';
                $scope.approvalpendinglist.push({id:2, name:'Email'});				
				}else{
					$scope.approval.emailVerified='Approved';
				}*/
					
			     if(!$localStorage.profile.adminVerified){
			       $scope.approval.adminVerified='Pending';
				   $scope.approvalpendinglist.push({id:2, name:'Admin'});	
				}else{
					$scope.approval.adminVerified='Approved';
				}
				
				 $scope.$watch('approval.verificationfor', function(newVal, oldVal){
												angular.forEach($scope.approvalpendinglist, function(value, key) {
													if(value.name==newVal){
														 $scope.approval.verificationtypelabel=value.name;
                                                        $scope.request.otpdetails={};  
                                                      /*  if(value.name=='Email'){
                                                        	  $scope.approval.verificationvalue=$localStorage.profile.userprofile.conatctDetails.email;
                                                        }*/
                                                        if(value.name=='MobileNumber'){
                                                        	$scope.approval.verificationvalue=$localStorage.profile.userprofile.conatctDetails.mobilenumber;
                                                        }
														 if(value.name=='MobileNumber'){
															   UserDetailsService.sendOTP($scope).success(function(data, status) {
															   	}).error(function(data, status) {
															   			var errormsg='Unable to Send OTP  '+status;
															   			//$rootScope.$emit("callAddAlert", {type:'danger',msg:errormsg});
															   		     alert(errormsg);
															   			$exceptionHandler(errormsg);
															   	});
														  }	
                                                          if(value.name=='Admin'){
															   $scope.approval.otpfor='ADMIN';
															    $scope.approval.otpsendto=$scope.approval.verificationvalue;
															  $scope.approval.verificationvalue='Admin Notify';
														  }															  
														 
													}	
													});
												});
												
				$scope.validateOTP=	function (){
						 UserDetailsService.validateOTP($scope).success(function(data, status) {
							 $localStorage.profile.mobilenoVerified=data.mobilenoVerified;
							 $localStorage.profile.emailVerified=data.emailVerified;
							 $localStorage.profile.adminVerified=data.adminVerified;
							angular.element('#approvalmodal').modal('hide');
							$scope.approvalpendinglist=[];
							if(!$localStorage.profile.mobilenoVerified){
			    $scope.approval.mobilenoVerified='Pending';	
			    $scope.approvalpendinglist.push({id:1, name:'MobileNumber'});
				}else{
					$scope.approval.mobilenoVerified='Approved';
				}
				/*if(!$localStorage.profile.emailVerified){
			    $scope.approval.emailVerified='Pending';
                $scope.approvalpendinglist.push({id:2, name:'Email'});				
				}else{
					$scope.approval.emailVerified='Approved';
				
				}*/
				}).error(function(data, status) {
									  var errormsg='Unable to validate OTP  '+status;
										//$rootScope.$emit("callAddAlert", {type:'danger',msg:errormsg});
									  alert(errormsg);
										$exceptionHandler(errormsg);
									});
					
				}	
				
				$scope.getNotificationDetails= function(){
					UserDetailsService.pollTaskDetails().success(function(data, status) {
					   	$scope.tasklist=data.tasklist;
						$scope.tasklist.count=data.tasklist.length;
						$scope.taskPoller=data.tasklist;
					  }).error(function(data, status) {
						  var errormsg='Unable to Update Your Task  '+status;
							//$rootScope.$emit("callAddAlert", {type:'danger',msg:errormsg});
						  alert(errormsg);
							$exceptionHandler(errormsg);
						});     
				}
				
				$scope.poller = function() {
				if($scope.taskPoller.length>0){
					$scope.tasklist.count=$scope.taskPoller.length;
					$scope.taskPoller=[];
				}
				$timeout($scope.poller, 4000); 
				};
				$scope.poller();
				  
				  $scope.clearRequestForm= function(){
					  $scope.approval.verificationvalue='';
					  $scope.approval.otpcode='';
				  }
				  
				  if(null!=$localStorage.profile.userprofile){
					  $scope.profileDisplayName.firstname=$localStorage.profile.userprofile.firstname;
					  $scope.profileDisplayName.lastname=$localStorage.profile.userprofile.lastname;
				  }
				
				  
		     $scope.displayProfile = function () {
					 $scope.userdetails=$localStorage.profile;
					 $scope.usertype=$scope.userdetails.userprofile.logindetails.authorities[0].name;
					 $scope.tasklist=$scope.userdetails.tasklist;
                    if(null!=$scope.tasklist){
					$scope.tasklist.count=$scope.tasklist.length;	
					}				
				if($scope.usertype=='ADMIN'){
			    	$scope.taskreadonly='false';
			    	$scope.paymentrollback=true;
				}else{
					$scope.taskreadonly='true';
					$scope.paymentrollback=false;
				}
					$scope.profileDisplayName.firstname=$scope.userdetails.userprofile.firstname;
					$scope.profileDisplayName.lastname=$scope.userdetails.userprofile.lastname;
					 $scope.user.image=$scope.userdetails.document.documentPath;
				     $scope.user.firstname=$scope.userdetails.userprofile.firstname;
					 $scope.user.lastname=$scope.userdetails.userprofile.lastname;
					 $scope.user.dateofbirth=new Date($scope.userdetails.userprofile.dateofbirth);
					 $scope.user.gender=$scope.userdetails.userprofile.gender;
					$scope.user.conatctDetails.addressText=$scope.userdetails.userprofile.conatctDetails.addressText;
					$scope.user.conatctDetails.district=$scope.userdetails.userprofile.conatctDetails.district;
					$scope.user.conatctDetails.email=$scope.userdetails.userprofile.conatctDetails.email;
					$scope.user.conatctDetails.mobilenumber=$scope.userdetails.userprofile.conatctDetails.mobilenumber;
					$scope.user.networkinfo.memberlevel=$scope.userdetails.userprofile.networkinfo.memberlevel;
					$scope.user.walletdetails.walletnumber=$scope.userdetails.userprofile.walletdetails.walletnumber;
					$scope.user.walletdetails.walletStatement.payout=$scope.userdetails.walletStatement.payout;
					$scope.user.walletdetails.walletStatement.encashedAmount=$scope.userdetails.walletStatement.encashedAmount;
					$scope.user.walletdetails.walletStatement.currentbalance=$scope.userdetails.walletStatement.currentbalance;
					$scope.user.walletdetails.walletStatement.walletlastupdate=new Date($scope.userdetails.walletStatement.walletlastupdate);
					if(null!=$scope.userdetails.userprofile.socialdetails){
						$scope.user.socialdetails.fblink=$scope.userdetails.userprofile.socialdetails.fblink;
						$scope.user.socialdetails.gogglepluslink=$scope.userdetails.userprofile.googlePlusLink;
						$scope.user.socialdetails.youtubelink=$scope.userdetails.userprofile.youtubelink;
						$scope.user.socialdetails.twiter=$scope.userdetails.userprofile.twiter;
						$scope.user.socialdetails.linkedin=$scope.userdetails.userprofile.linkedinlink;
					}else{
						$scope.user.socialdetails.fblink="https://www.facebook.com/";
						$scope.user.socialdetails.gogglepluslink="https://plus.google.com/";
						$scope.user.socialdetails.youtubelink="https://www.youtube.com/";
						$scope.user.socialdetails.twiter="https://twitter.com/";
						$scope.user.socialdetails.linkedin="https://www.linkedin.com/";
					}
					if(null!=$scope.userdetails.userprofile.bankAccountDetails){
					$scope.user.bankdetails.bankname=$scope.userdetails.userprofile.bankAccountDetails.bankName;
					$scope.user.bankdetails.ifsccode=$scope.userdetails.userprofile.bankAccountDetails.ifsccode;
					$scope.user.bankdetails.accountNumber=$scope.userdetails.userprofile.bankAccountDetails.accountNumber;
					$scope.user.bankdetails.pancardno=$scope.userdetails.userprofile.bankAccountDetails.pancardno;
					$scope.user.bankdetails.accountHolderName=$scope.userdetails.userprofile.bankAccountDetails.accountHolderName;
		              }else{
		            	  $scope.user.bankdetails.bankname="DUMMY BANK";
		            	  $scope.user.bankdetails.ifsccode="DUMMY012";
		            	  $scope.user.bankdetails.accountNumber="AC00125343";
		            	  $scope.user.bankdetails.pancardno="DUMMYPAN";
		            	  $scope.user.bankdetails.accountHolderName="GETPAY";
		              }
					$scope.networkcountlist=$scope.userdetails.networkcountlist;
		        //$scope.user.userperformance.totalnetwork=$scope.userdetails.userperformance.network;
					//$scope.user.userperformance.totalmetting=$scope.userdetails.userperformance.metting;
					//$scope.user.userperformance.totaltask=$scope.userdetails.userperformance.task;
					//$scope.user.userperformance.totalincome=$scope.userdetails.userperformance.income;
					//$scope.user.userperformance.totalperformance=$scope.userdetails.userperformance.performancetype;
					//$scope.user.userperformance.totalrating=$scope.userdetails.userperformance.ratting;
		     };
					if(null!=$localStorage.profile.networkinfolist){
						$scope.networkinfolist=$localStorage.profile.networkinfolist;
					}
					$scope.searchMemberWithPatteren = function() {
						}
						
						$scope.populateWalletForMember = function(network) {
									$scope.viewuser=network.memberid;
									UserDetailsService.getUserProfileDetails($scope).success(function(data, status) {
										$scope.paymentdetails={};
										$scope.paymentdetails.walletnumber=$scope.userdetails.userprofile.walletdetails.walletnumber;
										$scope.paymentdetails.cuurentbalance=$scope.user.walletdetails.walletStatement.currentbalance;
										$scope.paymentdetails.payeewalletnumber=data.userprofile.walletdetails.walletnumber;
										}).error(function(data, status) {
										  var errormsg='Unable to specifc user profile '+status;
										  alert(errormsg);
											//$rootScope.$emit("callAddAlert", {type:'danger',msg:errormsg});
											$exceptionHandler(errormsg);
										});    
									
						      }

							  $scope.viewMemberProfileDetailsForTask = function(task) {
								  $scope.viewuser=task.taskfor;
								  	UserDetailsService.getUserProfileDetails($scope).success(function(data, status) {
										$scope.viewuserprofile.firstname = data.userprofile.firstname;
										$scope.viewuserprofile.lastname = data.userprofile.lastname;
										$scope.viewuserprofile.memberlevel = data.userprofile.networkinfo.memberlevel;
										$scope.viewuserprofile.dateofbirth = data.userprofile.dateofbirth;
										$scope.viewuserprofile.gender = data.userprofile.gender;
										$scope.viewuserprofile.addressText = data.userprofile.conatctDetails.addressText;
										$scope.viewuserprofile.district = data.userprofile.conatctDetails.district;
										$scope.viewuserprofile.email = data.userprofile.conatctDetails.email;
										$scope.viewuserprofile.mobilenumber = data.userprofile.conatctDetails.mobilenumber;
										$scope.viewuserprofile.image=data.document.documentPath;
									  }).error(function(data, status) {
										  var errormsg='Unable to specifc user profile '+status;
										  alert(errormsg);
											//$rootScope.$emit("callAddAlert", {type:'danger',msg:errormsg});
											$exceptionHandler(errormsg);
										}); 
							  }
								
								$scope.viewMemberProfileDetails = function(network) {
									$scope.viewuser=network.memberid;
									UserDetailsService.getUserProfileDetails($scope).success(function(data, status) {
										$scope.viewuserprofile.firstname = data.userprofile.firstname;
										$scope.viewuserprofile.lastname = data.userprofile.lastname;
										$scope.viewuserprofile.memberlevel = data.userprofile.networkinfo.memberlevel;
										$scope.viewuserprofile.dateofbirth = data.userprofile.dateofbirth;
										$scope.viewuserprofile.gender = data.userprofile.gender;
										$scope.viewuserprofile.addressText = data.userprofile.conatctDetails.addressText;
										$scope.viewuserprofile.district = data.userprofile.conatctDetails.district;
										$scope.viewuserprofile.email = data.userprofile.conatctDetails.email;
										$scope.viewuserprofile.mobilenumber = data.userprofile.conatctDetails.mobilenumber;
										$scope.viewuserprofile.image=data.document.documentPath;
									  }).error(function(data, status) {
										  var errormsg='Unable to specifc user profile '+status;
										  alert(errormsg);
											//$rootScope.$emit("callAddAlert", {type:'danger',msg:errormsg});
											$exceptionHandler(errormsg);
										});    
									
								}
					
					$scope.setupNetwork=function(){
						$scope.userprofile={};
						$scope.userprofile.networkinfo={};
						$scope.userprofile.networkinfo.parentmemberid=$scope.userdetails.userprofile.logindetails.username;
						$scope.userprofile.networkinfo.memberlevel='LEVEL1';
					}
					
					$scope.setupNetworkForEdit=function(){
						$scope.userprofile.conatctDetails={};
						$scope.userprofile.networkinfo={};
						$scope.userprofile.bankAccountDetails={};
						$scope.userprofile.socialdetails={};
						UserHelper.prepareUserProfileForEdit($scope);
					}
					
					$scope.addMoneyToWallet=function(){
						$scope.request.addMoney={};
						UserHelper.populateAddToWalletRequest($scope);
						AdminService.addMoneyToWallet($scope.request).success(function(data, status) {
                           if(null!=data){
							$scope.user.walletdetails.walletStatement.payout=data.payout;
						    $scope.user.walletdetails.walletStatement.encashedAmount=data.encashedAmount;
							$scope.user.walletdetails.walletStatement.currentbalance=data.currentbalance;
                            $scope.userdetails.walletStatement.payout=data.payout;
					        $scope.userdetails.walletStatement.encashedAmount=data.encashedAmount;
					        $scope.userdetails.walletStatement.currentbalance=data.currentbalance;
					        $scope.userdetails.walletStatement.walletlastupdate=data.walletlastupdate;
							alert(" Your Transaction Complet Sucessfully");
							}
						}).error(function(data, status) {
							   var errormsg='Unable to pay  : '+status;
							   alert(errormsg);
								//$rootScope.$emit("callAddAlert", {type:'danger',msg:errormsg});
								$exceptionHandler(errormsg);
							});
					}
					
					$scope.pay=function(){
						$scope.request.paymentdetails={};
						UserHelper.populatePaymentDetailsRequest($scope);
						AdminService.pay($scope.request).success(function(data, status) {
							$scope.user.walletdetails.walletStatement.payout=data.payout;
						    $scope.user.walletdetails.walletStatement.encashedAmount=data.encashedAmount;
							$scope.user.walletdetails.walletStatement.currentbalance=data.currentbalance;
							$scope.userdetails.walletStatement.payout=data.payout;
					        $scope.userdetails.walletStatement.encashedAmount=data.encashedAmount;
					        $scope.userdetails.walletStatement.currentbalance=data.currentbalance;
					        $scope.userdetails.walletStatement.walletlastupdate=data.walletlastupdate;
							alert(" Your Transaction Complet Sucessfully");
						}).error(function(data, status) {
							   var errormsg='Unable to pay  : '+status;
							   alert(errormsg);
							//	$rootScope.$emit("callAddAlert", {type:'danger',msg:errormsg});
								$exceptionHandler(errormsg);
							});
					}
					$scope.reloadWalletDetails=function(){
						AdminService.reloadWalletDetails($scope).success(function(data, status) {
						   $scope.user.walletdetails.walletStatement.payout=data.payout;
						    $scope.user.walletdetails.walletStatement.encashedAmount=data.encashedAmount;
							$scope.user.walletdetails.walletStatement.currentbalance=data.currentbalance;
							$scope.userdetails.walletStatement.payout=data.payout;
					        $scope.userdetails.walletStatement.encashedAmount=data.encashedAmount;
					        $scope.userdetails.walletStatement.currentbalance=data.currentbalance;
					        $scope.userdetails.walletStatement.walletlastupdate=data.walletlastupdate;
							}).error(function(data, status) {
							   var errormsg='Unable to pay  : '+status;
							   alert(errormsg);
							//	$rootScope.$emit("callAddAlert", {type:'danger',msg:errormsg});
								$exceptionHandler(errormsg);
							});
					}
					
					$scope.alltxnlist=[];
					$scope.sendtxnlist=[];
					$scope.addtxnlist=[];
					$scope.recivedtxnlist=[];
					$scope.encashtxnlist=[];
								$scope.getAllTxnList = function() {
									AdminService.getTxnDetails($scope).success(function(data, status) {
										$scope.alltxnlist=data.transactionDetails;
									}).error(function(data, status) {
										   var errormsg='Unable to fetch Transaction details : '+status;
										   alert(errormsg);
										//	$rootScope.$emit("callAddAlert", {type:'danger',msg:errormsg});
											$exceptionHandler(errormsg);
										});
								
								}
								$scope.getSendTxnList = function() {
									$scope.sendtxnlist=[];
									$scope.txntype='SEND';
									AdminService.getTransactionDetailsForTxnType($scope).success(function(data, status) {
										$scope.sendtxnlist=data;
									}).error(function(data, status) {
										   var errormsg='Unable to fetch Transaction details : '+status;
										   alert(errormsg);
										//	$rootScope.$emit("callAddAlert", {type:'danger',msg:errormsg});
											$exceptionHandler(errormsg);
										});
								}
								$scope.getAddTxnList = function() {
									$scope.addtxnlist=[];
									$scope.txntype='ADD';
									AdminService.getTransactionDetailsForTxnType($scope).success(function(data, status) {
										$scope.addtxnlist=data;
									}).error(function(data, status) {
										   var errormsg='Unable to fetch Transaction details : '+status;
										   alert(errormsg);
										//	$rootScope.$emit("callAddAlert", {type:'danger',msg:errormsg});
											$exceptionHandler(errormsg);
										});
								}
								$scope.getEncashTxnList = function() { 
									$scope.encashtxnlist=[];
									$scope.txntype='ENCASH';
									AdminService.getTransactionDetailsForTxnType($scope).success(function(data, status) {
										$scope.encashtxnlist=data;
									}).error(function(data, status) {
										   var errormsg='Unable to fetch Transaction details : '+status;
										   alert(errormsg);
										//	$rootScope.$emit("callAddAlert", {type:'danger',msg:errormsg});
											$exceptionHandler(errormsg);
										});

								}
								$scope.getRecivedTxnList= function() {
									$scope.recivedtxnlist=[];
									$scope.txntype='RECIVED';
									AdminService.getTransactionDetailsForTxnType($scope).success(function(data, status) {
										$scope.recivedtxnlist=data;
									}).error(function(data, status) {
										   var errormsg='Unable to fetch Transaction details : '+status;
										   alert(errormsg);
										//	$rootScope.$emit("callAddAlert", {type:'danger',msg:errormsg});
											$exceptionHandler(errormsg);
										});
								}
							$scope.reloadWalletDetails=function(){
								UserDetailsService.reloadUserWalletDetails($scope).success(function(data, status) {
									$scope.user.walletdetails.walletStatement.payout=data.payout;
								    $scope.user.walletdetails.walletStatement.encashedAmount=data.encashedAmount;
									$scope.user.walletdetails.walletStatement.currentbalance=data.currentbalance;
									$scope.userdetails.walletStatement.payout=data.payout;
							        $scope.userdetails.walletStatement.encashedAmount=data.encashedAmount;
							        $scope.userdetails.walletStatement.currentbalance=data.currentbalance;
							        $scope.userdetails.walletStatement.walletlastupdate=data.walletlastupdate;
								}).error(function(data, status) {
									   var errormsg='Unable to refresh your wallet '+status;
									   alert(errormsg);
										//$rootScope.$emit("callAddAlert", {type:'danger',msg:errormsg});
										$exceptionHandler(errormsg);
									});
								}
					$scope.poulateAddMoneyToWallet=function(){
						$scope.addMoney={};
						$scope.addMoney.walletnumber=$scope.userdetails.userprofile.walletdetails.walletnumber;
					}
					$scope.poulatePaymentDetails=function(){
						$scope.paymentdetails={};
						$scope.paymentdetails.walletnumber=$scope.userdetails.userprofile.walletdetails.walletnumber;
						$scope.paymentdetails.cuurentbalance=$scope.user.walletdetails.walletStatement.currentbalance;
					}
					
					$scope.poulateEncashDetails=function(){
						$scope.encashdetails={};
						$scope.encashdetails.walletnumber=$scope.userdetails.userprofile.walletdetails.walletnumber;
						$scope.encashdetails.encashamount=$scope.userdetails.walletStatement.maxencashable;
					}
					$scope.createEncashRequest=function(){
						$scope.request.encashdetails={};
						$scope.request.encashdetails.walletnumber=$scope.encashdetails.walletnumber;
						$scope.request.encashdetails.encashamount=$scope.encashdetails.encashamount;
						UserDetailsService.createEncashRequest($scope.request).success(function(data, status) {
                      $scope.taskPoller.push("createEncashRequest");
					  alert(" Your have create EncashRequest Sucessfully");
					}).error(function(data, status) {
						   var errormsg='Unable to Create Encash Request : '+status;
						   alert(errormsg);
							//$rootScope.$emit("callAddAlert", {type:'danger',msg:errormsg});
							$exceptionHandler(errormsg);
						});
					}
					
					$scope.populateEncashDetailsForApprover=function(){
						UserHelper.populateEncashRequestDetails($scope);
					}
					
					
					$scope.addUserDetails=function(){
						UserHelper.prepareUserProfileRequest($scope);
						UserDetailsService.saveUserProfileDetails( $scope.request).success(function(data, status) {
                       $scope.taskPoller.push("userSave");
					   alert("User Added Sucessfully");
							if(!data.mobilenoVerified){
								$scope.otp.mobilenumber=data.userprofile.conatctDetails.mobilenumber;
								angular.element('#otppopup').modal('show');
						     	}
							if(!data.emailVerified){
								$scope.otp.email=data.userprofile.conatctDetails.email;
								angular.element('#otppopup').modal('show');
						     	}
								}).error(function(data, status) {
								   var errormsg='Unable to save User Profile  details : '+status;
								   alert(errormsg);
									//$rootScope.$emit("callAddAlert", {type:'danger',msg:errormsg});
									$exceptionHandler(errormsg);
								});
					}
					
					$scope.updateAdminTask=function(task){
				     $scope.request.task=task;
                     $scope.taskPoller.push("updateAdminTask");
					  if(task.tasktype=='ENCASH_REQUEST' && task.taskstatus=='Approved'){
						  angular.element('#notificationdetails').modal('hide');
						  angular.element('#encashdetailmodal').modal('show');
						  $scope.encashdetails.encashamount=task.encashvo.encashamount;
						  $scope.encashdetails.walletnumber=task.encashvo.walletnumber;
						  $scope.encashdetails.username=task.encashvo.username;
						  $scope.encashdetails.approver=$localStorage.profile.userprofile.logindetails.username;
					 }else{
				       angular.element('#encashdetailmodal').modal('hide');
				      AdminService.updateAdminTask($scope.request).success(function(data, status) {
					 angular.element('#notificationdetails').modal('hide');
					 }).error(function(data, status) {
								   var errormsg='Unable to Update Task Details: '+status;
									$rootScope.$emit("callAddAlert", {type:'danger',msg:errormsg});
									$exceptionHandler(errormsg);
								});
					}
					 }
				    $scope.saveEncashDetails=function(){
					//UserHelper.populateTaskWithEncashRequestDetails($scope.request.task);
					$scope.request.task.encashvo.encashtype=$scope.encashdetails.encashmethod;
                    $scope.request.task.encashvo.refrencenumber=$scope.encashdetails.refrence;					
					AdminService.updateAdminTask($scope.request).success(function(data, status) {
					 $scope.taskPoller.push("saveEncashDetails");
					 angular.element('#encashdetailmodal').modal('hide');
					 }).error(function(data, status) {
								   var errormsg='Unable to Update Task Details: '+status;
								   alert(errormsg);
									//$rootScope.$emit("callAddAlert", {type:'danger',msg:errormsg});
									$exceptionHandler(errormsg);
								});
					}
					$scope.txnRefrenceNumber='null';
						$scope.populateTransactionDetailsForRollBack = function(txnsend) {
							$scope.txnRefrenceNumber=txnsend.refrenceno;
							AdminService.getTxnDetailsForRefrenceNumber($scope).success(function(data, status) { 
							$scope.txnrollback.cfirstname=data.creditorFirstName;
							$scope.txnrollback.clastname=data.crediotrLastName;
							$scope.txnrollback.creditorgpmid=data.creditorGpmIdNo;
							$scope.txnrollback.payeefirstname=data.payeeFirstName;
							$scope.txnrollback.payeelastname=data.payeeLastName;
							$scope.txnrollback.payeegpmid=data.payeeGpmIdNo;
							$scope.txnrollback.payeecurrentbalance=data.payeeCurrentBalance;
							$scope.txnrollback.rollbackAmount=data.rollbackAmount;
							$scope.txnrollback.txnrefrenceno=data.txnRefrenceNumber;
							$scope.txnrollback.txndescription=data.txnDescription;
							$scope.txnrollback.reasone=null;
							}).error(function(data, status) {
								   var errormsg='Unable to fetch Details: '+status;
								   alert(errormsg);
									//$rootScope.$emit("callAddAlert", {type:'danger',msg:errormsg});
									$exceptionHandler(errormsg);
								});
						}	
						$scope.txnRollbackConfirm = function(txnrollback) {
							$scope.request.txnRefrenceNumber=txnrollback.txnrefrenceno;
							$scope.request.reasone=$scope.txnrollback.reasone;
							AdminService.rollbackTransaction($scope.request).success(function(data, status) {
								 var errormsg='Sucessfully rollback your transaction: '+status;
								 alert(errormsg);
							}).error(function(data, status) {
								   var errormsg='Unable to rollback your transaction: '+status;
								   alert(errormsg);
									//$rootScope.$emit("callAddAlert", {type:'danger',msg:errormsg});
									$exceptionHandler(errormsg);
								});
						}
						
						$scope.reloadNetworkCount=function() {	
							UserDetailsService.reloadNetworkCount($scope).success(function(data, status) {
								$scope.networkcountlist=data.networkcountlist;
							}).error(function(data, status) {
								   var errormsg='Unable to refresh your Network performance : '+status;
								   alert(errormsg);
									//$rootScope.$emit("callAddAlert", {type:'danger',msg:errormsg});
									$exceptionHandler(errormsg);
								});
						}
			$scope.uploadFile = function(files) {
               $scope.request.fd = new FormData();
               var fileSize = files[0].size;
               if(fileSize>$scope.maxFileUploadSize){
                   alert('file size is more than  2 MB');
                   return false;
               }
              //Take the first selected file
              $scope.request.fd.append("file", files[0]);
			  UserDetailsService.uploadImages($scope.request).success(function(data, status) {
				  $scope.documentid=data.documentID;
			  }).error(function(data, status) {
								   var errormsg='Unable to upload Image';
								   alert(errormsg);
									//$rootScope.$emit("callAddAlert", {type:'danger',msg:errormsg});
									$exceptionHandler(errormsg);
								});
					}
	
					
			 } ]);
}());