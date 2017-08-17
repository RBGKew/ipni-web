const test = require('tape');
const doi = require('../app/linkify/doi');

test('linkifies doi:xxx codes', function(t) {
  const actual = doi.linkify('doi:10.3100/hpib.v21iss1.2016.n6 [english]');
  const expected = '<a href="https://dx.doi.org/10.3100/hpib.v21iss1.2016.n6" target="_blank">doi:10.3100/hpib.v21iss1.2016.n6</a> [english]'

  t.equals(actual, expected);
  t.end();
});
