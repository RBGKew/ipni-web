var path = require('path');
var HtmlWebpackPlugin = require('html-webpack-plugin');
var webpack = require('webpack');

module.exports = {

  entry: './app/main.js',

  output: {
    path: path.resolve(__dirname, 'static'),
    filename: 'bundle.js',
  },

  module: {
    loaders: [
      { test: /\.hbs$/, loader: 'handlebars-loader', query: { inlineRequires: '/img/' } },
      { test: /\.scss$/, loader: 'style-loader!css-loader!sass-loader' },
      { test: /\.woff(2)?(\?v=[0-9]\.[0-9]\.[0-9])?$/, loader: 'url-loader?limit=10000&mimetype=application/font-woff' },
      { test: /\.(ttf|eot|svg)(\?v=[0-9]\.[0-9]\.[0-9])?$/, loader: 'file-loader' },
      { test: /\.(jpe?g|png|gif|svg)$/i, loader:'file-loader' }
    ]
  },

  plugins: [
    new HtmlWebpackPlugin({template: 'app/index.hbs'}),
    new webpack.ProvidePlugin({
      $: 'jquery',
      jQuery: 'jquery',
      _: 'lodash'
    })
  ],

  resolve: {
    alias: {
      handlebars: 'handlebars/dist/handlebars.js',
      pagination: 'simple-pagination.js/jquery.simplePagination.js',
      bloodhound: 'rbgkew-typeahead/dist/bloodhound.js'
    }
  },

  devtool: '#source-map',
};
