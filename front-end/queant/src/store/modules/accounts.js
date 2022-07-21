import spring from "@/api/spring"
import router from "@/router"
import axios from 'axios'

export default {
  state: {
    accessToken: '',
    refreshToken: '',
    currentUser: {},
    authError: null,
    emailCheckedStatus: '',
    isEmailVerified: '',
    isCompletedRegister: null
  },
  getters: {
    // token 있으면 true, 없으면 false => 로그인 유무
    isLoggedIn: state => !!state.accessToken,
    currentUser: state => state.currentUser,
    authHeader: state => ({ Authorization: `Token ${state.token}` }),
    emailCheckedStatus: state => state.emailCheckedStatus,
    isEmailVerified: state => state.isEmailVerified,
    isCompletedRegister: state => state.isCompletedRegister
  },
  mutations: {
    SET_CURRENT_USER: (state, userInfo) => state.currentUser = userInfo,
    SET_AUTH_ERROR: (state, error) => state.authError = error,
    SET_TOKEN: (state, token) => {
      state.accessToken = token.accessToken,
      state.refreshToken = token.refreshToken
    },
    SET_EMAIL_CHECKED_STATUS: (state, status) => state.emailCheckedStatus = status,
    SET_IS_EMAIL_VERIFIED: (state, status) => state.isEmailVerified = status,
    SET_IS_COMPLETED_REGISTER: (state, bool) => state.isCompletedRegister = bool
  },
  actions: {
    logout({ commit, dispatch }) {
      dispatch('removeToken')
      commit('SET_CURRENT_USER', {})
      router.push({ name: 'login' })
    },
    removeToken({ commit }) {
      const nullToken = {
        accessToken: '',
        refreshToken: ''
      }
      commit('SET_TOKEN', nullToken)
      // commit('SET_AUTH_ERROR', null)
      localStorage.removeItem('vuex')
      localStorage.setItem('accessToken', '')
      localStorage.setItem('refreshToken', '')
    },
    // LOGIN
    // 일반 로그인
    login({ commit, dispatch }, credentials) {
      axios({
        url: spring.member.login(),
        method: 'post',
        data: credentials,
      })
      .then( res => {
        const token = {
          accessToken: res.data.AccessToken,
          refreshToken: res.data.RefreshToken
        }
        const userCredentials = JSON.parse(res.config.data)
        dispatch('saveToken', token)
        dispatch('fetchCurrentUser', userCredentials)
        router.push({ name: 'home'})
      })
      .catch( err => {
        console.log(err)
        // commit('SET_AUTH_ERROR', err.response.data)
      })
    },

    // 회원가입
    register({ commit, dispatch }, credentials) {
      axios({
        url: spring.member.register(),
        method: 'post',
        data: credentials
      })
      .then( res => {
        commit('SET_IS_COMPLETED_REGISTER', true)
        // const token = res.data.key
        // dispatch('saveToken', token)
        // dispatch('fetchCurrentUser')
        // router.push({ name: 'movieList' })
      })
      .catch( err => {
        commit('SET_IS_COMPLETED_REGISTER', false)
        // commit('SET_AUTH_ERROR', err.response.data)
      })
    },

    // 회원가입 - 이메일 중복체크
    emailCheck({ commit }, email) {
      axios({
        url: spring.member.emailcheck(),
        method: 'post',
        data: {
          email: email
        }
      })
      .then( res => {
        console.log(res)
        commit('SET_EMAIL_CHECKED_STATUS', res.status)
      })
      .catch( err => {
        console.log(err)
        commit('SET_EMAIL_CHECKED_STATUS', err.response.status)
      })
    },

    emailVerify({ commit }, code) {
      axios({
        url: spring.member.emailverify(),
        method: 'post',
        data: {
          code: code
        }
      })
      .then( res => {
        commit('SET_IS_EMAIL_VERIFIED', res.status)
      })
      .catch( err => {
        commit('SET_IS_EMAIL_VERIFIED', err.response.status)
      })
    },

    saveToken({ commit }, token) {
      commit('SET_TOKEN', token)
      localStorage.setItem('accessToken', token.accessToken)
      localStorage.setItem('refreshToken', token.refreshToken)
    },
    fetchCurrentUser({ commit, getters }, userCredentials) {
      if (getters.isLoggedIn) {
        axios({
          url: spring.member.info(),
          method: 'post',
          data: {
            email: userCredentials.email
          }
        })
        .then( res => {
          commit('SET_CURRENT_USER', res.data)
        })
        .catch( err => {
          console.log(err)
        })
      }
    }
  }
}