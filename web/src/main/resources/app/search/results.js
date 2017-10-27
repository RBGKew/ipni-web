define(function(require) {

  var Handlebars = require('handlebars');
  var pagination = require('pagination');
  var filters = require('./filters');
  var events = require('./events');

  var resultsTmpl = require('./tmpl/results.hbs');
  var resultsListTmpl = require('./tmpl/results-list.hbs');
  var update = function(state) {
    $('html, body').animate({scrollTop: '0px'}, 100);
    if(!$('#c-page-body').length) {
      $('.content').after(resultsTmpl());
      $('.no-results').addClass('hidden')
    }
    $('body').removeClass('invisible')
    var indicateProgress = _.debounce(function() {
      $('.totalResults').addClass('hidden');
      $('.loadingResults').removeClass('hidden');
      $('.results').addClass('obscured')
    }, 100);
    indicateProgress();

    $.getJSON(API_BASE + 'search?' + state, function(results) {
      indicateProgress.cancel();

      load(results);
      $('results').removeClass('obscured')
      $('.totalResults').removeClass('hidden');
      $('.loadingResults').addClass('hidden');
      history.pushState({
        class: 'p-search',
        data: results
      }, null, '/?' + filters.serialize())
    });
  };

  var load = function(data) {
    data['sort'] = filters.getParam('sort');
    data['f'] = filters.getParam('f');
    data['expanded'] = filters.getParam('expanded');
    if($('#c-page-body').length) {
      $('#c-page-body').replaceWith(resultsTmpl(data));
    } else {
      $('.content').after(resultsTmpl(data));
    }

    if(data.totalResults > 10000) {
      $('.download').popover({
        content: 'Downloads are capped at a total of 10,000 records. ' +
          ' If you need a more comprehensive dataset, please contact us at ' +
          'ipnifeedback@kew.org',
          placement: 'bottom',
          trigger: 'hover',
      });
    }

    paginate(data);
    filters.refresh();
  }

  var downloadResults = function(e) {
    window.location = '/api/1/download?' + filters.serialize();
  }

  var loadResultBar = function() {
    $('.content').after(resultsTmpl());
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

    $('body')
      .on('click', '.sort-by a', setSort)
      .on('click', '.filter-by .btn', toggleFilter)
      .on('change', '.c-per-page', setLimit)
      .on('click', '.show-detailed-record', showDetailedRecords)
      .on('click', '.download', downloadResults);
  }

  function toggleFilter(event) {
    if($(this).hasClass('disabled')) return;
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

  function setLimit(limit) {
    event.preventDefault();
    filters.setParam('perPage', $(this).val());
  }

  function showDetailedRecords(event) {
    event.preventDefault();
    if(filters.getParam('expanded')) {
      filters.removeParam('expanded');
    } else {
      filters.setParam('expanded', true);
    }
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
      $('.panel-footer').removeClass('hidden');
    }

    if(results.totalResults > 50) {
      $('.c-per-page').removeClass('hidden');
      $('.panel-footer').removeClass('hidden');
    }
  }

  return {
    initialize: initialize,
    load: load,
    update: update,
    loadResultBar: loadResultBar,
  };
});
