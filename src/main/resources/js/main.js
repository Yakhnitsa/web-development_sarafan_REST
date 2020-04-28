import Vue from 'vue'
import vuetify from 'api/vuetify'
import '@babel/polyfill'
import 'api/resource'
import router from 'js/router/router'
import store from 'js/store/store'
import App from 'js/pages/App.vue'
import { connect } from 'js/util/ws'

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

