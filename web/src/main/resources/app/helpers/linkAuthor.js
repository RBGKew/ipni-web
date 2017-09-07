var author = require('../linkify/author');
var Handlebars = require('handlebars');

module.exports = function(authorTeam) {
  if(authorTeam === null){
    return null;
  }
  return new Handlebars.SafeString(author.linkify(authorTeam));
}
