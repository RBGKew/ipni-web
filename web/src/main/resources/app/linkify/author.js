var _ = require('lodash/fp');

var link = function(author) {
  if(!_.isEmpty(author.id)) {
    return '<a href="/' + author.fullyQualifiedId + '" class="author-link">' + author.name + '</a>';
  } else {
    return author.name;
  }
}

var authorsFor = function(team) {
  return function(type) {
    return _.flow(
      _.filter({'type': type}),
      _.sortBy(function(a) {return a.order}),
      _.map(link),
      _.join(' & ')
    )(team);
  }
}

var authorStr = function(team, type) {
  var authors = authorsFor(team);
  var aut = authors(type);
  var Ex = authors(type + 'Ex');
  var In = authors(type + 'In');

  var str = aut;
  if(!_.isEmpty(Ex)) {
    str = str + ' ex ' + Ex;
  }

  if(!_.isEmpty(In)) {
    str = str + ' in ' + In;
  }

  return str;
}


var build = function(citation) {
  if(citation.authorTeam) {
    var basonymStr = authorStr(citation.authorTeam, 'bas');
    if(!_.isEmpty(basonymStr)) {
      basonymStr = '(' + basonymStr + ') ';
    }

    return basonymStr + authorStr(citation.authorTeam, 'aut');
  } else {
    return citation.authors;
  }
}

module.exports = {
  linkify: build,
}
