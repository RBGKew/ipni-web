var showName = require('./showName.js');
var Handlebars = require('handlebars');

module.exports = function(citation) {
  if(!citation) return;

  var name = "<i>" + showName(citation) + "</i> ";

  if(citation.authors) {
    name += citation.authors;
  }

  if(citation.publication || citation.referenceCollation || citation.publicationYear) {
    name += ", <small>";

    if(citation.publication) {
      name += citation.publication + ' ';
    }

    if(citation.referenceCollation) {
      name += citation.referenceCollation + ' ';
    }

    if(citation.publicationYear) {
      name += '(' + citation.publicationYear + ')';
    }

     name += "</small>";
  }

  return new Handlebars.SafeString(name);
}
