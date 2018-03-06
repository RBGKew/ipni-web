var PATTERN = /doi:(\S+)/g;
var REPLACEMENT = '<a href="https://doi.org/$1" class="doi-link" data-doi="$1" target="_blank">$&</a>';

module.exports = {
  linkify: function(text) {
    return text.replace(PATTERN, REPLACEMENT);
  }
}
