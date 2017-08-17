var doi = require('../linkify/doi');
var Handlebars = require('handlebars');

module.exports = function(text) {
  return new Handlebars.SafeString(doi.linkify(text));
}
