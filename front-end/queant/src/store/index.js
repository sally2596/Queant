import { createStore } from 'vuex'
import accounts from './modules/accounts'
import oauth from './modules/oauth'
import todos from './modules/todos'

import createPersistedState from 'vuex-persistedstate'

export default createStore({
  modules: {
    accounts,
    oauth,
    todos
  },
  plugins: [
    createPersistedState({
      paths: ["accounts"]
    })
  ]
})
