import Vue from 'vue'
import App from 'pages/App.vue'
import VueResouce from 'vue-resource'
import Vuetify from 'vuetify'
import { connect } from 'util/ws'
//Импорт vuetify стилей
import 'vuetify/dist/vuetify.min.css'

if (frontendData.profile) {
    connect();
}

Vue.use(Vuetify)
Vue.use(VueResouce)

new Vue({
    el:'#app',
    render: a => a(App)

})

