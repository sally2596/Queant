import spring from "@/api/spring"
import router from "@/router"
import axios from 'axios'

export default {
  state: {

  },
  getters: {

  },
  mutations: {

  },
  actions: {
    // 구글 로그인
    googleLogin() {
      // this.$http.get(sprigin.social.google(), {
      //   headers: {
      //     'Access-Control-Allow-Origin': '*',
      //     'Content-Type': 'application/json; charset = utf-8'
      //   }
      // })
      axios({
        url: spring.social.google(),
        method: 'get',
        // headers: {
        //   'Access-Control-Allow-Origin': '*',
        //   'Content-Type': 'application/json; charset = utf-8'
        // }
      })
      .then( res => {
        console.log(res)
      })
      .catch( err => {
        console.log(err)
      })
    },

    // 카카오 로그인
    kakaoLogin() {
      axios({
        url: spring.social.kakao(),
        method: 'get',
      })
      .then( res => {
        console.log(res)
      })
      .catch( err => {
        console.log(err)
      })
    },
    
    // 카카오 로그인
    // kakaoLogin() {

    //   window.Kakao.init(process.env.VUE_APP_KAKAO)

    //   if (window.Kakao.Auth.getAccessToken()) {
    //     window.Kakao.API.request({
    //       url: '/v1/user/unlink',
    //       success(response) {
    //         console.log(response)
    //       },
    //       fail(error) {
    //         console.log(error)
    //       },
    //     })
    //     window.Kakao.Auth.setAccessToken(undefined)
    //   }


    //   window.Kakao.Auth.login({
    //     success() {
    //       window.Kakao.API.request({
    //         url: '/v2/user/me',
    //         data: {
    //           property_keys: ["kakao_account.email"]
    //         },
    //         success: async function (response) {
    //           console.log(response);
    //         },
    //         fail(error) {
    //           console.log(error)
    //         },
    //       })
    //     },
    //     fail(error) {
    //       console.log(error)
    //     },
    //   })
    // },
  }
}