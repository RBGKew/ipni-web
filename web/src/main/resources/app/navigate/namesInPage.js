define(function(require) {

  var Handlebars = require('handlebars');
  var pagination = require('pagination');
  var namesInPageTmpl = require('./tmpl/namesInPage.hbs');
  var resultsListTmpl = require('../search/tmpl/results-list.hbs');
  var currentPage = 0;
  var sort = "name_asc"
  var pageType;
  var url;

  var initialize = function() {
    url = window.location.pathname;
    var title;
    if(url.includes("urn:lsid:ipni.org:authors:")){
      pageType = "author";
      title = {'section-title': 'Names Published by this Author'}
      $(".names-in-page").html(namesInPageTmpl(title))
      getNames();
    }if(url.includes("urn:lsid:ipni.org:publications:")){
      pageType = "publication";
      title = {'section-title': 'Names Published in this Publication'}
      $(".names-in-page").html(namesInPageTmpl(title))
      getNames();
    }
  }

  function loadInPage(results){
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
    if(pageType == "author"){
      apiUrl += '&author+team+ids=*@' + url.replace("/urn:lsid:ipni.org:authors:", "") + '@*';
      load(apiUrl);
    }if(pageType == "publication"){
      apiUrl += '&publication+id=' + url.replace("/", "");
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
