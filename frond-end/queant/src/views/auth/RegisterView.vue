<template>
    <section class="register-form">
      <h1>회원가입</h1>
      <!-- Social Register -->
      <p>Social Sign in with</p>
      <div class="auth-area">
        <button><img src="@/assets/image/kakao_icon.png" alt=""> </button>
        <button><img src="@/assets/image/naver_icon.png" alt=""> </button>
        <button><img src="@/assets/image/google_icon.png" alt=""> </button>
      </div>

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
            type="text"
            id="email"
            autocomplete="off"
            required>
            <label class="form-label" for="email">이메일 주소</label>
        </div>

        <div class="int-area confirm">
          <input
            type="text"
            id="confirm"
            autocomplete="off"
            required>
            <label class="form-label" for="email">인증 번호</label>
            <a action=""><button class='confirm'>확인</button></a>
        </div>

        <div class="int-area">
          <input
            type="password"
            id="password1"
            autocomplete="off"
            required>
          <label for="password1">비밀번호</label>
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
        <router-link to="/"><a href="">이미 회원이신가요?</a></router-link>
      </div>
  </section>
</template>

<script>
import { mapActions } from 'vuex'
import * as EmailValidator from "email-validator";
export default {
  name: 'RegisterView',
  data() {
    return {
      credentials: {
        username: '',
        email: '',
        password1: '',
        password2: ''
      }
    }
  },
  watch: {
      
    email: function(v) {
      this.checkForm();
    }
  },
  methods: {
    ...mapActions(['register']),
    checkForm() {
      
      if (this.email.length >= 0 && !EmailValidator.validate(this.email))
        this.error.email = "이메일 형식이 아닙니다.";
      else this.error.email = false;

    }
  },
  data: () => {
    return {
      email: "",
    };
  }
};
</script>
<style>
  @import '@/assets/css/user.css';
</style>