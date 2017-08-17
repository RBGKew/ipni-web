var bhl = require('../bhl');

module.exports = function(text, options) {
  if(bhl.hasTitleId(text) || bhl.hasPageId(text)) {
    return options.fn(this);
  }
}
