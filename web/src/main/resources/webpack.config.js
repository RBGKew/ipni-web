const path = require('path');
const webpack = require('webpack');

const CleanWebpackPlugin = require('clean-webpack-plugin');
const ExtractTextPlugin = require('extract-text-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = (env = {}) => {

  const production = env.production === true;
  console.log('building for production: ' + production);

  return {
    entry: {
      app: './app/main.js',
      vendor: [
        'bloodhound',
        'bootstrap',
        'handlebars',
        'immutable',
        'jquery',
        'lodash',
        'pagination',
        'pubsub-js',
        'rbgkew-typeahead',
      ],
    },

    output: {
      path: path.resolve(__dirname, 'static'),
      filename: '[name].[chunkhash].js',
    },

    module: {
      rules: [
        {
          test: /\.hbs$/,
          use: {
            loader: 'handlebars-loader',
            query: { inlineRequires: '/img/' }
          }
        },

        {
          test: /\.scss$/,
          use: ExtractTextPlugin.extract({
            fallback: 'style-loader',
            use: ['css-loader', 'sass-loader'],
          })
        },

        {
          test: /\.woff(2)?(\?v=[0-9]\.[0-9]\.[0-9])?$/,
          use: [{
            loader: 'url-loader',
            options: {
              limit: 10000,
              mimetype: 'application/font-woff',
            }
          }]
        },

        {
          test: /\.(ttf|eot|svg)(\?v=[0-9]\.[0-9]\.[0-9])?$/,
          use: 'file-loader',
        },

        {
          test: /\.(jpe?g|png|gif|svg)$/i,
          use: 'file-loader',
        }
      ]
    },

    plugins: [

      new ExtractTextPlugin({
        filename: 'style.css',
        disable: false,
        allChunks: true,
      }),

      new HtmlWebpackPlugin({
        template: 'app/index.hbs',
      }),

      new webpack.ProvidePlugin({
        $: 'jquery',
        jQuery: 'jquery',
        _: 'lodash',
      }),

      new webpack.optimize.CommonsChunkPlugin({
        names: ['vendor', 'manifest']
      }),

      new webpack.optimize.UglifyJsPlugin({
        compress: production
      }),

      new CleanWebpackPlugin(['static']),

      new webpack.DefinePlugin({
        'API_BASE': (() => {
          if(production) return JSON.stringify('/api/1/')
          else return JSON.stringify('http://localhost:18080/api/1/') // allows for running frontend via 'npm start'
        })()
      }),
    ],

    resolve: {
      alias: {
        handlebars: 'handlebars/dist/handlebars.js',
        pagination: 'simple-pagination.js/jquery.simplePagination.js',
        bloodhound: 'rbgkew-typeahead/dist/bloodhound.js',
        bootstrap: 'bootstrap-sass/assets/javascripts/bootstrap.js',
      }
    },

    devtool: (() => {
      if (production) return 'hidden-source-map'
      else return 'cheap-module-eval-source-map'
    })()
  }
};
