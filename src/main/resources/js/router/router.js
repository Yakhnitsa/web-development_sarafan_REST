import Vue from 'vue'
import VueRouter from 'vue-router'
import MessageList from 'js/pages/MessageList.vue'
import Auth from 'js/pages/Auth.vue'
import Profile from 'js/pages/Profile.vue'
import Subscriptions from 'js/pages/Subscriptions.vue'

Vue.use(VueRouter)

export default new VueRouter({
    mode:'history',
    routes:
    [
        { path: '/', component: MessageList },
        { path: '/auth', component: Auth },
        { path: '/user/:id?', component: Profile },
        { path: '/subscriptions/:id?', component: Subscriptions },

        { path: '*', component: MessageList }
    ]

})