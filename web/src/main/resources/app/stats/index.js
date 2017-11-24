define(function(require) {

  var c3 = require('c3');

  var publishedTmpl = require('./tmpl/published.hbs');
  var activityAddedTmpl = require('./tmpl/activity.hbs');

  var stats = {};
  var currentYear = new Date().getFullYear();

  var totals = function(table) {
    var total = 0;
    var res = _.reduce(Object.keys(table), function(memo, row) {
      memo[row] = _.sum(Object.values(table[row]));
      total += memo[row];
      _.each(table[row], function(num, column) {
        memo[column] = memo[column] ? memo[column] + num : num;
      });

      return memo;
    }, {});

    res['total'] = total;

    return res;
  }

  var renderPublished = function(data, year) {
    var years = _.rangeRight(1752, currentYear + 1);
    var obj = {
      years: years,
      current: year,
      data: data,
      totals: totals(data),
    };

    $('#published-container').html(publishedTmpl(obj));
  }

  var renderAdded = function(data, year) {
    var years = _.rangeRight(2003, currentYear + 1);
    var obj = {
      id: 'record-additions',
      years: years,
      current: year,
      data: data,
      action: 'added',
      totals: totals(data),
    }

    $('#added-container').html(activityAddedTmpl(obj));
  }

  var renderUpdated = function(data, year) {
    var years = _.rangeRight(2003, currentYear + 1);
    var obj = {
      id: 'record-updates',
      years: years,
      current: year,
      data: data,
      action: 'updated',
      totals: totals(data),
    }

    $('#updated-container').html(activityAddedTmpl(obj));
  }

  var show = function(getter) {
    return function(e) {
      var selected = $('option:selected', this);
      getter(selected.val());
      $(selected).focus();
    }
  }

  var published = function(year) {
    get('Published', year, 'stats/namesPublishedIn/', renderPublished);
  }

  var added = function(year) {
    get('Added', year, 'stats/recordsAdded/', renderAdded);
  }

  var updated = function(year) {
    get('Updated', year, 'stats/recordsUpdated/', renderUpdated);
  }

  function get(stat, year, apiMethod, render) {
    if(!_.hasIn(stats, [stat, year])) {
      $.getJSON(API_BASE + apiMethod + year, function(data) {
        _.merge(stats, {stat: {year: data}});
        render(data, year);
      });
    } else {
      render(stats[stat][year], year);
    }
  }

  function standardization() {
    $.getJSON(API_BASE + 'stats/standardization', function(data) {
      $('#standardization-container').before('<h4>Standardisation</h4>');
      c3.generate({
        bindto: '#standardization-container',
        data: {
          x: 'date',
          type: 'spline',
          rows: data
        },
        axis: {
          x: {
            type: 'timeseries',
            tick: { format: '%Y-%m-%d' }
          },
          y: {
            tick: {
              format: function(y) { return y + '%' }
            }
          }
        },
        point: { show: false }
      })
    });
  }

  var initialize = function() {
    published(currentYear);
    added(currentYear);
    updated(currentYear);
    standardization();

    $('.container')
      .on('change', '#npi-year', show(published))
      .on('change', '#record-additions', show(added))
      .on('change', '#record-updates', show(updated));
  }

  $(document).ready(function() {
    initialize()
  });

  return {
    initialize: initialize,
  };
});
