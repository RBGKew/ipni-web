define(function(require) {

  var Handlebars = require('handlebars');
  var pagination = require('pagination');
  var namesInPageTmpl = require('./tmpl/namesInPage.hbs');
  var resultsListTmpl = require('../search/tmpl/results-list.hbs');
  var currentPage = 1;
  var sort = "name_asc"
  var url;

  var initialize = function() {
    url = window.location.pathname;
    if(shouldLoad()) {
      getNames();
    }
  }

  function shouldLoad() {
    return $('.container').hasClass('p-author')
      || $('.container').hasClass('p-publication');
  }

  function pageType() {
    if($('.container').hasClass('p-author')) {
      return 'author';
    } else if($('.container').hasClass('p-publication')) {
      return 'publication';
    }
  }

  function loadInPage(results){
    if($('.names-in-page').children().size() === 0) {
      var connector = pageType() === 'author' ? ' by ' : ' in ';
      $(".names-in-page").html(namesInPageTmpl({
        'section-title': results.totalResults + ' names published' + connector + $('.stdForm').text()
      }))
    }

    $("body").find('.name-results').html(resultsListTmpl(results))
    $('body').on('click', '.sort-by-in-page a', setSort);
  }

  function setSort(event) {
    event.preventDefault();
    sort = $(this).attr("id");
    getNames();
  }

  function getNames() {
    var apiUrl = "search?callback=?&perPage=20";
    if(pageType() == "author") {
      apiUrl += '&author+team+ids=*@' + url.replace("/urn:lsid:ipni.org:authors:", "") + '@*aut*';
      load(apiUrl);
    } if(pageType() == "publication") {
      apiUrl += '&publication+id=' + url.replace("/", "");
      load(apiUrl);
    }
  }

  function load(apiUrl){
    apiUrl += "&page=" + (currentPage - 1);
    apiUrl += "&sort=" + sort;
    $.getJSON(API_BASE + apiUrl , function(data) {
      if(data.results && data.results.length){
        data['sort'] = sort;
        loadInPage(data);
        paginate(data);
        $(".names-by").removeClass("hidden")
      }
    });
  }

  function paginate(results) {
    if(results.totalPages > 1) {
      $('.c-pagination-in-page').pagination({
        items: results.totalResults,
        itemsOnPage: results.perPage,
        pages: results.totalPages,
        listStyle: 'pagination',
        hrefTextPrefix: '',
        currentPage : currentPage,
        onPageClick: function(page, e) {
          currentPage = page;
          getNames()
          if(e) e.preventDefault();
        }
      });
    }
  }

  return {
    initialize: initialize,
  };
});
