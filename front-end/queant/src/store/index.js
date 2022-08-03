import { createStore } from 'vuex'
import auth from './mo./modules/auth'
import oauth from './modules/oauth'
import admin from './modules/admin'
import bank from './modules/bank'

import createPersistedState from 'vuex-persistedstate'

export default createStore({
  modules: {
    auth,
    oauth,
    admin,
    bank
  },
  plugins: [
    createPersistedState({
      paths: ["auth"]
    })
  ]
})
