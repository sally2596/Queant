import spring from "@/api/spring"
import router from "@/router"
import axios from 'axios'

export default {
  state: {
    accessToken: '',
    refreshToken: '',
    userInfo: {},
    authError: '',
    emailCheckedStatus: '',
    emailVerifiedStatus: '',
    passwordCheckedStatus: ''
  },
  getters: {
    // token 있으면 true, 없으면 false => 로그인 유무
    isLoggedIn: state => !!state.accessToken,
    userInfo: state => state.userInfo,
    authHeader: state => ({ Authorization: `Token ${state.token}` }),
    authError: state => state.authError,
    emailCheckedStatus: state => state.emailCheckedStatus,
    emailVerifiedStatus: state => state.emailVerifiedStatus,
    passwordCheckedStatus: state => state.passwordCheckedStatus
  },
  mutations: {
    SET_USER_INFO: (state, userInfo) => state.userInfo = userInfo,
    SET_AUTH_ERROR: (state, error) => state.authError = error,
    SET_ACCESS_TOKEN: (state, accessToken) => state.accessToken = accessToken,
    SET_REFRESH_TOKEN: (state, refreshToken) => state.refreshToken = refreshToken,
    SET_EMAIL_CHECKED_STATUS: (state, status) => state.emailCheckedStatus = status,
    SET_EMAIL_VERIFIED_STATUS: (state, status) => state.emailVerifiedStatus = status,
    SET_PASSWORD_CHECKED_STATUS: (state, status) => state.passwordCheckedStatus = status
  },
  actions: {
    passwordChange({ commit }, credentials) {
      axios({
        url: spring.member.password(),
        method: 'put',
        data: {
          email: credentials.email,
          password: credentials.temporaryPassword,
          new_password: credentials.password1
        }
      })
      .then( res => {
        console.log(res)
        alert('비밀번호가 변경되었습니다! 다시 로그인 해주세요.')
        commit('SET_PASSWORD_CHECKED_STATUS', '')
        router.push({ name: 'login'})
      })
      .catch( err => {
        console.log(err)
        commit('SET_PASSWORD_CHECKED_STATUS', err.response.status)
      })
    },
    passwordCheck({ commit }, password) {
      const email = JSON.parse(localStorage.vuex).accounts.userInfo.email
      axios({
        url: spring.member.password(),
        method: 'post',
        data: {
          email: email,
          password: password 
        }
      })
      .then( res => {
        commit('SET_PASSWORD_CHECKED_STATUS', res.status)
      })
      .catch( err => {
        commit('SET_PASSWORD_CHECKED_STATUS', err.response.status)
      })
    },
    sendTemporaryPassword({ commit }, email) {
      axios({
        url: spring.member.password(),
        method: 'patch',
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
    editUserInfo({ dispatch }, credentials) {
      axios({
        url: spring.member.info(),
        method: 'put',
        data: {
          email: credentials.email,
          name: credentials.name,
          gender: credentials.gender,
          birthdate: credentials.birthdate
        }
      })
      .then( res => {
        dispatch('fetchUserInfo', credentials.email)
        router.push({ name: 'home' })
      })
      .catch( err => {
        console.log(err)
      })
    },
    unregister({ dispatch }, email) {
      axios({
        url: spring.member.status(),
        method: 'put',
        data: {
          email: email
        }
      })
      .then( res => {
        console.log(res)
        dispatch('logout')
      })
      .catch( err => {
        console.log(err)
      })
    },
    logout({ commit, dispatch }) {
      dispatch('removeToken')
      commit('SET_USER_INFO', {})
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
    login({ commit, dispatch }, credentials) {
      axios({
        url: spring.member.login(),
        method: 'post',
        data: credentials,
      })
      .then( res => {
        const userCredentials = JSON.parse(res.config.data)
        dispatch('saveAccessToken', res.data.AccessToken)
        dispatch('saveRefreshToken', res.data.RefreshToken)
        dispatch('fetchUserInfo', userCredentials.email)
        router.push({ name: 'home' })
      })
      .catch( err => {
        commit('SET_AUTH_ERROR', err.response.status)
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
          password: credentials.password1
        }
      })
      .then( res => {
        commit('SET_EMAIL_VERIFIED_STATUS', '')
        commit('SET_EMAIL_CHECKED_STATUS', '')
        alert('회원가입이 완료되었습니다! 다시 로그인 해주세요.')
        router.push({ name: 'login' })
      })
      .catch( err => {
        commit('SET_EMAIL_VERIFIED_STATUS', '')
        commit('SET_EMAIL_CHECKED_STATUS', '')
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
    fetchUserInfo({ commit, getters }, email) {
      if (getters.isLoggedIn) {
        axios({
          url: spring.member.info(),
          method: 'post',
          data: {
            email: email
          }
        })
        .then( res => {
          if (window.location.pathname === '/admin') {
            commit('SET_USERS', [res.data])
          } else {
            commit('SET_USER_INFO', res.data)
          }
        })
        .catch( err => {
          console.log(err)
        })
      }
    },
  }
}