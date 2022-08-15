import spring from '@/api/spring'
import router from '@/router'
import axios from 'axios'

export default {
  state: {
    accessToken: '',
    refreshToken: '',
    userInfo: {},
    emailCheckedStatus: '',
    emailVerifiedStatus: '',
    passwordCheckedStatus: '',
    authError: '',
    isAdmin: false,
    accessTokenExpiryTime: null,
    refreshTokenExpiryTime: null
  },
  getters: {
    isLoggedIn: state => !!state.accessToken,
    isAdmin: state => state.isAdmin,
    userInfo: state => state.userInfo,
    authError: state => state.authError,
    authHeader: state => ({ 'X-AUTH-TOKEN': state.accessToken }),
    emailCheckedStatus: state => state.emailCheckedStatus,
    emailVerifiedStatus: state => state.emailVerifiedStatus,
    passwordCheckedStatus: state => state.passwordCheckedStatus,
    // 액세스토큰 만료 30초 전인가? true/false
    isAccessTokenExpired: state => state.accessTokenExpiryTime - new Date().getTime() < 30000,
    // 리프레쉬토큰 만료 30초 전인가? true/false
    isRefreshTokenExpired: state => state.refreshTokenExpiryTime - new Date().getTime() < 30000
  },
  mutations: {
    SET_USER_INFO: (state, userInfo) => state.userInfo = userInfo,
    SET_AUTH_ERROR: (state, error) => state.authError = error,
    SET_ACCESS_TOKEN: (state, accessToken) => state.accessToken = accessToken,
    SET_REFRESH_TOKEN: (state, refreshToken) => state.refreshToken = refreshToken,
    SET_EMAIL_CHECKED_STATUS: (state, status) => state.emailCheckedStatus = status,
    SET_EMAIL_VERIFIED_STATUS: (state, status) => state.emailVerifiedStatus = status,
    SET_PASSWORD_CHECKED_STATUS: (state, status) => state.passwordCheckedStatus = status,
    SET_IS_ADMIN: (state, bool) => state.isAdmin = bool,
    SET_ACCESS_TOKEN_EXPIRY_TIME: (state, time) => state.accessTokenExpiryTime = time,
    SET_REFRESH_TOKEN_EXPIRY_TIME: (state, time) => state.refreshTokenExpiryTime = time
  },
  actions: {
    // 회원가입 & 로그인
    // 1. 이메일 중복체크 검사 요청
    emailCheck({ commit }, email) {
      commit('SET_EMAIL_CHECKED_STATUS', '')
      axios({
        url: spring.member.emailcheck(),
        method: 'post',
        data: {
          email: email
        }
      })
      .then(res => {
        console.log('이메일 중복 검사에 성공했습니다.')
        commit('SET_EMAIL_CHECKED_STATUS', res.status)
      })
      .catch( err => {
        console.log('이메일 중복 검사에 실패했습니다.')
        commit('SET_EMAIL_CHECKED_STATUS', err.response.status)
      })
    },
    // 2. 이메일 인증번호 검증 요청
    emailVerify({ commit }, code) {
      axios({
        url: spring.member.emailverify(),
        method: 'post',
        data: {
          code: code
        }
      })
      .then(res => {
        console.log('이메일 인증번호 검사에 성공했습니다.')
        commit('SET_EMAIL_VERIFIED_STATUS', res.status)
      })
      .catch(err => {
        console.log('이메일 인증번호 검사에 실패했습니다.')
        commit('SET_EMAIL_VERIFIED_STATUS', err.response.status)
      })
    },
    // 3. 회원가입 요청
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
      .then(res => {
        console.log('회원가입 되었습니다. 다시 로그인 해주세요.')
        commit('SET_EMAIL_VERIFIED_STATUS', '')
        commit('SET_EMAIL_CHECKED_STATUS', '')
        alert('회원가입이 완료되었습니다! 다시 로그인 해주세요.')
        router.push({ name: 'login' })
      })
      .catch(err => {
        commit('SET_EMAIL_VERIFIED_STATUS', '')
        commit('SET_EMAIL_CHECKED_STATUS', '')
      })
    },
    // 4. 로그인 요청 (액세스, 리프레쉬 토큰 발급)
    login({ dispatch, commit }, { credentials, nextPath }) {
      axios({
        url: spring.member.login(),
        method: 'post',
        data: credentials,
      })
      .then(res => {
        console.log('로그인 되었습니다.')
        const userCredentials = JSON.parse(res.config.data)
        dispatch('saveAccessToken', res.data.AccessToken)
        dispatch('saveRefreshToken', res.data.RefreshToken)
        dispatch('fetchUserInfo', userCredentials.email)
        router.push(nextPath)
      })
      .catch(err => {
        commit('SET_AUTH_ERROR', err.response.status)
      })
    },
    // 5. 사용자 정보 요청
    fetchUserInfo({ dispatch, commit, getters }, email) {
      // 리프레쉬나 액세스토큰이 만료됐으면 재발급 요청
      if (getters.isRefreshTokenExpired || getters.isAccessTokenExpired)
        dispatch('updateAccessToken')

      if (getters.isLoggedIn) {
        axios({
          url: spring.member.info(),
          method: 'post',
          headers: getters.authHeader,
          data: {
            email: email
          }
        })
        .then(res => {
          console.log('유저 정보를 가져오는데 성공했습니다.')
          if (window.location.pathname === '/admin') {
            console.log('가져온 유저 정보를 관리자 페이지에서 검색 결과로 확인할 수 있습니다.')
            commit('SET_USERS', [res.data])
          } else {
            console.log('가져온 유저 정보를 현재 유저 정보로 저장합니다.')
            let isAdmin = res.data.role_set.length === 3
            commit('SET_IS_ADMIN', isAdmin)
            commit('SET_USER_INFO', res.data)
          }
        })
        .catch(err => {
          console.log(err)
          console.log('유저 정보를 가져오는데 실패했습니다.')
        })
      }
    },
    // 토큰 저장 & 재발급
    // 1. 토큰 저장
    saveAccessToken({ commit }, accessToken) {
      console.log('액세스 토큰을 저장합니다.')
      commit('SET_ACCESS_TOKEN', accessToken)
      localStorage.setItem('accessToken', accessToken)
      // 액세스 토큰 만료시간 구하기
      const now = new Date().getTime()
      commit('SET_ACCESS_TOKEN_EXPIRY_TIME', now + 1800000)
    },
    saveRefreshToken({ commit }, refreshToken) {
      console.log('리프레쉬 토큰을 저장합니다.')
      commit('SET_REFRESH_TOKEN', refreshToken)
      localStorage.setItem('refreshToken', refreshToken)
      // 리프레쉬 토큰 만료시간 구하기
      const now = new Date().getTime()
      commit('SET_REFRESH_TOKEN_EXPIRY_TIME', now + 3600000)
    },
    // 2 액세스토큰 재발급
    updateAccessToken({ dispatch, commit, state }) {
      axios({
        url: spring.member.refreshtoken(),
        method: 'post',
        data: {
          email: state.userInfo.email,
          refreshtoken: state.refreshToken
        }
      })
      .then(res => {
        console.log(res)
        const now = new Date().getTime()
        dispatch('saveAccessToken', res.data)
        commit('SET_ACCESS_TOKEN_EXPIRY_TIME', now + 60000)
      })
      .catch(err => {
        console.log(err)
        if (err.response.status === 406) {
          alert('다시 로그인 해주세요.')
          dispatch('logout')
        }
      })
    },
    // 회원정보
    // 1. 회원정보 수정을 위한 비밀번호 검증 요청
    passwordCheck({ dispatch, commit, getters }, password) {
      // 리프레쉬나 액세스토큰이 만료됐으면 재발급 요청
      if (getters.isRefreshTokenExpired || getters.isAccessTokenExpired)
        dispatch('updateAccessToken')

      const email = JSON.parse(localStorage.vuex).auth.userInfo.email
      axios({
        url: spring.member.password(),
        method: 'post',
        headers: getters.authHeader,
        data: {
          email: email,
          password: password 
        }
      })
      .then(res => {
        commit('SET_PASSWORD_CHECKED_STATUS', res.status)
      })
      .catch(err => {
        commit('SET_PASSWORD_CHECKED_STATUS', err.response.status)
      })
    },
    // 2. 회원정보 수정 요청
    editUserInfo({ dispatch, getters }, credentials) {
      console.log(typeof(credentials.birthdate))
      // 리프레쉬나 액세스토큰이 만료됐으면 재발급 요청
      if (getters.isRefreshTokenExpired || getters.isAccessTokenExpired)
        dispatch('updateAccessToken')
      
      axios({
        url: spring.member.info(),
        method: 'put',
        headers: getters.authHeader,
        data: {
          email: credentials.email,
          name: credentials.name,
          gender: credentials.gender,
          birthdate: credentials.birthdate
        }
      })
      .then(res => {
        console.log(res)
        dispatch('fetchUserInfo', credentials.email)
        router.push({ name: 'home' })
      })
      .catch(err => {
        console.log(err)
      })
    },
    // 3. 비밀번호 잊은 사용자가 이메일을 입력하면 해당 이메일로 임시 비밀번호 전송해줄 것을 요청
    sendTemporaryPassword({ commit }, email) {
      axios({
        url: spring.member.password(),
        method: 'patch',
        data: {
          email: email
        }
      })
      .then(res => {
        console.log('적어주신 이메일로 임시 비밀번호를 전송했습니다.')
        alert(`${email}로 임시 비밀번호를 전송했습니다.`)
        commit('SET_EMAIL_CHECKED_STATUS', res.status)
      })
      .catch(err => {
        console.log('가입된 이메일이 아니므로 임시 비밀번호 전송에 실패했습니다.')
        commit('SET_EMAIL_CHECKED_STATUS', err.response.status)
      })
    },
    // 4. 임시 비밀번호를 검증하고 새로운 비밀번호 설정 요청
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
      .then(res => {
        console.log('비밀번호가 변경되었습니다. 다시 로그인 해주세요.')
        alert('비밀번호가 변경되었습니다! 다시 로그인 해주세요.')
        commit('SET_PASSWORD_CHECKED_STATUS', '')
        router.push({ name: 'login'})
      })
      .catch(err => {
        console.log('임시 비밀번호를 확인해주세요.')
        commit('SET_PASSWORD_CHECKED_STATUS', err.response.status)
      })
    },
    // 회원탈퇴 & 로그아웃
    // 1. 회원탈퇴 요청 (계정 비활성화)
    unregister({ dispatch, getters }, email) {
      axios({
        url: spring.member.status(),
        method: 'put',
        headers: getters.authHeader,
        data: {
          email: email
        }
      })
      .then(res => {
        console.log(res)
        dispatch('logout')
      })
      .catch( err => {
        console.log(err)
      })
    },
    // 2. 로그아웃
    logout({ commit, dispatch }) {
      router.push({ name: 'home' })
      dispatch('removeToken')
      commit('SET_IS_ADMIN', false)
      commit('SET_USER_INFO', {})
    },
    // 3. 스토어와 로컬스토리지에 저장된 토큰 정보 삭제
    removeToken({ commit }) {
      commit('SET_ACCESS_TOKEN', '')
      commit('SET_REFRESH_TOKEN', '')
      localStorage.removeItem('vuex')
      localStorage.setItem('accessToken', '')
      localStorage.setItem('refreshToken', '')
    }
  }
}