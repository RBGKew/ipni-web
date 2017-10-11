define(function(require) {
  var Navigator = require('./navigator');
  var nameTmpl = require('./tmpl/name.hbs');
  var authorTmpl = require('./tmpl/author.hbs');
  var publicationTmpl = require('./tmpl/publication.hbs');
  var filters = require('../search/filters');
  var results = require('../search/results');
  var name = new Navigator(nameTmpl, 'p-name')
  var author = new Navigator(authorTmpl, 'p-author')
  var publication = new Navigator(publicationTmpl, 'p-publication')
  var namesInPage = require('./namesInPage')
  var crossref = require('../crossref');

  function showNameDetail(e) {
    e.preventDefault();
    var link = $(this).attr('href');
    name.navigateTo(link, crossref.initialize);
    filters.clear();
  }

  function showAuthorDetail(e) {
    e.preventDefault();
    var link = $(this).attr('href');
    author.navigateTo(link, namesInPage.initialize);
    filters.clear();
  }

  function showPublicationDetail(e) {
    e.preventDefault();
    var link = $(this).attr('href');
    publication.navigateTo(link, namesInPage.initialize);
    filters.clear();
  }


  function initialize() {
    window.onpopstate = function(event) {
      switch(event.state.class) {
        case 'p-search':
          filters.deserialize(window.location.search, false);
          results.load(event.state.data);
          break;
        case 'p-name':
          name.load(event.state.data, crossref.initialize);
          filters.clear();
          break;
        case 'p-publication':
          publication.load(event.state.data, namesInPage.initialize);
          filters.clear();
          break;
        case 'p-author':
          author.load(event.state.data, namesInPage.initialize);
          filters.clear();
          break;
      }
    };

    $('body')
      .on('click', '.name-link', showNameDetail)
      .on('click', '.author-link', showAuthorDetail)
      .on('click', '.publication-link', showPublicationDetail);

    $("body").tooltip({ selector: '[data-toggle=tooltip]' });
    namesInPage.initialize();
    crossref.initialize();
  }

  $(document).ready(initialize);
});
