angular.module('app').controller(

  // controller name
  'BookmarksSearchCtrl',

  // dependencies injection
  ['$scope', 'BookmarksSearchResource', 'PaginationFactory', 'InputFocusFactory',

// controller definition
function ($scope, resource, pagination, input) {

  var ctrlName = 'BookmarksSearchCtrl';
  input = input.get(ctrlName);
  pagination = pagination.get(ctrlName);

  /*
  console.log(ctrlName);
  console.log(input);
  console.log(pagination);
  */

  //---
  input.config(
    $scope,
    [
      'focusSearchInput',
      'focusPageSizeInput',
      'focusFilterSearchInput'
    ]);
  
  input.setFocus('focusSearchInput', 200);
  //---

  var config = {
    pageMinSize: 2,
    pageMaxSize: 50,
    showFilterBtnMinlength: 5
  };

  //---

  function stringEmpty(str) {
    var pattern = /^\s*$/;
    return (str == null || pattern.test(str));
  };

  //---

  function updateInterface() {
    $scope.clearFilter();

    // check if filter is visible
    if($scope.showOptions) $scope.showOptionsBtnClick();
    if($scope.showFilter || $scope.showFilterBtnActive) $scope.showFilterBtnClick();
    
    // check if filter is needed
    $scope.showFilterBtn = checkShowfilterBtn();

    $scope.showPagination = true;
    $scope.showFilter = false;
    $scope.showFilterBtnActive = false;

    input.setFocus('focusSearchInput');
  };

  //---

  function loadData(page) {
    if(!stringEmpty($scope.searchName)) {
      resource.get(
        {
          name: $scope.searchName,
          page: page,
          size: pagination.getPageSize()
        },
        function(result) {
          $scope.bookmarks = result;

          pagination.updateMetainf(
            result.count,
            result.data.length,
            result.page,
            result.pages
          );

          updateInterface();
        }
      );
    }
  };

  //--- 
  // @begin: options

  $scope.showOptions = false;

  $scope.optionsBtnLabel = 'Show Options';

  $scope.showOptionsBtnClick = function() {
    $scope.showOptions = !$scope.showOptions;
    $scope.optionsBtnLabel = ($scope.showOptions ? 'Hide' : 'Show') + ' Option';

    if($scope.showOptions) {      
      $scope.showFilter = $scope.showFilterBtnActive;

      if($scope.showFilter) input.setFocus('focusFilterSearchInput');
      else input.setFocus('focusPageSizeInput');
    } else {      
      if($scope.showFilter && stringEmpty($scope.filter.search)) $scope.showFilterBtnClick();
      $scope.showFilter = false;
      
      input.setFocus('focusSearchInput');
    }
  }

  // @end: options
  //---
  // @begin: filter

  $scope.filter = { search: '' };
  $scope.showFilter = false;

  function checkShowfilterBtn() {
    return (
      (pagination.getPageSize() >= config.showFilterBtnMinlength) && 
      (pagination.metainf.lastPageSize >= config.showFilterBtnMinlength)
    );
  }

  $scope.showFilterBtn = false;
  $scope.showFilterBtnActive = false;

  $scope.filterBtnLabel = 'Show filter';

  $scope.showFilterBtnClick = function() {
    $scope.showFilter = $scope.showFilterBtnActive = !$scope.showFilter;
    $scope.filterBtnLabel = ($scope.showFilter ? 'Hide' : 'Show') + ' filter';
    if(!$scope.showFilter) $scope.clearFilter();
    $scope.showPagination = !$scope.showFilter;

    // change input field focus
    if($scope.showFilter) input.setFocus('focusFilterSearchInput');
    else input.setFocus('focusPageSizeInput');
  }

  $scope.clearFilter = function() {
    $scope.filter = { search: '' };
  }

  // @end: filter
  //---
  // @begin: pagination
  
  $scope.showPagination = true;
  $scope.pageSize = pagination.getPageSize();
  $scope.pageMinSize = config.pageMinSize;
  $scope.pageMaxSize = config.pageMaxSize;

  $scope.setPage = function() {
    if((this.n+1) != $scope.bookmarks.page) {
      pagination.setNextPage(this.n+1);
      loadData(pagination.getNextPage());
    }
  };

  $scope.updatePageSizeInvalid = function(pageSize) {
    var flag = false;

    flag = (
      pageSize === undefined || 
      pageSize === null || 
      pageSize === pagination.getPageSize() ||
      pageSize < $scope.pageMinSize ||
      pageSize > $scope.pageMaxSize
    );

    return flag;
  };

  $scope.updatePageSize = function() {
    // check if filter is visible
    if($scope.showFilter) $scope.showFilterBtnClick();

    pagination.resetPageSize($scope.pageSize);

    loadData(pagination.getNextPage());
  }

  $scope.updatePageSizeFormSubmit = function() { 
    if(!$scope.updatePageSizeInvalid($scope.pageSize))
      $scope.updatePageSize();
  }

  // @end: pagination
  //---

  $scope.doSearch = function() {
    pagination.resetPageSize($scope.pageSize);
    
    loadData(pagination.getNextPage());
  };

}]);