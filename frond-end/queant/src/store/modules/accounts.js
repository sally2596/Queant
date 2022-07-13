import router from "@/router"
import axios from 'axios'

export default {
  state: {
    token: '',
    currentUser: {},
    authError: null,
  },
  getters: {
    // token 있으면 true, 없으면 false => 로그인 유무
    isLoggedIn: state => !!state.token,
    authHeader: state => ({ Authorization: `Token ${state.token}` })
  },
  mutations: {
    SET_CURRENT_USER: (state, user) => state.currentUser = user,
    SET_AUTH_ERROR: (state, error) => state.authError = error,
  },
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
    register({ commit, dispatch }, credentials) {
      // 회원가입
      // 성공하면 응답 토큰 저장, 현재 사용자 정보 받고 메인 페이지 이동
      // 실패하면 에러 메세지 표시
      axios({
        url: '',
        method: '',
        data: credentials,
      })
      .then( res => {
        const token = res.data.key
        dispatch('saveToken', token)
        dispatch('fetchCurrentUser')
        router.push({ name: 'movieList' })
      })
      .catch( err => {
        commit('SET_AUTH_ERROR', err.response.data)
      })
    },
    saveToken({ commit }, token) {
      commit('SET_TOKEN', token)
      localStorage.setItem('token', token)
    },
    fetchCurrentUser({ commit, getters, dispatch }) {
      if (getters.isLoggedIn) {
        axios({
          url: '',
          method: '',
          headers: getters.authHeader
        })
        .then( res => {
          commit('SET_CURRENT_USER', res.data)
        })
        .catch( err => {
          if (err.response.data === 401) {
            dispatch('removeToken')
            router.push({ name: 'login' })
          }
        })
      }
    }
  },
}