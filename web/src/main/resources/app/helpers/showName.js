module.exports = function(taxon) {
  if(!taxon) return;

  if(taxon.name) {
    return taxon.name;
  } else {
    return taxon.family;
  }
};

