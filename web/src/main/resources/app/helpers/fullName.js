var showName = require('./showName.js');
var Handlebars = require('handlebars');

module.exports = function(citation) {
  if(!citation) return;

  var name = "<i>" + showName(citation) + "</i> ";

  if(citation.authors) {
    name += citation.authors;
  }

  if(citation.reference) {
    name += ", <small>" + citation.reference + "</small>";
  }

  return new Handlebars.SafeString(name);
}
