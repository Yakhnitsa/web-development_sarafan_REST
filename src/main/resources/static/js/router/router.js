import Vue from 'vue'
import VueRouter from 'vue-router'
import MessageList from 'pages/MessageList.vue'
import Auth from 'pages/Auth.vue'

Vue.use(VueRouter)

export default new VueRouter({
    mode:'history',
    routes:
    [
        { path: '/', component: MessageList },
        { path: '/auth', component: Auth },
        { path: '*', component: MessageList }
    ]

})