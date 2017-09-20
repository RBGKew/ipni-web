define(function(require) {

  var Handlebars = require('handlebars');
  var pagination = require('pagination');
  var filters = require('./filters');
  var events = require('./events');

  var resultsTmpl = require('./tmpl/results.hbs');
  var resultsListTmpl = require('./tmpl/results-list.hbs');
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
      data['expanded'] = filters.getParam('expanded');
      if($('#c-page-body').length) {
        $('#c-page-body').replaceWith(resultsTmpl(data));
      } else {
        $('.content').after(resultsTmpl(data));
      }

      paginate(data);
      filters.refresh();
  }

  var loadInPage = function(results){
    $('.name-results').html(resultsListTmpl(results))
  }

  var getNamesBy = function(url, type) {
    if(type == "author"){
      url = url.replace("/urn:lsid:ipni.org:authors:", "");
      $.getJSON(API_BASE + 'search?callback=?&names_by_author=*@' + url + '@*', function(results) {
        loadInPage(results);
      });
    }if(type == "publication"){

    }
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

    //if($(window).width() < 992) {
  //    $(document).scrollTop( $("#search_box").offset().top);
  //  }

    $('body')
      .on('click', '.sort-by a', setSort)
      .on('click', '.filter-by .btn', toggleFilter)
      .on('change', '.c-per-page', setLimit)
      .on('click', '.show-detailed-record', showDetailedRecords);
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
      if(filters.getParam('expanded')){
        filters.removeParam('expanded');
      }else{
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
    }

    if(results.totalResults > 50) {
      $('.c-per-page').removeClass('hidden');
    }
  }

  return {
    initialize: initialize,
    load: load,
    update: update,
    getNamesBy : getNamesBy,
  };
});
