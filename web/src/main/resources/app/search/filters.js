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
    _.each(suggesters, function(suggester) {
      if(!(suggester in res.suggestedTerms)) {
        return;
      }

      for(i = 0; i < res.suggestedTerms[suggester].length && i < 5; i++) {
        ret.push({
          value: res.suggestedTerms[suggester][i],
          category: humanize(suggester),
        });
      }
    });

    return ret;
  }

  var initialize = function(initial) {
    engine = new Bloodhound({
      datumTokenizer: Bloodhound.tokenizers.whitespace,
      queryTokenizer: Bloodhound.tokenizers.whitespace,
      remote: {
        url: API_BASE + 'suggest?query=%q&callback=?',
        wildcard: '%q',
        transform: transform,
      }
    });
    engine.initialize();

    tokenfield = $('#search').tokenfield({
      tokens: initial,
      typeahead: [
        {
          hint: false,
          highlight: true,
        },
        {
          display: 'value',
          limit: 8,
          source: engine.ttAdapter(),
          templates: { suggestion: suggestionTmpl }
        }
      ]
    })
      .on('tokenfield:createdtoken', createdToken)
      .on('tokenfield:removedtoken', publishUpdated);

    initialized = true;

    $(window).on('resize', refresh);
  }

  function publishUpdated(e) {
    pubsub.publish('search.updated');
  };

  function createdToken() {
    publishUpdated();
  };

  var setTokens = function(tokens) {
    if(_.isObject(tokens) && !_.isArray(tokens)) {
      tokens = _.map(tokens, function(val, key) {
        return val ? key + ':' + val : key
      });
    }

    if(initialized) {
      tokenfield.tokenfield('setTokens', tokens);
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
    if(params.has('page')) {
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

    if(params.has('page')) {
      params = params.delete('page');
    }

    if(_.defaultTo(publish, true)) {
      pubsub.publish('search.updated.params.' + key);
    }
  }

  var serialize = function() {
    var q = params.toObject();

    if(!_.isEmpty(this.filters())) {
      $.extend(q, {'q': _.map(this.filters(), 'value').join(',')});
    }

    return($.param(q));
  };

  var deserialize = function(serialized) {
    if(serialized[0] == '?') {
      serialized = serialized.slice(1);
    }

    var deserialized = deparam(serialized);
    for(key in deserialized) {
      if(key === 'q') continue;
      params = params.set(key, deserialized[key]);
    }

    if(_.isString(deserialized['q'])) {
      setTokens(deserialized['q'].split(','));
    } else {
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
    deserialize: deserialize,
    filters: getFilters,
    getParam: getParam,
    initialize: initialize,
    refresh: refresh,
    removeParam: removeParam,
    serialize: serialize,
    setParam: setParam,
    set: setTokens,
    clear: clear,
  }
});
