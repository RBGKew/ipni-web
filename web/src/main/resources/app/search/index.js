define(function(require) {
  var pubsub = require('pubsub-js');
  require('bootstrap-datepicker');
  require('jquery-serializejson');

  var events = require('./events');
  var filters = require('./filters');
  var results = require('./results');

  var initialize = function() {
    filters.initialize();
    results.initialize();

    // populate results based on existing query string
    if(window.location.search.length > 1) {
      filters.deserialize(window.location.search);
    }

    $('.container').on('click', '#advanced-search button', function(e) {
      e.preventDefault();
      filters.set(_.pickBy($('#advanced-search').serializeJSON()));
    })
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
