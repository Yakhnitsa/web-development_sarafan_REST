#20 Сборка всего добра в единый файл для деплоя
   [Подробности сборки](https://webpack.js.org/guides/production/)
##Рефакторинг путей
  - Переносим все файлы из resources/static/js в корень resources/js  

  - Устанавливаем приложение сборки 
    yarn add -D webpack-merge
       
  - Переименовуем файлы
    - webpack.config.js -> webpack.common.js  
    
## Создание доп файлов и разделение путей    
  - Создаем дополнительные файлы конфигурации
     + webpack.dev.js
     + webpack.prod.js  
  - Разделяем настройки по файлам        
    [webpack.common.js](../webpack.common.js)
  
        const path = require('path');
        const VueLoaderPlugin = require('vue-loader/lib/plugin');
        module.exports = {
        -    mode: 'development',
        -    devtool: 'source-map',
        -    entry: path.join(__dirname, 'src', 'main', 'resources', 'static', 'js', 'main.js'),
        -    devServer: {
        -        contentBase: './dist',
        -        compress: true,
        -        port: 8000,
        -        allowedHosts: [
        -            'localhost:8080'
        -        ],
        -        stats: 'errors-only',
        -        clientLogLevel: 'error'
        -    },
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
        
      
   [webpack.dev.js](../webpack.dev.js)
    
        const merge = require('webpack-merge');
        const common = require('./webpack.common.js');
            module.exports = merge(common, {
        +     mode: 'development',
        +     devtool: 'source-map',
        +    devServer: {
        +        contentBase: './dist',
        +        compress: true,
        +        port: 8000,
        +        allowedHosts: [
        +            'localhost:8080'
        +        ],
        +        stats: 'errors-only',
        +        clientLogLevel: 'error'
        +   },
        +    });   
            
   [webpack.prod.js](../webpack.prod.js)    
   
    const merge = require('webpack-merge');
    const common = require('./webpack.common.js');
        module.exports = merge(common, {
          mode: 'production',
        });    
        
  Убираем директорию 'static' из common файла   
  [webpack.common.js](../webpack.common.js)   
  
    - entry: path.join(__dirname, 'src', 'main', 'resources', 'static', 'js', 'main.js'),  - убираем путь static
    + entry: path.join(__dirname, 'src', 'main', 'resources', 'js', 'main.js'),  
    
    resolve: {
            modules: [
    -            path.join(__dirname, 'src', 'main', 'resources','static','js'),
    +            path.join(__dirname, 'src', 'main', 'resources', 'js'),
                path.join(__dirname, 'node_modules'),
            ],
        }
    
## Установка сборщика мусора и настройка файла продакшена
       yarn add -D clean-webpack-plugin
   Добавляем плагин в файл [webpack.prod.js](../webpack.prod.js)  
    
        const { CleanWebpackPlugin } = require('clean-webpack-plugin');     
   
   Добавляем плагин в секцию плагинов
          
       plugins: [
           new CleanWebpackPlugin()
       ],    
   Добавляем секцию output файла (сборка в файл main.js + раздешенные пути)

        output: {
            filename: 'main.js',
            path: path.resolve(__dirname, 'src', 'main', 'resources', 'static','js'),
        },            

## Настройка файла запуста
   в файле [package.json](../package.json)  
   добавляем настройки запуска приложения:
   
     "scripts": {
       "start": "webpack-dev-server --open --config webpack.dev.js",
       "build": "webpack --config webpack.prod.js"
     },  
     
## Долгая и нудная дрочелыга по изменению путей: 

## Добавление плагина для деплоя в maven:
   Добавляем плагин в секцию plugins перед плагином сборки
    
    <build>
        <plugins>    
                <plugin>
                    <groupId>com.github.eirslett</groupId>
                    <artifactId>frontend-maven-plugin</artifactId>
                    <version>1.6</version> 
    
   Прописываем конфигурацию в плагине:
   
    <plugin>
    .....
     <configuration>
         <workingDirectory>target/frontend</workingDirectory>
         <environmentVariables>
        <!-- Avoid certificates issues, needed behind a corporate proxy -->
         <NODE_TLS_REJECT_UNAUTHORIZED>0</NODE_TLS_REJECT_UNAUTHORIZED>
         </environmentVariables>

         <nodeVersion>v12.13.0</nodeVersion>

         <!--<npmVersion>2.15.9</npmVersion>-->

         <yarnVersion>v1.21.0</yarnVersion>
     </configuration>
   
   Прописываем этапы сборки в плагине:
   
    <plugin>
    ....
    <executions>
      <execution>
          <id>install node and yarn</id>
          <goals>
              <goal>install-node-and-yarn</goal>
          </goals>
      </execution>
        
      <execution>
          <id>yarn install</id>
          <goals>
              <goal>yarn</goal>
          </goals>
      </execution>
        
      <execution>
          <id>yarn build</id>
          <goals>
              <goal>yarn</goal>
          </goals>
          <configuration>
              <arguments>build</arguments>
          </configuration>
      </execution>     
      
      
   Собирае проект с помощью maven       
   
    maven install 