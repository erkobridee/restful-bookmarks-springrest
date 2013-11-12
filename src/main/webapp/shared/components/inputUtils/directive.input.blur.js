angular.module('inputUtils').directive(
  
  // component name
  'ngExtBlur', 

// component definition
function(){

  return {
    restrict: 'A',
    link: function (scope, element, attr) {
      element.bind('blur', function () {
        //apply scope (attributes)
        scope.$apply(attr.ngExtBlur);
        //return scope value for focusing to false
        scope.$eval(attr.ngExtFocus + '=false');
      });
    }
  };

});

/*
  based on:
  http://blog.ejci.net/2013/08/06/dealing-with-focus-and-blur-in-angularjs-directives/
*/