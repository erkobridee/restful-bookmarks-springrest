require({

  // define js scripts dependencies
  shim: {

    'shared/components/pagination/module': {
      deps: ['angular']
    },

    'shared/components/pagination/filter.pages.range': {
      deps: ['shared/components/pagination/module']
    },

    'shared/components/pagination/factory': {
      deps: ['shared/components/pagination/module']
    },

    'shared/components/pagination/start': {
      deps: [
        'shared/components/pagination/filter.pages.range',
        'shared/components/pagination/factory'
      ]
    }

  }

},

['require'], function(require) {

  console.log('shared/components/pagination require.js config');

  require(['shared/components/pagination/start']);

});