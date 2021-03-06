define(function(require) {
  var pubsub = require('pubsub-js');
  require('jquery-serializejson');

  var events = require('./events');
  var filters = require('./filters');
  var results = require('./results');

  var initialize = function() {
    filters.initialize();
    results.initialize();

    // populate results based on existing query string
    if(window.location.pathname === '/' && window.location.search.length > 1) {
      filters.deserialize(window.location.search);
    }else{
      $('body').removeClass('invisible')
    }

    $('.c-search')
      .on('submit', '#advanced-search form', function(e) {
        e.preventDefault();
        var params = _.pickBy($(this).serializeJSON({
          skipFalsyValuesForTypes:["string"]
        }));

        if($('#publishing-author-only').is(':checked')) {
          params['publishing author'] = params['name author'];
          delete params['name author'];
        }

        filters.set(params);
        $('.c-search .input-group-btn').removeClass('open');
      })
      .on('click', '#search-button', function(e) {
        var input = $('.token-input');
        filters.add(input.val());
        input.val('');
      })
      .on('click', '.clear', function(e) {
        $($(this).parents()[0]).find('input').val('');
        filters.set({});
      })
      .on('click', '.panel-body', function(e) {
        e.stopPropagation();
      })
      .on('shown.bs.dropdown', function() {
        $('#c-page-body').addClass('obscured');
      })
      .on('hidden.bs.dropdown', function() {
        $('#c-page-body').removeClass('obscured');
      })
  };

  // event listeners for updating search results based on filters
  pubsub.subscribe('search.updated', function() {
    if($('.jumbotron')) {
      $('.container').addClass('p-search');
      $('.jumbotron').removeClass('vertically-centred');
      $('.jumbotron').removeClass('jumbotron');
    }

    results.update(filters.serialize());
  });

  $(document).ready(function() {
    initialize();
  });
});
