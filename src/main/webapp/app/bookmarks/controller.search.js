angular.module('app').controller(

  // controller name
  'BookmarksSearchCtrl',

  // dependencies injection
  ['$scope', 'BookmarksSearchResource', 'PaginationService',

// controller definition
function ($scope, resource, pagination) {

  //---

  var config = {
    pageMinSize: 2,
    pageMaxSize: 50,
    showFilterBtnMinlength: 5
  };

  //---

  function loadData(page) {
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

        $scope.showFilterBtn = checkShowfilterBtn();
      }
    );
  };

  //---

  $scope.showOptions = false;

  $scope.optionsBtnLabel = 'Show Options';

  $scope.showOptionsBtnClick = function() {
    $scope.showOptions = !$scope.showOptions;
    $scope.optionsBtnLabel = ($scope.showOptions ? 'Hide' : 'Show') + ' Option';
    //if(!$scope.showFilter) $scope.clearFilter();

    // TODO: review
    if(!$scope.showOptions) {
      if($scope.showFilter) $scope.showFilterBtnClick();
    }
  }

  //---

  $scope.filter = { search: '' };
  $scope.showFilter = false;

  function checkShowfilterBtn() {
    return ($scope.pageSize < config.showFilterBtnMinlength) ? false : true;
  }

  $scope.showFilterBtn = checkShowfilterBtn();

  $scope.filterBtnLabel = 'Show filter';

  $scope.showFilterBtnClick = function() {
    $scope.showFilter = !$scope.showFilter;
    $scope.filterBtnLabel = ($scope.showFilter ? 'Hide' : 'Show') + ' filter';
    if(!$scope.showFilter) $scope.clearFilter();
  }

  $scope.clearFilter = function() {
    $scope.filter = { search: '' };
  }

  //---
  
  $scope.setPage = function() {
    if((this.n+1) != $scope.bookmarks.page) {
      pagination.setNextPage(this.n+1);
      loadData(pagination.getNextPage());
    }
  };

  //---

  $scope.pageSize = pagination.getPageSize();
  $scope.pageMinSize = config.pageMinSize;
  $scope.pageMaxSize = config.pageMaxSize;

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

  //---

  $scope.doSearch = function() {
    loadData(pagination.getNextPage());
  };

}]);