import Vue from 'vue'
import VueResouce from 'vue-resource'
import App from 'pages/App.vue'
import { connect } from 'util/ws'
import vuetify from 'plugins/vuetify'
import 'vuetify/dist/vuetify.min.css' //Импорт vuetify стилей

if (frontendData.profile) {
    connect();
}

// Vue.use(Vuetify)
Vue.use(VueResouce)

new Vue({
    el:'#app',
    vuetify,
    render: a => a(App)

})

