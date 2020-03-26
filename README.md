Поясненияк уроку 7:
1. установка yarn https://yarnpkg.com/lang/ru/docs/install/#windows-stable
2. установка с установленным NPM npm install -g yarn
3. проверка версии yarn --version
4. инициализация yarn init
5. установка vue и библиотек-  yarn add vue vue-resource
6. установка девелоперских зависимостей: yarn add -D webpack webpack-cli webpack-dev-server babel-loader @babel/core @babel/preset-env vue-loader vue-template-compiler
7. добавление в корне нового файла webpack.config.js


## Пояснение к уроку 8 (Установка WebSocket)
 Устанавливаем зависимости yarn add sockjs-client @stomp/stompjs
 импортируем библиотеки в код ws.js
    import SockJs from 'sockjs-client'
    import Stomp from '@stomp/stompjs'
 
 Меняем log level webpack, в файле webpack.config.js  добавляем настройки логирования:
    devServer: {
         stats: 'errors-only',
         clientLogLevel: 'error'

## Пояснение к уроку 9 (Добавление Vuetify и Material design)
 - Устанавливаем библиотеки стилей 
    yarn add -D vue-style-loader css-loader
 - Меняем конфигурацию Webpack:
  module -> rules, добавляем правило обработки:
              {
                  test: /\.css$/,
                  use: [
                      'vue-style-loader',
                      'css-loader'
                  ]
              }
              
  - Устанавливаем vuetify
    yarn add vuetify  
  - Импортируем Vuetify в приложение
    main.js:
        import Vuetify from 'vuetify'
        //Импорт vuetify стилей
        import 'vuetify/dist/vuetify.min.css'
        Vue.use(Vuetify)
  - После возникновения ошибки создаем файл настройки Vuetify  plugins/vuetify.js
        import Vue from "vue"
        import Vuetify from 'vuetify'
        
        Vue.use(Vuetify)
        
        export default new Vuetify({
            icons: {
                iconfont: 'md',  // 'mdi' || 'mdiSvg' || 'md' || 'fa' || 'fa4'
            },
            theme: {
                dark: false,
            },
            themes: {
                light: {
                    primary: "#4682b4",
                    secondary: "#b0bec5",
                    accent: "#8c9eff",
                    error: "#b71c1c",
                },
            },
        })
  - Импортируем vuetify в main.js и добавляем к приложению:
        import vuetify from 'plugins/vuetify'
        new Vue({
            el:'#app',
            vuetify, - передаем настроенный экземпляр в приложение
            render: a => a(App)
        
        })          
  - Импортируем стили и настройки в index.html:
        <!--Импорт иконок и стилей для vuetify-->
        <link href="https://fonts.googleapis.com/css?family=Roboto:100,300,400,500,700,900" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/@mdi/font@4.x/css/materialdesignicons.min.css" rel="stylesheet">
        <!--Настройка viewport для корректного отображения на мобильных устройствах-->
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no, minimal-ui">      
        
## Часть 11, настройка vuex
    - Добавление vuex в приоожение
        yarn add vuex
        yarn add @babel/polyfill        
     
    -  Создаем файл настройки vuex в папке store/store.js
    
## Часть 12, Настройка Vue-router
    - Устанавливаем vue router
        yarn add vue-router
    - Настраиваем сервер для работы Vue router:
    Добавляем конфигурацию для того, чтобы все непрописанные пути в приложении отправлялись обратно в браузер, и управлялись с помощью роутера.
    @Configuration
    public class WebMvcConfig implements WebMvcConfigurer {
        @Bean
        public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer(){
            return container ->{
                   /*Настройка перенаправления в случаи ошибки (если запрашиваемый УРЛ не найден)
                   * Насройка для того, чтобы всеми неизвесными запросами занимался роутер.
                   * */
                container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND,"/"));
            };
        }
    }
    - Настраиваем router в файле router/router и импортируем страницы, которые он отображает
    import Vue from 'vue'
    import VueRouter from 'vue-router'
    import MessageList from 'pages/MessageList.vue' - импорт страниц
    import Auth from 'pages/Auth.vue' - импорт страниц
    
    Vue.use(VueRouter)
    
    export default new VueRouter({
        mode:'history', - меняем режим на отображения простых путей, без #
        routes: - настраивание путей
        [
            { path: '/', component: MessageList },
            { path: '/auth', component: Auth },
            { path: '*', component: MessageList }
        ]
    
    })
   
    - Настраиваем главную страницу для отображения страницы роутинга:
    pages/App.vue:
            <v-content>
                <router-view></router-view> - отображение контента роутинга.
    
            </v-content>
 
        