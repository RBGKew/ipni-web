const test = require('tape')
const linkify = require('../app/linkify/author')

const citation = {
  'authorTeam': [
    {
      'name': 'P.S.Wang',
      'id': '14980-1',
      'order': 3,
      'type': 'autEx',
      'fullyQualifiedId': 'urn:lsid:ipni.org:authors:14980-1'
    },
    {
      'name': 'X.Y.Wang',
      'id': null,
      'order': 4,
      'type': 'autEx',
      'fullyQualifiedId': null
    },
    {
      'name': 'Z.R.Wang',
      'id': '14556-1',
      'order': 2,
      'type': 'aut',
      'fullyQualifiedId': 'urn:lsid:ipni.org:authors:14556-1'
    },
    {
      'name': 'Z.R.Wang',
      'id': '14556-1',
      'order': 5,
      'type': 'bas',
      'fullyQualifiedId': 'urn:lsid:ipni.org:authors:14556-1'
    },
    {
      'name': 'W.M.Chu',
      'id': '13738-1',
      'order': 1,
      'type': 'aut',
      'fullyQualifiedId': 'urn:lsid:ipni.org:authors:13738-1'
    }
  ],
  'authors' : 'blah'
}

test('builds author string from author team', function(t) {
  const expected = '(<a href="/urn:lsid:ipni.org:authors:14556-1" class="author-link">Z.R.Wang</a>) '
    + '<a href="/urn:lsid:ipni.org:authors:13738-1" class="author-link">W.M.Chu</a> & <a href="/urn:lsid:ipni.org:authors:14556-1" class="author-link">Z.R.Wang</a> '
    + 'ex <a href="/urn:lsid:ipni.org:authors:14980-1" class="author-link">P.S.Wang</a> & X.Y.Wang'
  const actual = linkify(citation)

  t.equal(actual, expected)
  t.end()
})
