var author = require('../linkify/author');
var Handlebars = require('handlebars');

module.exports = function(authorTeam) {
  return new Handlebars.SafeString(author.linkify(authorTeam));
}
