import { createStore } from 'vuex'
import accounts from './modules/accounts'
import oauth from './modules/oauth'
import admin from './modules/admin'

import createPersistedState from 'vuex-persistedstate'

export default createStore({
  modules: {
    accounts,
    oauth,
    admin
  },
  plugins: [
    createPersistedState({
      paths: ["accounts"]
    })
  ]
})
