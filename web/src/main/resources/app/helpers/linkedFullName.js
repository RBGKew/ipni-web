var Handlebars = require('handlebars');
var linkify = require('../linkify');
var showName = require('./showName.js');

module.exports = function(citation) {
  if(!citation) return;

  var name = "<h2><i>" + showName(citation) + "</i> ";

  if(citation.authors) {
    name += linkify.author(citation);
  }

  name += '</h2>';

  if(citation.publication || citation.referenceCollation || citation.publicationYear) {
    name += ', ' + linkify.publication(citation);

    if(citation.referenceCollation) {
      name += ' ' + citation.referenceCollation;
    }

    if(citation.publicationYear) {
      name += ' (' + citation.publicationYear + ')';
    }

    if(!_.endsWith(name, '.')) {
      name += '.';
    }
  }

  return new Handlebars.SafeString(name);
}
