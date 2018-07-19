module.exports = function(value, menu_item) {
  if(value && value.includes(menu_item)) {
    return 'active';
  }
};
