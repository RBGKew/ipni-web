define(function(require) {

  var startsWith = require('lodash/fp/startsWith');
  function Navigator(template, pageClass) {
    this.tmpl = template;
    this.pageClass = pageClass;

    this.navigateTo = function(url, cb) {
      var obj = this;

      $.getJSON(this.fq(url), function(json) {
        obj.load(json);
        $('html, body').animate({scrollTop: '0px'}, 300);
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
    };

    this.fq = function(url) {
      if(url.charAt(0) === '/') {
        url = url.substring(1);
      }

      return API_BASE + url;
    }

    this.staticNavigateTo = function(url){
      $('html, body').animate({scrollTop: '0px'}, 300);

      history.pushState({
        class: this.pageClass,
      }, null, url)
      $('.container').removeClass(this.pageClasses).addClass(this.pageClass);
      if($('#c-page-body').length) {
        $('#c-page-body').replaceWith(this.tmpl());
      } else {
        $('.content').after(this.tmpl());
      }

    }

  }


  return Navigator;
});
