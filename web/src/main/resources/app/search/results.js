define(function(require) {

  var Handlebars = require('handlebars');
  var pagination = require('pagination');
  var filters = require('./filters');
  var events = require('./events');
  var header = require('./results-header');

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

  var update = function(state) {
    header.showSelectedSort(filters.getParam('sort') || 'a-z');

    $.getJSON(API_BASE + 'search?callback=?&' + state, function(json) {
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
    $.getJSON(API_BASE + 'search?callback=?&' + state, function(json) {
      filters.refresh();
    });
  }

  var initialize = function(initialToken) {
    if($(window).width() < 992) {
      $(document).scrollTop( $("#search_box").offset().top);
    }

    $('.container')
      .on('click', '#c-search-results .sort-by a', setSort)
  }

  function setSort(event) {
    event.preventDefault();
    filters.setParam('sort', $(this).attr("id"));
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
