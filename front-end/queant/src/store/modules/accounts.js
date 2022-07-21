import spring from "@/api/spring"
import router from "@/router"
import axios from 'axios'

export default {
  state: {
    token: '',
    currentUser: {},
    authError: null,
    emailCheckedStatus: '',
    isEmailVerified: '',
    isCompletedRegister: null
  },
  getters: {
    // token 있으면 true, 없으면 false => 로그인 유무
    isLoggedIn: state => !!state.token,
    authHeader: state => ({ Authorization: `Token ${state.token}` }),
    emailCheckedStatus: state => state.emailCheckedStatus,
    isEmailVerified: state => state.isEmailVerified,
    isCompletedRegister: state => state.isCompletedRegister
  },
  mutations: {
    SET_CURRENT_USER: (state, user) => state.currentUser = user,
    SET_AUTH_ERROR: (state, error) => state.authError = error,
    SET_TOKEN: (state, token) => state.token = token,
    SET_EMAIL_CHECKED_STATUS: (state, status) => state.emailCheckedStatus = status,
    SET_IS_EMAIL_VERIFIED: (state, status) => state.isEmailVerified = status,
    SET_IS_COMPLETED_REGISTER: (state, bool) => state.isCompletedRegister = bool
  },
  actions: {
    naverLogin({ commit, dispatch }, naverAccessToken) {
      dispatch('saveToken', naverAccessToken)
      router.push('/')
    },
    logout({ commit, dispatch }) {
      dispatch('removeToken')
      commit('SET_CURRENT_USER', {})
      router.push('/')
    },
    // LOGIN
    // 일반 로그인
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

    // 카카오 로그인
    kakaoLogin() {

      window.Kakao.init(process.env.VUE_APP_KAKAO)

      if (window.Kakao.Auth.getAccessToken()) {
        window.Kakao.API.request({
          url: '/v1/user/unlink',
          success(response) {
            console.log(response)
          },
          fail(error) {
            console.log(error)
          },
        })
        window.Kakao.Auth.setAccessToken(undefined)
      }


      window.Kakao.Auth.login({
        success() {
          window.Kakao.API.request({
            url: '/v2/user/me',
            data: {
              property_keys: ["kakao_account.email"]
            },
            success: async function (response) {
              console.log(response);
            },
            fail(error) {
              console.log(error)
            },
          })
        },
        fail(error) {
          console.log(error)
        },
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
        commit('SET_IS_EMAIL_VERIFIED', res.status)
      })
      .catch( err => {
        commit('SET_IS_EMAIL_VERIFIED', err.response.status)
      })
    },

    saveToken({ commit }, token) {
      commit('SET_TOKEN', token)
      localStorage.setItem('token', token)
    },
    removeToken({ commit }) {
      commit('SET_TOKEN', '')
      commit('SET_AUTH_ERROR', null)
      localStorage.removeItem('vuex')
      localStorage.setItem('token', '')
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
  }
}