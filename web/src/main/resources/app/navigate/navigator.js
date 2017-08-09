define(function(require) {

  function Navigator(template, pageClass) {
    this.tmpl = template;
    this.pageClass = pageClass;

    this.navigateTo = function(url) {
      var obj = this;
      $.getJSON(API_BASE  + url + "?callback=?", function(json) {
        obj.load(json);
        history.pushState({
          class: obj.pageClass,
          data: json,
        }, null, url);
      });
    }

    this.load = function(data) {
      $(".container").addClass(this.pageClass)
      $('#c-page-body').replaceWith(this.tmpl(data));
    }
  }

  return Navigator;
});

