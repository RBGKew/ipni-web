const test = require('tape')
const showName = require('../app/helpers/showName')

test('displays family name when nothing else is available', function(t) {
  var citation = { family: 'Cleomaceae' }
  var expected = '<i>Cleomaceae</i> ';
  var actual = showName(citation);

  t.equals(actual, expected);
  t.end();
});

test('de-italicises rank at beginning of name', function(t) {
  var citation = { name: 'gen. ser. Apoda' };
  var expected = 'gen. ser. <i>Apoda</i> ';
  var actual = showName(citation);

  t.equals(actual, expected);
  t.end();
});

test('Returns italicised binomial', function(t) {
  var citation = { name: 'Breynia officinalis' };
  var expected = '<i>Breynia officinalis</i> ';
  var actual = showName(citation);

  t.equals(actual, expected);
  t.end();
});

test('de-italicises rank', function(t) {
  var citation = { name: 'Breynia officinalis var. accrescens' };
  var expected = '<i>Breynia officinalis </i> var. <i>accrescens</i> ';
  var actual = showName(citation);

  t.equals(actual, expected);
  t.end();
});

test('de-italicises ranks that have other ranks as substrings ', function(t) {
  var citation = { name: 'Breynia officinalis subvar. accrescens' };
  var expected = '<i>Breynia officinalis </i> subvar. <i>accrescens</i> ';
  var actual = showName(citation);

  t.equals(actual, expected);
  t.end();
});

