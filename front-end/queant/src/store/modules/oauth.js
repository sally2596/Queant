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

    // 카카오 로그인
    kakaoLogin() {
      axios({
        url: spring.social.kakao(),
        method: "get",
      })
      .then((res) => {
        console.log(res);
        window.location.href = res.data
      })
      .catch((err) => {
        console.log(err)
      });
    },

    naverLogin() {
      axios({
        url: spring.social.naver(),
        method: "get",
      })
      .then((res) => {
        console.log(res);
        window.location.href = res.data
      })
      .catch((err) => {
        console.log(err)
      });
    }
  }
};
