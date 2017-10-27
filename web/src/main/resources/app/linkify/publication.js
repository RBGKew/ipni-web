var linkify = function(citation) {
  if(citation.publication) {
    if(citation.publicationId) {
      return '<a href="/p/' + citation.publicationId + '" class="publication-link">' + citation.publication + '</a>';
    } else {
      return citation.publication;
    }
  }
}

module.exports = linkify
