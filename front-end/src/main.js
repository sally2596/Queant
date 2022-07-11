import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router';
import routes from './routes'
import store from './vuex/store'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap"

/* add icons to the library */
Vue.config.productionTip = false

Vue.use(VueRouter)

const router = new VueRouter({
    routes,
});

new Vue({
    router,
    store,
    render: h => h(App),
}).$mount('#app');


