test = require('tape');
bhl = require('../app/bhl');

const options = {
  fn: function(context) { return context.link + ". "; }
}

test('find page ids', function(t) {
  t.ok(bhl.hasPageId('[BHL_page_id:690]'));
  t.ok(bhl.hasPageId('blah blah [BHL_page_id:690] blah'));
  t.notOk(bhl.hasPageId('blah blah blah'));
  t.notOk(bhl.hasPageId(''));

  t.end();
})

test('find title ids', function(t) {
  t.ok(bhl.hasTitleId('[BHL_title_id:690]'));
  t.ok(bhl.hasTitleId('blah blah blah [BHL_title_id:690] blah'));
  t.notOk(bhl.hasTitleId('blah blah blah'));
  t.notOk(bhl.hasTitleId(''));

  t.end();
})

test('generate page links', function(t) {
  var actual = bhl.pageLinks('[BHL_page_id:690]', options);
  var expected = 'http://www.biodiversitylibrary.org/openurl?pid=page:690. ';

  t.equals(actual, expected);
  t.end();
});

test('generate title links', function(t) {
  var actual = bhl.titleLinks('[BHL_title_id:690]', options);
  var expected = 'http://www.biodiversitylibrary.org/openurl?pid=title:690. ';

  t.equals(actual, expected);
  t.end();
});

test('stip title ids', function(t) {
  var actual = bhl.strip('blah blah [BHL_title_id:690]')
  var expected = 'blah blah ';

  t.equals(actual, expected);
  t.end();
})

test('stip markers', function(t) {
  var actual = bhl.strip('BHL has 1887-1900 and 1901-1923 as two records [BHL_title_id:10719][BHL_title_id:10719]')
  var expected = 'BHL has 1887-1900 and 1901-1923 as two records ';

  t.equals(actual, expected);
  t.end();
})

test('handles multiple markers', function(t) {
  var actual = bhl.titleLinks('BHL has 1887-1900 and 1901-1923 as two records [BHL_title_id:10719][BHL_title_id:10719]', options)
  var expected = 'http://www.biodiversitylibrary.org/openurl?pid=title:10719. http://www.biodiversitylibrary.org/openurl?pid=title:10719. '

  t.equals(actual, expected);
  t.end();
})
