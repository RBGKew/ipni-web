/**
 * State module: Keeps track of the state of applied search filters
 *
 * Publishes events on 'search.filters' channel
 */
define(function(require) {
  require('../vendor/bootstrap-tokenfield');

  var Immutable = require('immutable');
  var pubsub = require('pubsub-js');
  var deparam = require('deparam');
  var Bloodhound = require('bloodhound');
  var typeahead = require('rbgkew-typeahead');
  var Handlebars = require('handlebars');

  var suggestionTmpl = require('./tmpl/suggestion.hbs');

  var initialized = false;
  var tokenfield;
  var params = Immutable.Map();

  var suggesters = [
    'scientific-name',
    'author',
    'publication'
  ];

  function humanize(name) {
    return name.split('-').join(' ');
  }

  function transform(res) {
    ret = [];
    var totalSuggestions = _.reduce(
      res.suggestedTerms,
      function(n, sug) { return n + sug.length },
      0
    );

    _.each(suggesters, function(suggester) {
      if(!(suggester in res.suggestedTerms)) {
        return;
      }

      for(i = 0; i < res.suggestedTerms[suggester].length && i < 5; i++) {
        var val = res.suggestedTerms[suggester][i];
        val = val.replace('>', '');
        ret.push({
          value: suggester === suggesters[0] ? val : suggester + ":" + val,
          display: val,
          category: suggesterToGlyphicon(suggester),
        });
      }
    });

    return ret;
  }

  function suggesterToGlyphicon(category) {
    switch(category) {
      case 'scientific-name':
        return '';
      case 'author':
        return 'glyphicon-user';
      case 'publication':
        return 'glyphicon-book';
    }
  }

  var initialize = function(initial) {
    engine = new Bloodhound({
      datumTokenizer: Bloodhound.tokenizers.whitespace,
      queryTokenizer: Bloodhound.tokenizers.whitespace,
      remote: {
        url: API_BASE + 'suggest?query=%q',
        wildcard: '%q',
        transform: transform,
      }
    });
    engine.initialize();

    tokenfield = $('#search').tokenfield({
      tokens: initial,
      allowPasting: false,
      typeahead: [
        {
          hint: false,
          highlight: true,
        },
        {
          display: 'value',
          limit: 6,
          source: engine.ttAdapter(),
          templates: { suggestion: suggestionTmpl }
        }
      ]
    })
      .on('tokenfield:createdtoken', tokenChanged)
      .on('tokenfield:removedtoken', tokenChanged);

    initialized = true;

    $(window).on('resize', refresh);
  }

  function tokenChanged(e) {
    params = params.clear();
    publishUpdated();
  };

  function publishUpdated(e) {
    pubsub.publish('search.updated');
  };

  var setTokens = function(tokens, update) {
    update = _.defaultTo(update, true);
    if(_.isObject(tokens) && !_.isArray(tokens)) {
      tokens = _.map(tokens, function(val, key) {
        return val ? key + ':' + val : key
      });
    }

    if(initialized) {
      tokenfield.tokenfield('setTokens', tokens, false, update);
    } else {
      initialize(tokens);
    }
  }

  var add = function(key, val) {
    tokenfield.tokenfield('createToken', val ? key + ":" + val : key);
  }

  var getFilters = function() {
    return tokenfield.tokenfield('getTokens');
  }

  var setParam = function(key, value, publish) {
    if(params.has('page') && key != "expanded") {
      params = params.delete('page');
    }

    params = params.set(key, value);
    if(_.defaultTo(publish, true)) {
      pubsub.publish('search.updated.params.' + key);
    }
  };

  var getParam = function(key) {
    return params.get(key);
  };

  var removeParam = function(key, publish) {
    params = params.delete(key);

    if(params.has('page') && key != "expanded") {
      params = params.delete('page');
    }

    if(_.defaultTo(publish, true)) {
      pubsub.publish('search.updated.params.' + key);
    }
  }

  var toggleFacet = function(facet) {
    var facets = params.get('f');
    facets = _.isUndefined(facets) ? [] : _.split(facets, ',');
    if(_.includes(facets, facet)) {
      _.remove(facets, function(f) { return f === facet })
    } else {
      facets.push(facet);
    }

    params = params.delete('page');
    params = params.set('f', _.join(facets, ','));
    publishUpdated();
  }

  var serialize = function() {
    var q = params.toObject();

    if(!_.isEmpty(this.filters())) {
      $.extend(q, {'q': _.map(this.filters(), 'value').join(',')});
    }

    return($.param(q));
  };

  var deserialize = function(serialized, publish) {
    if(serialized[0] == '?') {
      serialized = serialized.slice(1);
    } else {
      return;
    }

    var deserialized = deparam(serialized);
    for(key in deserialized) {
      if(key === 'q') continue;
      params = params.set(key, deserialized[key]);
    }

    if(_.isString(deserialized['q'])) {
      setTokens(deserialized['q'].split(','), false);
    }

    if(_.defaultTo(publish, true)) {
      publishUpdated();
    }
  }

  var refresh = function() {
    tokenfield.tokenfield('update');
  }

  var clear = function() {
    setTokens([]);
  }

  return {
    add: add,
    clear: clear,
    deserialize: deserialize,
    filters: getFilters,
    getParam: getParam,
    initialize: initialize,
    refresh: refresh,
    removeParam: removeParam,
    serialize: serialize,
    set: setTokens,
    setParam: setParam,
    toggleFacet: toggleFacet,
  }
});
