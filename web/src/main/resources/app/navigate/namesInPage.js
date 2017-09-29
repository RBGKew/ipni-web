define(function(require) {

  var Handlebars = require('handlebars');
  var pagination = require('pagination');
  var resultsListTmpl = require('../search/tmpl/results-list.hbs');
  var currentPage = 0;
  var sort = "name_asc"
  var initialize = function() {
    getNames();
  }


  var loadInPage = function(results){
    $('.name-results').html(resultsListTmpl(results))
    $('body')
      .on('click', '.sort-by-in-page a', setSort);
  }

  function setSort(event) {
    event.preventDefault();
    sort = $(this).attr("id");
    getNames();
  }

  var getNames = function() {
      var apiUrl = "search?callback=?&perPage=20";
      var url = window.location.pathname;
      if(url.includes("urn:lsid:ipni.org:authors:")){
        apiUrl += '&names_by_author=*@' + url.replace("/urn:lsid:ipni.org:authors:", "") + '@*';
        load(apiUrl);
      }if(url.includes("urn:lsid:ipni.org:publications:")){
        apiUrl += '&publication id=' + url.replace("/", "");
        load(apiUrl);
      }

  }

  function load(apiUrl){
    apiUrl += "&page=" + currentPage;
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
          currentPage = page -1;
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
