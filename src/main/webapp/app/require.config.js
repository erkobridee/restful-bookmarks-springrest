require({

  // define js scripts dependencies
  shim: {

    //--- 

    'app/main/module': {
      deps: [
        'angular', 
        'angular_resource',
        'shared/components/progressbar/loading/module',
        'shared/components/input/utils/module'
      ]
    },

    //--- @begin app files

    'app/main/filter.range': {
      deps: ['app/main/module']
    },

    'app/main/factory.menu.config': {
      deps: ['app/main/module']
    },

    'app/main/service.pagination': {
      deps: ['app/main/module']
    },

    'app/main/controller': {
      deps: ['app/main/factory.menu.config']
    },

      //--- @begin: bookmarks
      'app/bookmarks/resource': {
        deps: ['app/main/module']
      },      
      'app/bookmarks/resource.search': {
        deps: ['app/main/module']
      },

      'app/bookmarks/controller.search': {
        deps: [
          'app/bookmarks/resource.search',
          'shared/components/input/utils/service.input.focus'
        ]
      },
      'app/bookmarks/controller.list': {
        deps: [
          'app/bookmarks/resource',          
          'app/main/service.pagination', // TODO: review
          'shared/components/input/utils/service.input.focus'
        ]
      },
      'app/bookmarks/controller.new': {
        deps: ['app/bookmarks/resource']
      },
      'app/bookmarks/controller.edit': {
        deps: ['app/bookmarks/resource']
      },      
      //--- @edit: bookmarks


    //--- @end app files

    'app/main/routes': {
      deps: [ // TODO: review and add app controllers ref's
        'app/bookmarks/controller.search',
        'app/bookmarks/controller.list',
        'app/bookmarks/controller.new',
        'app/bookmarks/controller.edit'
      ]
    },

    'app/main/start': {
      deps: [ // TODO: review and add app routes and view componentes ref's
        'app/main/filter.range',
        'app/main/controller',
        'app/main/routes'     
      ]
    }

  }

},

['require'], function(require) {

  console.log('app require.js config');

  require(['app/main/start']);

});