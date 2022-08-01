import { createStore } from 'vuex'
import accounts from './modules/accounts'
import oauth from './modules/oauth'
import admin from './modules/admin'
import bank from './modules/bank'

import createPersistedState from 'vuex-persistedstate'

export default createStore({
  modules: {
    accounts,
    oauth,
    admin,
    bank
  },
  plugins: [
    createPersistedState({
      paths: ["accounts"]
    })
  ]
})
