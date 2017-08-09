module.exports = function(row, type, options) {
  if(row.recordType === type) {
    return options.fn(this);
  }
}
