define(function(require) {

  var formatAuthors = function(data) {
    var authorFullnames = _.map(data.author, function(author) {
      return author.given + ' ' + author.family;
    });

    return _.join(authorFullnames, ', ');
  }

  var formatPublication = function(data) {
    return _.join(data['container-title'], ', ');
  }

  var lookup = function(doiLink) {
    doiLink = $(doiLink);
    var doi = doiLink.data('doi');

    // the ?mailto param is added to be polite as per: https://github.com/CrossRef/rest-api-doc#etiquette
    $.ajax("https://api.crossref.org/v1/works/" + encodeURI(doi) + "?mailto=bi@kew.org")
      .done(function(result) { addPopover(result, doiLink); })
      .fail(function(e) { console.log("couldn't access crossref api"); });
  }

  var addPopover = function(result, doiLink) {
    var authors = formatAuthors(result.message);
    var publication = formatPublication(result.message);

    var content = '<dl>';

    content += '<dt>Title</dt><dd>' + result.message.title + '</dd>';

    if(authors) {
      content += '<dt>Authors</dt><dd>' + authors + '</dd>';
    }

    if(publication) {
      content += '<dt>Publication</dt><dd>' + publication + '</dd>';
    }

    if(result.message.publisher) {
      content += '<dt>Publisher</dt><dd>' + result.message.publisher + '</dd>';
    }

    content += '</dl>';

    doiLink.popover({
      title: 'DOI Metadata',
      content: content,
      placement: 'right',
      trigger: 'hover',
      html: true,
    });
  }

  var initialize = function() {
    _.each($('.doi-link'), function(doiLink) {
      lookup(doiLink);
    });
  };

  $(document).ready(function() {
    initialize();
  });

  return { initialize: initialize }

});
