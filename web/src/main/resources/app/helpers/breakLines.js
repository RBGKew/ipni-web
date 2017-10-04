var Handlebars = require('handlebars');

module.exports = function(text) {
  return new Handlebars.SafeString(text.replace(/;/g, '<br/>'));
}
