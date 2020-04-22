import Vue from 'vue'
import vuetify from 'api/vuetify'
import '@babel/polyfill'
import 'api/resource'
import router from 'router/router'
import store from 'store/store'
import App from 'pages/App.vue'
import { connect } from 'util/ws'

import 'vuetify/dist/vuetify.min.css' //Импорт vuetify стилей

import * as Sentry from '@sentry/browser'
import { Vue as VueIntegration } from '@sentry/integrations'

Sentry.init({
    dsn: 'https://339d5fa59e26408e94cea1deb6457398@o381691.ingest.sentry.io/5209398',
    integrations: [new VueIntegration({Vue, attachProps: true})],
})

// Настройка логирования для пользователя
Sentry.setUser({
    id: profile && profile.id,
    username: profile && profile.name,
    email: profile && profile.email,
});

// Sentry.captureException(new Error("First error..."));

if (profile) {
    connect();
}

new Vue({
    el:'#app',
    vuetify,
    router,
    store: store,
    render: a => a(App)

})

