define(function(require) {

  function Navigator(template, pageClass) {
    this.Handlebars = require('handlebars');
    this.nameTmpl = require('./tmpl/author.hbs');
    this.pageClass = 'c-author';

    this.navigateTo = function(url) {
      $.getJSON(API_BASE  + url + "?callback=?", function(json) {
        load(json);
        history.pushState({
          class: pageClass,
          data: json,
        }, null, url);
      });
    }

    this.load = function(data) {
      $(".container").addClass(namePageClass)
      $('#c-page-body').replaceWith(nameTmpl(data));
    }
  }

  return Navigator;
});

