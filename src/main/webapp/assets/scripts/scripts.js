"use strict";
angular.module("mlmlogicq",["ui.router","snap","ngAnimate"])
.config(["$stateProvider","$urlRouterProvider",
function(r,t)
{t.when("/dashboard","/dashboard/overview"),
t.otherwise("/login"),
r.state("base",{"abstract":!0,url:"",templateUrl:"assets/views/base.html"})
.state("login",{url:"/login",parent:"base",templateUrl:"views/login.html",controller:"LoginController"})
.state("dashboard",{url:"/dashboard",parent:"base",templateUrl:"views/dashboard.html",controller:"DashboardCtrl"})
.state("overview",{url:"/overview",parent:"dashboard",templateUrl:"assets/views/dashboard/overview.html"})
.state("reports",{url:"/reports",parent:"dashboard",templateUrl:"views/dashboard/reports.html"})}]),
angular.module("mlmlogicq").controller("LoginController",["$scope","$location",
function(r,t){r.submit=function(){return t.path("/dashboard"),!1}}]),
angular.module("mlmlogicq").controller("DashboardCtrl",["$scope","$state",function(r,t){r.$state=t}]);