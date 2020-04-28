### 20 Сборка всего добра в единый файл для деплоя

  - Переносим все файлы из resources/static/js в корень resources/js  
   [Подробности сборки](https://webpack.js.org/guides/production/)
  - Устанавливаем приложение сборки 
    yarn add -D webpack-merge
       
  - Переименовуем файлы
    - webpack.config.js -> webpack.common.js  
  - Создаем дополнительные файлы конфигурации
     + webpack.dev.js
     + webpack.prod.js  
  - Разделяем настройки по файлам        
    webpack.common.js
  
        const path = require('path');
        const VueLoaderPlugin = require('vue-loader/lib/plugin');
        module.exports = {
-            mode: 'development',
-            devtool: 'source-map',
            entry: path.join(__dirname, 'src', 'main', 'resources', 'static', 'js', 'main.js'),
            devServer: {
                contentBase: './dist',
                compress: true,
                port: 8000,
                allowedHosts: [
                    'localhost:8080'
                ],
                stats: 'errors-only',
                clientLogLevel: 'error'
            },
            module: {
                rules: [
                    {
                        test: /\.js$/,
                        exclude: /(node_modules|bower_components)/,
                        use: {
                            loader: 'babel-loader',
                            options: {
                                presets: ['@babel/preset-env']
                            }
                        }
                    },
                    {
                        test: /\.vue$/,
                        loader: 'vue-loader'
                    },
                    {
                        test: /\.css$/,
                        use: [
                            'vue-style-loader',
                            'css-loader'
                        ]
                    },
     
                ]
            },
            plugins: [
                new VueLoaderPlugin()
            ],
            resolve: {
                modules: [
                    path.join(__dirname, 'src', 'main', 'resources', 'static', 'js'),
                    path.join(__dirname, 'node_modules'),
                ],
            }
        }   
        
    webpack.dev.js  
    
        const merge = require('webpack-merge');
        const common = require('./webpack.common.js');
            module.exports = merge(common, {
        +     mode: 'development',
        +     devtool: 'source-map',
              devServer: {
                contentBase: './dist',
                  },
            });   