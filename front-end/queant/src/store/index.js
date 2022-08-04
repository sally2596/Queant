import { createStore } from 'vuex'
import auth from './modules/auth'
import oauth from './modules/oauth'
import admin from './modules/admin'
import bank from './modules/bank'
import product from './modules/product'

import createPersistedState from 'vuex-persistedstate'

export default createStore({
  modules: {
    auth,
    oauth,
    admin,
    bank,
    product
  },
  plugins: [
    createPersistedState({
      paths: ['auth']
    })
  ]
})
