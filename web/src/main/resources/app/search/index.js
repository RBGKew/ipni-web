define(function(require) {
  var pubsub = require('pubsub-js');
  require('bootstrap-datepicker');
  require('jquery-serializejson');

  var events = require('./events');
  var filters = require('./filters');
  var name = require('./name');
  var results = require('./results');

  var initialize = function() {
    filters.initialize();

    // populate results based on existing query string
    if(window.location.search.length > 1) {
      results.initialize();
      filters.deserialize(window.location.search);
    }

    $('.container').on('click', '#advanced-search button', function(e) {
      e.preventDefault();
      filters.set(_.pickBy($('#advanced-search').serializeJSON()));
    })

    window.onpopstate = function(event) {
      switch(event.state.class) {
        case 'c-search':
          results.load(event.state.data);
          $('.container').removeClass('c-name');
          break;
        case 'c-name':
          name.load(event.state.data);
          $('.container').removeClass('c-search');
          break;
      }
    };
  };

  // event listeners for updating search results based on filters
  pubsub.subscribe('search.updated', function() {
    if($('.jumbotron')) {
      $('.jumbotron').addClass('p-search');
      $('.jumbotron').removeClass('vertically-centred');
      $('.jumbotron').removeClass('jumbotron');
    }

    console.log(filters.serialize());
    results.update(filters.serialize());
  });

  $(document).ready(function() {
    initialize();
  });
});
