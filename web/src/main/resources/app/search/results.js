define(function(require) {

  var $ = require('jquery');
  var _ = require('lodash');
  var Handlebars = require('handlebars');
  var pagination = require('pagination');
  var filters = require('./filters');
  var events = require('./events');

  var resultsTmpl = require('./tmpl/results.hbs');

  $(document).on('keydown', function (event) {
    if($(event.target).is('input')) return;

    var pag = $('.c-pagination');
    if(event.which === events.LEFT_ARROW) {
      pag.pagination('prevPage');
    } else if (event.which === events.RIGHT_ARROW) {
      pag.pagination('nextPage');
    }
  });

  var prepare = function() {
    if(_.isEmpty($('.c-results-outer'))) {
      $('.c-footer').show();
      $(".c-results-outer").on("click", ".js-show-list", function() {
        $(".c-results-outer").addClass("grid--rows").removeClass("grid--columns");
      });

      $(".c-results-outer").on("click", ".js-show-grid", function() {
        $(".c-results-outer").addClass("grid--columns").removeClass("grid--rows");
      });
    }
  }

  var update = function(state) {
    prepare();

    $.getJSON("/api/1/search?" + state, function(json) {
      if($('#c-search-results').length) {
        $('#c-search-results').replaceWith(resultsTmpl(json));
      } else {
        $('.jumbotron').after(resultsTmpl(json));
      }
      paginate(json);
      filters.refresh();
    });
  };

  var updateItems = function(state) {
    $.getJSON("/api/1/search?" + state, function(json) {
      prepare();

      filters.refresh();
    });
  }

  var initialize = function(initialToken) {
    if($(window).width() < 992) {
      $(document).scrollTop( $("#search_box").offset().top);
    }
  }

  function paginate(results) {
    $('.c-pagination').pagination({
      items: results.totalResults,
      itemsOnPage: results.perPage,
      pages: results.totalPages,
      listStyle: 'pagination',
      hrefTextPrefix: '',
      currentPage: Number(filters.getParam('page'))+1,
      onPageClick: function(page, e) {
        filters.setParam('page', page-1);
        if(e) e.preventDefault();
      }
    });
  }

  return {
    initialize: initialize,
    update : update,
    updateItems: updateItems
  };
});
