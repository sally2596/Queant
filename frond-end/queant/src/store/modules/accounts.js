import router from "@/router"
import axios from 'axios'

export default {
  state: {},
  getters: {},
  mutations: {},
  actions: {
    login({ commit, dispatch }, credentials) {
      // 통신
      axios({
        url: '',
        method: '',
        data: credentials,
      })
      .then( res => {
        const token = res.data.key
        dispatch('saveToken', token)
        dispatch('fetchCurrentUser')
        router.push({ name: '로그인하고 이동할 views or componenets'})
      })
      .catch( err => {
        commit('SET_AUTH_ERROR', err.response.data)
      })
    },
  },
}