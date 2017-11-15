define(function(require) {
  var startsWith = require('lodash/fp/startsWith');
  var staticPages = require.context('./tmpl', false);

  function pageClasses(index, classes) {
    return _.filter(classes.split(' '), startsWith('p-')).toString();
  }

  function load(url, cb) {
    $('.container').removeClass(pageClasses).addClass('p-static');
    template = staticPages('./' + stripSlash(url) + '.hbs');
    if($('#c-page-body').length) {
      $('#c-page-body').replaceWith(template);
    } else {
      $('#jumbotron').removeClass('vertically-centred');
      $('#jumbotron').removeClass('jumbotron');
      $('.content').after(template);
    }
    if(cb) {
      cb();
    }
  }

  function navigateTo(url, cb) {
    $('html, body').animate({scrollTop: '0px'}, 300);
    history.pushState({
      class: 'p-static',
    }, null, url)

    load(url, cb)
  }

  function stripSlash(url) {
    if(url.charAt(0) === '/') {
      return url.substring(1);
    }
  }

  return {
    navigateTo: navigateTo,
    load: load
  };
});
