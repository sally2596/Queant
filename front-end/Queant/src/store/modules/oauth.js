import spring from '@/api/spring'
import router from '@/router'
import axios from 'axios'

export default {
  state: {},
  getters: {},
  mutations: {},
  actions: {
    // 구글 로그인
    // 1. 회원이 로그인 화면에서 구글 로고를 클릭했을 때
    googleLogin() {
      axios({
        url: spring.social.google(),
        method: 'get'
      })
      .then((res) => {
        window.location.href = res.data
      })
      .catch((err) => {
        console.log(err)
      })
    },
    // 2. googleLogin() 요청으로 받은 화면에서 로그인 버튼을 누르면 백엔드에게 인가코드를 넘긴다.
    getGoogleAuthorizationCode({ dispatch }, authorizationCode) {
      axios({
        url: spring.social.googlelogin(),
        method: 'get',
        params: {
          code: authorizationCode
        }
      })
      .then((res) => {
        dispatch('saveAccessToken', res.data.AccessToken)
        dispatch('saveRefreshToken', res.data.RefreshToken)
        dispatch('fetchUserInfo', res.data.email)
        router.push({ name: 'home' })
      })
      .catch((err) => {
        if (err.response.status === 409)
          alert('Queant 또는 다른 소셜 계정으로 이미 가입된 계정입니다.')
        else if (err.response.status === 423)
          alert('비활성화 계정입니다.')
        router.push({ name: 'login' })
      })
    },
    // 카카오 로그인
    kakaoLogin() {
      axios({
        url: spring.social.kakao(),
        method: 'get'
      })
      .then((res) => {
        window.location.href = res.data
      })
      .catch((err) => {
        console.log(err)
      })
    },
    
    getKakaoAuthorizationCode({ dispatch }, authorizationCode) {
      axios({
        url: spring.social.kakaologin(),
        method: 'get',
        params: {
          code: authorizationCode
        }
      })
      .then( res => {
        dispatch('saveAccessToken', res.data.AccessToken)
        dispatch('saveRefreshToken', res.data.RefreshToken)
        dispatch('fetchUserInfo', res.data.email)
        router.push({ name: 'home' })
      })
      .catch( err => {
        if (err.response.status === 409)
          alert('Queant 또는 다른 소셜 계정으로 이미 가입된 계정입니다.')
        else if (err.response.status === 423)
          alert('비활성화 계정입니다.')
        router.push({ name: 'login' })
      })
    },
    // 네이버 로그인
    naverLogin() {
      axios({
        url: spring.social.naver(),
        method: 'get'
      })
      .then((res) => {
        window.location.href = res.data
      })
      .catch((err) => {
        console.log(err)
      })
    },

    getNaverAuthorizationCode({ dispatch }, query) {
      axios({
        url: spring.social.naverlogin(),
        method: 'get',
        params: {
          code: query.code,
          state: query.state
        }
      })
      .then( res => {
        dispatch('saveAccessToken', res.data.AccessToken)
        dispatch('saveRefreshToken', res.data.RefreshToken)
        dispatch('fetchUserInfo', res.data.email)
        router.push({ name: 'home' })
      })
      .catch( err => {
        if (err.response.status === 409)
          alert('Queant 또는 다른 소셜 계정으로 이미 가입된 계정입니다.')
        else if (err.response.status === 423)
          alert('비활성화 계정입니다.')
        router.push({ name: 'login' })
      })
    } 
    
    // kakaoLogin() {
    //   window.Kakao.init(process.env.VUE_APP_KAKAO_JAVASCRIPT)
    //   const params = {
    //     redirectUri: "http://localhost:8080/kakao",
    //   };
    //   window.Kakao.Auth.authorize(params)
    // },

    // naverLogin() {
      //   const clientId = process.env.VUE_APP_NAVER_CLIENT_ID
      //   const redirectUri = 'http://localhost:8080/naver'
      //   var url = 'https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id='+clientId+'&redirect_uri='+redirectUri+'&state=1234';
      //   window.location.replace(url)
      // },
  }
};
