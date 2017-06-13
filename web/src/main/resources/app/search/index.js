define(function(require) {
  var pubsub = require('pubsub-js');

  var events = require('./events');
  var filters = require('./filters');
  var results = require('./results');

  require('bootstrap-loader');

  function setFacet(event) {
    event.preventDefault();
    var facet = $(this).data('facet');
    $('.facet.' + facet).toggleClass('selected');

    var facets = [];
    $('.facet.selected').each(function(i, el) {
      facets.push($(el).data('facet'));
    });

    if(_.isEmpty(facets)) {
      filters.removeParam('f');
    } else {
      filters.setParam('f', _.uniq(facets).join(','));
    }
  }

  function setSort(event) {
    event.preventDefault();
    filters.setParam('sort', $(this).attr("id"));
  }

  var initialize = function() {
    filters.initialize();

    // populate results based on existing query string
    if(window.location.search.length > 1) {
      results.initialize();
      filters.deserialize(window.location.search);
    }

    $('.c-search #search').on('input', function(e) {
      results.initialize();
    });

    $('.c-search')
      .on('click', '.facet', setFacet)
      .on('click', '.c-results-outer .sort_options', setSort)

    $('.s-page').removeClass('invisible');
  };

  // event listeners for updating search results based on filters
  pubsub.subscribe('search.updated', function() {
    console.log(filters.serialize());
    results.update(filters.serialize());
    history.pushState(null, null, '?' + filters.serialize());
  });

  $(document).ready(function() {
    initialize();
  });
});
