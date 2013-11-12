angular.module('input.utils').service(

  // service name
  'InputFocusService',

// dependencies injection
['$timeout',

// @being: Service Class Definition
(function() {

  // private
  var $scope, 
      $timeout, 
      focusFieldNameArray
      lastFocusInput = null,
      toSelect = null;

  function resetFocusFields() {
    for(var i=0, len=focusFieldNameArray.length; i<len; i++) {
      $scope[focusFieldNameArray[i]] = false;
    }
  };

  function selectFocusField() {
    if(lastFocusInput !== toSelect) {
      resetFocusFields();

      if(toSelect) {
        $scope[toSelect] = true;
        lastFocusInput = toSelect;
      } 
    }
  }

  //--- === ---

  // class constructr
  var ClassDef = function(timeout) {
    $timeout = timeout;
  };

  // public

  ClassDef.prototype.config = function(scope, _focusFieldNameArray) {
    $scope = scope;
    focusFieldNameArray = _focusFieldNameArray; 
    resetFocusFields();
  };

  ClassDef.prototype.setFocus = function(focusFieldName, wait) {
    wait = wait || 100; // ms
    toSelect = focusFieldName;
    $timeout(selectFocusField, wait);
  };

  ClassDef.prototype.focusReset = function() {
    lastFocusInput = null;
    toSelect = null;
    resetFocusFields();
  };

  //---
  return ClassDef;

})()
// @end: Service Class Definition

]);