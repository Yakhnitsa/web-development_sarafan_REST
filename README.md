Поясненияк уроку 7:
1. установка yarn https://yarnpkg.com/lang/ru/docs/install/#windows-stable
2. установка с установленным NPM npm install -g yarn
3. проверка версии yarn --version
4. инициализация yarn init
5. установка vue и библиотек-  yarn add vue vue-resource
6. установка девелоперских зависимостей: yarn add -D webpack webpack-cli webpack-dev-server babel-loader @babel/core @babel/preset-env vue-loader vue-template-compiler
7. добавление в корне нового файла webpack.common.js


## Пояснение к уроку 8 (Установка WebSocket)
 Устанавливаем зависимости yarn add sockjs-client @stomp/stompjs
 импортируем библиотеки в код ws.js
    import SockJs from 'sockjs-client'
    import Stomp from '@stomp/stompjs'
 
 Меняем log level webpack, в файле webpack.common.js  добавляем настройки логирования:
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
 
     
## 15. Решение циклических ссылок при сериализации JSON
    Аннотации для классов:
    (Вместо целого объекта записывает одно из полей, в данном случае поле ID
    @JsonIdentityInfo(
            generator=ObjectIdGenerators.PropertyGenerator.class,
            property = "id"
    )  
    
    Аннотации для полей
    (Убирает обратную ссылку на объект
    @JsonManagedReference - на поле со ссылкой на другой сериализируемый объект
    private List<Comment> comments = new ArrayList<>();
    
    @JsonBackReference - на поле, на которое ссылается предыдущая аннотация.   
    private Message message;   
    
## 17. Подписки и подписчики:
        @JsonIdentityReference - кастомизирует следующую аннотацию, 
        @JsonIdentityInfo(
                generator=ObjectIdGenerators.PropertyGenerator.class,
                property = "id"
        ) - аннотация записывающая объекты под аннотацией в виде полей property="id" вместо полного объекта.
        
        @EntityGraph(attributePaths = {"subscriptions", "subscribers"})
        Optional<User> findById(String s);   - Аннотация для загрузки ленивых коллекций, позовляет загружать ленивые коллекции при вызове 
        методов репозитория.
        
        Пояснение к роутеру:
        Динамический путь
        export default new VueRouter({
            ....
            { path: '/user/:id?', component: Profile }, :id? - необязательное поле
        Получение параметров роутера в элементе
        const id = this.$route.params.id        
        
### 19. Интеграция логирования с Sentry
    Установка проекта
    yarn add @sentry/browser
    yarn add @sentry/integrations
    
    Интеграция проекта в приложение в main.js
    import Vue from 'vue'
    import * as Sentry from '@sentry/browser';
    import { Vue as VueIntegration } from '@sentry/integrations';
    
    Sentry.init({
        dsn: 'https://339d5fa59e26408e94cea1deb6457398@o381691.ingest.sentry.io/5209398',
        integrations: [new VueIntegration({Vue, attachProps: true})],
    })
    
    Sentry.setUser({
        id: profile && profile.id,
        username: profile && profile.name,
        // email: profile && profile.email,
    });
        
    Интерграция sentry на сервере для spring  https://docs.sentry.io/clients/java/integrations/#spring
        <dependency>
            <groupId>io.sentry</groupId>
            <artifactId>sentry-spring</artifactId>
            <version>1.7.30</version>
        </dependency> 
        
    Создаем конфигурацию в файле logging.properties
    dns=.....
    Создаем евенты:
    Sentry.capture("Some message...");
    
### 20 Сборка всего добра в единый файл для деплоя
   [Подробнее в файле](/readme/WEBPACK_BUILD.md)
   
### 21 Публикация нахер... на хероку
 - Регистрация на сайте https://heroku.com 
 - Настраиваем зависимости для деплоя. Создаем еще один файл application.properties, 
 старый переименовуем на application-dev.properties для сохранности, и будущих запусков при деплое.
 - Устанавливаем heroku на компе:
 https://devcenter.heroku.com/articles/getting-started-with-java#set-up
 !!! Если установить зависимость по дефолту могут возникать ошибки, следует выбирать путь без пробелов и русских символов
 - Логинимся в heroku
        
        heroku login

- Создаем новый проект на heroku
    
     heroku create <название проекта> - если не указать название название назначится самостоятельно

- прописываем переменные окружения

       heroku config:set clientSecret="secret from google"

- Прописываем разрешенные URL в гугле для логина и для перенаправления

- Коммитим все изменения и отправляем на heroku

        git push heroku master       
- Запускаем проект
        heroku ps:scale web=1
- Открываем проект в браузере
        heroku open
        
- Если все это добро работает останавливаем проект
        heroku ps:scale web=0                        