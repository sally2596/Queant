import { createStore } from 'vuex'
import accounts from './modules/accounts'
import todos from './modules/todos'

export default createStore({
  modules: {
    accounts,
    todos
  }
})
