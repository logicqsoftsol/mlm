/**
 * 
 */

(function () {
    'use strict';
    
 angular.module('mlmlogicq').config(['$httpProvider','$provide', function ($httpProvider,$provide) {  
    $httpProvider.interceptors.push('APIInterceptor');
    $provide.decorator("$exceptionHandler", function ($delegate, $injector) {
        return function (exception, cause) {
			   var rootScope = $injector.get("$rootScope");
			   $('#preloader').hide();
		       $('#displaydata').show();             
		       $delegate(exception, cause);
			//Not requird as added error message
		     //  alert(cause+'\n'+exception);
        };
    });
	
        }]);
	
 
  angular.module('mlmlogicq').service('APIInterceptor', ['$sessionStorage','AppConstants', function ($sessionStorage,AppConstants) {
	return {
     'request': function(config) {
    	  if ($sessionStorage.authToken) {
              config.headers['AUTH-TOKEN'] = $sessionStorage.authToken;
          }
    	  var hideUrl = AppConstants.pollTaskListURL;
    	  var hide = (config.url.indexOf(hideUrl));
    	  if(hide == -1){
    		  $('#preloader').show();
    	  }
		  $('#displaydata').hide();
         return config;
      },

      'response': function(response) {
         $('#preloader').hide();
		  $('#displaydata').show();
         return response;
      }
    };   

        }]);
  
  angular.module('mlmlogicq').directive('loading', function () {
      return {
        restrict: 'E',
        replace:true,
        template: '<div class="loading"><i class="fa fa-spinner fa-pulse fa-4x fa-fw"></i><span>Please Wait...</span></div>',
        link: function (scope, element, attr) {
              scope.$watch('loading', function (val) {
                  if (val)
                      $(element).show();
                  else
                      $(element).hide();
              });
        }
      }
  });
		
angular.module('mlmlogicq').directive('showErrors', function ($timeout, showErrorsConfig) {
      var getShowSuccess, linkFn;
      getShowSuccess = function (options) {
        var showSuccess;
        showSuccess = showErrorsConfig.showSuccess;
        if (options && options.showSuccess != null) {
          showSuccess = options.showSuccess;
        }
        return showSuccess;
      };
      linkFn = function (scope, el, attrs, formCtrl) {
        var blurred, inputEl, inputName, inputNgEl, options, showSuccess, toggleClasses;
        blurred = false;
        options = scope.$eval(attrs.showErrors);
        showSuccess = getShowSuccess(options);
        inputEl = el[0].querySelector('[name]');
        inputNgEl = angular.element(inputEl);
        inputName = inputNgEl.attr('name');
        if (!inputName) {
          throw 'show-errors element has no child input elements with a \'name\' attribute';
        }
        inputNgEl.bind('blur', function () {
          blurred = true;
          return toggleClasses(formCtrl[inputName].$invalid);
        });
        scope.$watch(function () {
          return formCtrl[inputName] && formCtrl[inputName].$invalid;
        }, function (invalid) {
          if (!blurred) {
            return;
          }
          return toggleClasses(invalid);
        });
        scope.$on('show-errors-check-validity', function () {
          return toggleClasses(formCtrl[inputName].$invalid);
        });
        scope.$on('show-errors-reset', function () {
          return $timeout(function () {
            el.removeClass('has-error');
            el.removeClass('has-success');
            return blurred = false;
          }, 0, false);
        });
        return toggleClasses = function (invalid) {
          el.toggleClass('has-error', invalid);
          if (showSuccess) {
            return el.toggleClass('has-success', !invalid);
          }
        };
      };
      return {
        restrict: 'A',
        require: '^form',
        compile: function (elem, attrs) {
          if (!elem.hasClass('form-group')) {
            throw 'show-errors element does not have the \'form-group\' class';
          }
          return linkFn;
        }
      };
    }
  );
  
  angular.module('mlmlogicq').provider('showErrorsConfig', function () {
    var _showSuccess;
    _showSuccess = false;
    this.showSuccess = function (showSuccess) {
      return _showSuccess = showSuccess;
    };
    this.$get = function () {
      return { showSuccess: _showSuccess };
    };
  });	

 
 
}());