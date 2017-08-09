module.exports = function(result) {
  var href = 'href="' + result.url + '"';
  var klass = 'class="list-group-item result name-link';
  if(result.topCopy && result.recordType === 'citation') {
    klass += ' top-copy';
  }

  var resultRow;
  switch(result.recordType) {
    case 'citation':
      resultRow = '<em>' + (result.name ? result.name : result.family) + '</em> ' + result.authors;
      if(reference) {
        resultRow += ', <small>' + result.reference + '</small>';
      }
      break;
    case 'publication':
      break;
    case 'author':
      break;
  }


   return "<a " + href + " " + klass + ">" + resultRow + "</a>";
};
