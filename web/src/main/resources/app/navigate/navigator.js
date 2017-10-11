define(function(require) {

  var startsWith = require('lodash/fp/startsWith');
  function Navigator(template, pageClass) {
    this.tmpl = template;
    this.pageClass = pageClass;

    this.navigateTo = function(url, cb) {
      var obj = this;
      $.getJSON(API_BASE  + url + "?callback=?", function(json) {
        obj.load(json);
        history.pushState({
          class: obj.pageClass,
          data: json,
        }, null, url);

        if(cb) {
          cb();
        }
      });
    }

    this.pageClasses = function(index, classes) {
      return _.filter(classes.split(' '), startsWith('p-')).toString();
    }

    this.load = function(data, cb) {
      $('.container').removeClass(this.pageClasses).addClass(this.pageClass);
      $('#c-page-body').replaceWith(this.tmpl(data));

      if(cb) {
        cb();
      }
    }
  }

  return Navigator;
});
