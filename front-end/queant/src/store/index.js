import { createStore } from 'vuex'
import accounts from './modules/accounts'
import oauth from './modules/oauth'
import todos from './modules/todos'

export default createStore({
  modules: {
    accounts,
    oauth,
    todos
  }
})
