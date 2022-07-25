import spring from "@/api/spring";
import router from "@/router";
import axios from "axios";

export default {
  state: {},
  getters: {},
  mutations: {},
  actions: {
    // 구글 로그인
    googleLogin({ dispatch }) {
      axios({
        url: spring.social.google(),
        method: "get",
      })
      .then((res) => {
        window.location.href = res.data
      })
      .catch((err) => {
        console.log(err)
      })
    },

    kakaoLogin() {
      window.Kakao.init(process.env.VUE_APP_KAKAO_JAVASCRIPT)
      const params = {
        redirectUri: "http://localhost:8080/kakao",
      };
      window.Kakao.Auth.authorize(params)
    },

    getKakaoAuthorizationCode({ commit }, authorizationCode) {
      console.log(authorizationCode)
      axios({
        url: spring.social.kakao(),
        method: 'get',
        params: {
          code: authorizationCode
        }
      })
      .then( res => {
        console.log(res)
      })
      .catch( err => {
        console.log(err)
      })
    },
    
    naverLogin() {
      const clientId = process.env.VUE_APP_NAVER_CLIENT_ID
      const redirectUri = 'http://localhost:8080/naver'
      var url = 'https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id='+clientId+'&redirect_uri='+redirectUri+'&state=1234';
      window.location.replace(url)
    },

    getNaverAuthorizationCode({ commit }, authorizationCode) {
      console.log(authorizationCode)
      axios({
        url: spring.social.naver(),
        method: 'get',
        params: {
          code: authorizationCode,
          state: '1234'
        }
      })
      .then( res => {
        console.log(res)
      })
      .catch( err => {
        console.log(err)
      })
    }
  }
};
