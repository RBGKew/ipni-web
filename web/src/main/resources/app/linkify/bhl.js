var handlebars = require('handlebars');

var TITLE = /\[BHL_title_id:(\d+)\]/g;
var PAGE = /\[BHL_page_id:(\d+)\]/g;
var OPENURL = "http://www.biodiversitylibrary.org/openurl?pid=";

var extract = function(pattern, queryparam) {
  return function(text, options) {
    var ret = "";

    while((result = pattern.exec(text)) !== null) {
      ret = ret + options.fn({link: OPENURL + queryparam + ":" + result[1]});
    }

    return ret;
  }
}

var strip = function(text) {
  text = text.replace(PAGE, '')
  text = text.replace(TITLE, '')

  return text;
}

module.exports =  {
  hasPageId: function(text) {
    return new RegExp(PAGE).test(text);
  },

  hasTitleId: function(text) {
    return new RegExp(TITLE).test(text);
  },

  pageLinks: extract(PAGE, 'page'),

  titleLinks: extract(TITLE, 'title'),

  strip: strip
}
