import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

// font awesome
import { library } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { fas } from '@fortawesome/free-solid-svg-icons'
import { far } from '@fortawesome/free-regular-svg-icons'
import { fab } from '@fortawesome/free-brands-svg-icons'

createApp(App).use(store).use(router).mount('#app')


library.add(fas, far, fab)
Vue.component('font-awesome-icon', FontAwesomeIcon)
