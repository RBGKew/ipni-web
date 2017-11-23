const path = require('path');
const webpack = require('webpack');

const CleanWebpackPlugin = require('clean-webpack-plugin');
const ExtractTextPlugin = require('extract-text-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CopyWebpackPlugin = require('copy-webpack-plugin');

module.exports = (env = {}) => {

  const production = env.production === true;
  console.log('building for production: ' + production);

  return {
    entry: {
      app: './app/main.js',
      vendor: [
        'bloodhound',
        'bootstrap',
        'c3',
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
      publicPath: '/',
      filename: '[name].[chunkhash].js',
    },

    module: {
      rules: [
        {
          test: /\.hbs$/,
          use: {
            loader: 'handlebars-loader',
            query: {
              inlineRequires: '/img/',
              helperDirs: [__dirname + "/app/helpers"]
            }
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
          test: /\.(ttf|eot|svg|woff2|woff)(\?v=[0-9]\.[0-9]\.[0-9])?$/,
          use: 'file-loader',
        },

        {
          test: /\.(jpe?g|png|gif|svg)$/i,
          use: 'file-loader',
        }
      ]
    },

    plugins: [

      new CleanWebpackPlugin(['static']),

      new HtmlWebpackPlugin({ template: 'app/index.hbs', }),

      // provide standard libs so they don't have to be required everywhere
      new webpack.ProvidePlugin({
        $: 'jquery',
        jQuery: 'jquery',
        _: 'lodash',
      }),

      new ExtractTextPlugin({
        filename: 'style.[chunkhash].css',
        disable: false,
        allChunks: true,
      }),

      new webpack.optimize.CommonsChunkPlugin({
        names: ['vendor', 'manifest']
      }),

      new webpack.optimize.UglifyJsPlugin({
        compress: production
      }),

      new webpack.DefinePlugin({
        'API_BASE': (() => {
          if(production)
            return JSON.stringify('/api/1/')
          else
            // allows for running frontend through webpack dev server and 'npm start'
            return JSON.stringify('http://localhost:18080/api/1/')
        })()
      }),

      /*
       * Export a handlebars template that can be rendered serverside, but which also
       * has the js/css assets injected via webpack. extra .hbs templates are copied
       * into the static folder as well
       */
      new HtmlWebpackPlugin({ template: 'app/author.html', filename: 'author.hbs' }),
      new HtmlWebpackPlugin({ template: 'app/name.html', filename: 'name.hbs' }),
      new HtmlWebpackPlugin({ template: 'app/publication.html', filename: 'publication.hbs' }),
      new HtmlWebpackPlugin({ template: 'app/static.html', filename: 'static.hbs' }),
      new HtmlWebpackPlugin({ template: 'app/stats.html', filename: 'stats.html' }),
      new CopyWebpackPlugin([
        { from: '**/tmpl/*.hbs', context: 'app' },
        { from: 'app/gtm.hbs' },
        { from: 'app/footer.hbs' }
      ]),
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
