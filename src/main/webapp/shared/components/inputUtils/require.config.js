require({

  // define js scripts dependencies
  shim: {

    'shared/components/inputUtils/module': {
      deps: ['angular']
    },

    'shared/components/inputUtils/directive.input.blur': {
      deps: ['shared/components/inputUtils/module']
    },

    'shared/components/inputUtils/directive.input.focus': {
      deps: ['shared/components/inputUtils/module']
    },

    'shared/components/inputUtils/start': {
      deps: [
        'shared/components/inputUtils/directive.input.blur',
        'shared/components/inputUtils/directive.input.focus'
      ]
    }

  }

},

['require'], function(require) {

  console.log('shared/components/loadingBar require.js config');

  require(['shared/components/inputUtils/start']);

});