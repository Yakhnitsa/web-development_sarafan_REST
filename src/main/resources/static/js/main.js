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

import {initSenty} from "./api/sentry";
initSenty()

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

