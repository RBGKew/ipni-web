define(function(require) {

  var Handlebars = require('handlebars');
  var nameTmpl = require('./tmpl/name.hbs');
  var namePageClass = 'c-name';

  var navigateTo = function(url) {
    $.getJSON(API_BASE  + url + "?callback=?", function(json) {
      load(json);
      history.pushState({
        class: namePageClass,
        data: json,
      }, null, url);
    });
  }

  var load = function(data) {
    $(".container").addClass(namePageClass)
    $('#c-page-body').replaceWith(nameTmpl(data));
  }

  return {
    navigateTo : navigateTo,
    load: load,
  }
});
