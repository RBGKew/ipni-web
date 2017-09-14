define(function(require) {
  var Navigator = require('./navigator');
  var nameTmpl = require('./tmpl/name.hbs');
  var authorTmpl = require('./tmpl/author.hbs');
  var publicationTmpl = require('./tmpl/publication.hbs');
  var filters = require('../search/filters');
  var results = require('../search/results');
  var name = new Navigator(nameTmpl, 'c-name')
  var author = new Navigator(authorTmpl, 'c-author')
  var publication = new Navigator(publicationTmpl, 'c-publication')

  function showNameDetail(e) {
    e.preventDefault();
    var link = $(this).attr('href');
    name.navigateTo(link);
    filters.clear();
  }

  function showAuthorDetail(e) {
    e.preventDefault();
    var link = $(this).attr('href');
    author.navigateTo(link);
    filters.clear();
  }

  function showPublicationDetail(e) {
    e.preventDefault();
    var link = $(this).attr('href');
    publication.navigateTo(link);
    filters.clear();
  }


  function initialize() {
    window.onpopstate = function(event) {
      switch(event.state.class) {
        case 'c-search':
          filters.deserialize(window.location.search, false);
          results.load(event.state.data);
          $('.container').removeClass('c-name');
          break;
        case 'c-name':
          name.load(event.state.data);
          $('.container').removeClass('c-search');
          break;
      }
    };

    $('body')
      .on('click', '.name-link', showNameDetail)
      .on('click', '.author-link', showAuthorDetail)
      .on('click', '.publication-link', showPublicationDetail);

    $("body").tooltip({ selector: '[data-toggle=tooltip]' });
  }

  $(document).ready(initialize);
});
