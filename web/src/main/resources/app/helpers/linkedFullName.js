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
  }

  if(citation.nameStatusType || citation.nameStatusBotCode) {
    if(name.endsWith() == '.') {
      name = name.substring(0, name.length - 1) + ',';
    } else {
      name += ',';
    }

    if(citation.nameStatusType) {
      name += ' ' + citation.nameStatusType;
    }

    if(citation.nameStatusBotCode) {
      name += ' ' + citation.nameStatusBotCode;
    }
  }

  if(!name.endsWith('.')) {
    name += '.';
  }

  return new Handlebars.SafeString(name);
}
