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
    emailVerifiedStatus: '',
  },
  getters: {
    // token 있으면 true, 없으면 false => 로그인 유무
    isLoggedIn: state => !!state.accessToken,
    currentUser: state => state.currentUser,
    authHeader: state => ({ Authorization: `Token ${state.token}` }),
    emailCheckedStatus: state => state.emailCheckedStatus,
    emailVerifiedStatus: state => state.emailVerifiedStatus,
  },
  mutations: {
    SET_CURRENT_USER: (state, userInfo) => state.currentUser = userInfo,
    SET_AUTH_ERROR: (state, error) => state.authError = error,
    SET_ACCESS_TOKEN: (state, accessToken) => state.accessToken = accessToken,
    SET_REFRESH_TOKEN: (state, refreshToken) => state.refreshToken = refreshToken,
    SET_EMAIL_CHECKED_STATUS: (state, status) => state.emailCheckedStatus = status,
    SET_EMAIL_VERIFIED_STATUS: (state, status) => state.emailVerifiedStatus = status,
  },
  actions: {
    passwordCheck({ commit }, password) {
      const email = JSON.parse(localStorage.vuex).accounts.currentUser.email
      axios({
        url: spring.member.passwordcheck(),
        method: 'post',
        data: {
          email: email,
          password: password 
        }
      })
      .then( res => {
        console.log(res)
      })
      .catch( err => {
        console.log(err)
      })
    },
    logout({ commit, dispatch }) {
      dispatch('removeToken')
      commit('SET_CURRENT_USER', {})
      router.push({ name: 'home' })
    },
    removeToken({ commit }) {
      commit('SET_ACCESS_TOKEN', '')
      commit('SET_REFRESH_TOKEN', '')
      // commit('SET_AUTH_ERROR', null)
      localStorage.removeItem('vuex')
      localStorage.setItem('accessToken', '')
      localStorage.setItem('refreshToken', '')
    },
    // 일반 로그인
    login({ dispatch }, credentials) {
      axios({
        url: spring.member.login(),
        method: 'post',
        data: credentials,
      })
      .then( res => {
        const userCredentials = JSON.parse(res.config.data)
        dispatch('saveAccessToken', res.data.AccessToken)
        dispatch('saveRefreshToken', res.data.RefreshToken)
        dispatch('fetchCurrentUser', userCredentials)
        router.push({ name: 'home'})
      })
      .catch( err => {
        console.log(err)
        // commit('SET_AUTH_ERROR', err.response.data)
      })
    },
    // 회원가입
    register({ commit }, credentials) {
      axios({
        url: spring.member.register(),
        method: 'post',
        data: {
          birthdate: credentials.birthdate,
          email: credentials.email,
          gender: credentials.gender,
          name: credentials.name,
          password: credentials.password
        }
      })
      .then( res => {
        commit('SET_EMAIL_VERIFIED_STATUS', '')
        commit('SET_EMAIL_CHECKED_STATUS', '')
        alert('회원가입이 완료되었습니다! 다시 로그인해주세요.')
        router.push({ name: 'login' })
      })
      .catch( err => {
        console.log(err)
        commit('SET_EMAIL_VERIFIED_STATUS', '')
        commit('SET_EMAIL_CHECKED_STATUS', '')
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
        commit('SET_EMAIL_CHECKED_STATUS', res.status)
      })
      .catch( err => {
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
        commit('SET_EMAIL_VERIFIED_STATUS', res.status)
      })
      .catch( err => {
        commit('SET_EMAIL_VERIFIED_STATUS', err.response.status)
      })
    },

    saveAccessToken({ commit }, accessToken) {
      commit('SET_ACCESS_TOKEN', accessToken)
      localStorage.setItem('accessToken', accessToken)
    },
    saveRefreshToken({ commit }, refreshToken) {
      commit('SET_REFRESH_TOKEN', refreshToken)
      localStorage.setItem('refreshToken', refreshToken)
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
    },
  }
}