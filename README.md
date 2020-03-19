Поясненияк уроку 7:
1. установка yarn https://yarnpkg.com/lang/ru/docs/install/#windows-stable
2. установка с установленным NPM npm install -g yarn
3. проверка версии yarn --version
4. инициализация yarn init
5. установка vue и библиотек-  yarn add vue vue-resource
6. установка девелоперских зависимостей: yarn add -D webpack webpack-cli webpack-dev-server babel-loader @babel/core @babel/preset-env vue-loader vue-template-compiler
7. добавление в корне нового файла webpack.config.js


Пояснение к уроку 8 (Установка WebSocket)
 Устанавливаем зависимости yarn add sockjs-client @stomp/stompjs
 импортируем библиотеки в код ws.js
    import SockJs from 'sockjs-client'
    import Stomp from '@stomp/stompjs'
 
 Меняем log level webpack, в файле webpack.config.js  добавляем настройки логирования:
    devServer: {
         stats: 'errors-only',
         clientLogLevel: 'error'

