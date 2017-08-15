define(function(require) {

  var Handlebars = require('handlebars');
  var pagination = require('pagination');
  var filters = require('./filters');
  var events = require('./events');

  var resultsTmpl = require('./tmpl/results.hbs');

  var update = function(state) {
    $.getJSON(API_BASE + 'search?callback=?&' + state, function(results) {
      load(results);
      history.pushState({
        class: 'c-search',
        data: results
      }, null, '/?' + filters.serialize())
    });
  };

  var load = function(data) {
      data['sort'] = filters.getParam('sort');
      data['f'] = filters.getParam('f');

      if($('#c-page-body').length) {
        $('#c-page-body').replaceWith(resultsTmpl(data));
      } else {
        $('.content').after(resultsTmpl(data));
      }

      paginate(data);
      filters.refresh();
  }

  var initialize = function(initialToken) {
    $(document).on('keydown', function (event) {
      if($(event.target).is('input')) return;

      var pag = $('.c-pagination');
      if(event.which === events.LEFT_ARROW) {
        pag.pagination('prevPage');
      } else if (event.which === events.RIGHT_ARROW) {
        pag.pagination('nextPage');
      }
    });

    if($(window).width() < 992) {
      $(document).scrollTop( $("#search_box").offset().top);
    }

    $('body')
      .on('click', '.sort-by a', setSort)
      .on('click', '.filter-by a', toggleFilter);
  }

  function toggleFilter(event) {
    event.preventDefault();
    var filter = $(this).attr("id");
    if(filters.getParam('f') === filter) {
      filters.removeParam('f');
    } else {
      filters.setParam('f', filter);
    }
  }

  function setSort(event) {
    event.preventDefault();
    filters.setParam('sort', $(this).attr("id"));
  }

  function paginate(results) {
    if(results.totalPages > 1) {
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
  }

  return {
    initialize: initialize,
    load: load,
    update: update,
  };
});
