var showName = require('./showName.js');
var Handlebars = require('handlebars');

module.exports = function(citation) {
  if(!citation) return;

  var name = showName(citation);

  if(citation.authors) {
    name += citation.authors;
  }

  if(citation.publication || citation.referenceCollation || citation.publicationYear) {
    name += ", <small>";

    if(citation.publication) {
      name += citation.publication + ' ';
    }

    if(citation.referenceCollation) {
      name += citation.referenceCollation;
    }

    if(citation.publicationYear) {
      name += ' (' + citation.publicationYear + ')';
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

    name += "</small>";
  }

  return new Handlebars.SafeString(name);
}
