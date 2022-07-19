<template>
    <section class="register-form">
      <br>
      <h1>회원가입</h1>
      <!-- Social Register -->
      <p>Social Sign in with</p>
      <div class="auth-area">
        <button><img src="@/assets/image/kakao_icon.png" alt=""> </button>
        <button><img src="@/assets/image/naver_icon.png" alt=""> </button>
        <button><img src="@/assets/image/google_icon.png" alt=""> </button>
      </div>
      <br>
      <!-- Register Form -->
      <form @submit.prevent="register(credentials)">
        <div class="int-area">
          <input
            type="text"
            id="name"
            autocomplete="off"
            required>
          <label class="form-label" for="name">이름</label>
        </div>

        <div class="int-area">
          <input
            v-model="email"
            v-bind:class="{error : error.email, complete:!error.email&&email.length!==0}"
            type="text"
            id="email"
            autocomplete="off"
            required>
            <label class="form-label" for="email">이메일 주소</label>
            <a action=""><button class='mail-send'>인증</button></a>
            <div class="error-text" v-if="error.email">{{error.email}}</div>
            
        </div>

        <div class="int-area confirm">
          <input
            type="text"
            id="confirm"
            autocomplete="off"
            required>
            <label class="form-label" for="email">인증</label>
            <a action=""><button class='confirm'>확인</button></a>
        </div>

        <div class="int-area">
          <input
            v-model="password"
            v-bind:class="{error : error.password, complete:!error.password&&password.length!==0}"
            type="password"
            id="password1"
            autocomplete="off"
            required>
          <label for="password1">비밀번호</label>
          <div class="error-text" v-if="error.password">{{error.password}}</div>
        </div>

        <div class="int-area">
          <input
            type="password"
            id="password2"
            autocomplete="off"
            required>
          <label for="password2">비밀번호 확인</label>
        </div>

        <!-- Submit Button -->
        <div class="btn-area">
          <button type="submit">가입하기</button>
        </div>
      </form>
      
      <div class="register-comeback">
        <router-link to="/login"><a href="">이미 회원이신가요?</a></router-link>
      </div>
  </section>
</template>

<script>
import { mapActions } from 'vuex'
import * as EmailValidator from "email-validator";
import PV from "password-validator"
export default {
  name: 'RegisterView',
  created() {
    this.component = this;

    this.passwordSchema
      .is()
      .min(8)
      .is()
      .max(100)
      .has()
      .digits()
      .has()
      .letters();
  },
  watch: {
    password: function(v) {
      this.checkForm();
    },
    email: function(v) {
      this.checkForm();
    },
  },
  methods: {
    ...mapActions(['register']),
    checkForm() {
      
      if (this.email.length > 0 && !EmailValidator.validate(this.email))
        this.error.email = "올바른 이메일 형식이 아닙니다.";
      else this.error.email = false;
      if (this.password.length > 0 && !this.passwordSchema.validate(this.password))
        this.error.password = "영문,숫자 포함 8 자리이상이어야 합니다.";
      else this.error.password = false;
    },
  },
  data: () => {
    return {
      email: "",
      password : "",
      passwordSchema: new PV(),
      error: {
        email : false,
        password : false
      },
      credentials: {
        username: '',
        email: '',
        password1: '',
        password2: ''
      },
      timerCount : 300
    };
  }
};
</script>
<style>
  @import '@/assets/css/user.css';
</style>