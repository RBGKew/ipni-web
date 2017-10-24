var _ = require('lodash');

// Ranks must be ordered in decending order of length so correct rank is replaced
var ranks = [
  "[infrafam.unranked]",
  "[infragen.unranked]",
  "[infrasp.unranked]",
  "[infragen.grex]",
  "c.[infragen.]",
  "infragen.grex",
  "nothosubsect.",
  "nothosubtrib.",
  "supersubtrib.",
  "nothosubgen.",
  "[infragen.]",
  "nothosubsp.",
  "subsubforma",
  "grex_sect.",
  "[infragen]",
  "nothosect.",
  "subgenitor",
  "subsubvar.",
  "supersect.",
  "supertrib.",
  "suprasect.",
  "agamovar.",
  "[epsilon]",
  "gen. ser.",
  "microgen.",
  "nothogrex",
  "nothoser.",
  "nothovar.",
  "superser.",
  "agamosp.",
  "[alpha].",
  "[gamma].",
  "subhybr.",
  "subsect.",
  "subspec.",
  "subtrib.",
  "agglom.",
  "[beta].",
  "convar.",
  "genitor",
  "monstr.",
  "nothof.",
  "subfam.",
  "subgen.",
  "sublus.",
  "subser.",
  "subvar.",
  "f.juv.",
  "Gruppe",
  "modif.",
  "proles",
  "stirps",
  "subsp.",
  "cycl.",
  "forma",
  "group",
  "linea",
  "prol.",
  "sect.",
  "spec.",
  "subf.",
  "trib.",
  "fam.",
  "gen.",
  "grex",
  "lus.",
  "mut.",
  "oec.",
  "psp.",
  "race",
  "ser.",
  "var.",
  "ap.",
  "II.",
  "nm.",
  "2.",
  "A.",
  "B.",
  "C.",
  "D.",
  "E.",
  "f.",
  "G.",
  "H.",
  ">Z",
];

module.exports = function(citation) {
  if(citation.name) {
    var formatted = '<i>' + citation.name + '</i> ';
    _.each(ranks, function(rank) {
      if(formatted.indexOf(rank) !== -1) {
        formatted = formatted.replace(rank + ' ', '</i> ' + rank + ' <i>');
        formatted = formatted.replace('<i></i>', '');
        formatted = _.trimStart(formatted);

        // break out of loop so ranks that are substrings of others won't be replaced
        return false;
      }
    });

    return formatted;
  } else {
    return '<i>' + citation.family + '</i> ';
  }
};

