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
            
            <!-- emailCheckedStatus가 빈값이면 초기상태 -->
            <div v-if="!emailCheckedStatus">
              <button class="mail-send" id="check-email" @click="emailCheck(email)">중복검사</button>
            </div>
            
            <!-- emailCheckedStatus가 200이면 중복 이메일이 아니고, 인증 메일 발송 -->
            <div v-else-if="emailCheckedStatus === 200">
              <div>
                <!-- <button class="mail-send" id="check-email">중복검사</button> -->
                <p>해당 메일로 인증번호를 보냈습니다.</p>
              </div>

              <div class="int-area">
                <input
                  id="code"
                  v-model="code"
                  type="text"
                  required>
                <label class="form-label" for="code">인증번호</label>
                <!-- 빈값 인증버튼 누르기 전 초기상태 -->
                <button v-if="isEmailVerified !== 200" class="verified" @click="emailVerify(code)">인증</button>
                <!-- 200 인증코드 일치 -->
                <button v-else-if="isEmailVerified === 200" id="verified">인증성공</button>
                <!-- 409 인증코드 불일치 -->
                <div v-else-if="isEmailVerified === 409">
                  <div>
                    <p>인증번호가 다릅니다.</p>
                  </div>
                </div>
              </div>
            </div>

            <!-- emailCheckedStatus가 409면 중복 이메일 -->
            <div v-else-if="emailCheckedStatus === 409">
              <button class="mail-send" id="check-email" @click="emailCheck(email)">인증</button>
              <p>이미 가입된 이메일입니다.</p>
            </div>

            <!-- 재익이가 처음에 해놓은 설정 -->
            <!-- <button class='mail-send' v-if="isStatusOff" @click="toggleOnOff" id="check-email">인증</button> -->
            <div class="error-text" v-if="error.email">{{error.email}}</div>
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
import { mapActions, mapGetters } from 'vuex'
import * as EmailValidator from "email-validator";
import PV from "password-validator"
export default {
  name: 'RegisterView',
  computed: {
    ...mapGetters(['emailCheckedStatus', 'isEmailVerified'])
  },
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
    ...mapActions(['register', 'emailCheck', 'emailVerify']),
    checkForm() {
      
      if (this.email.length > 0 && !EmailValidator.validate(this.email))
        this.error.email = "올바른 이메일 형식이 아닙니다.";
      else this.error.email = false;
      if (this.password.length > 0 && !this.passwordSchema.validate(this.password))
        this.error.password = "영문,숫자 포함 8 자리이상이어야 합니다.";
      else this.error.password = false;
    },
      toggleOnOff: function() {
      this.isStatusOn = !this.isStatusOn;
      this.isStatusOff = !this.isStatusOff;
    },
  },
  data: () => {
    return {
      email: "",
      code: "",
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
      timerCount : 300,
      isStatusOn : false,
      isStatusOff : true

    };
  }
};
</script>
<style>
  @import '@/assets/css/user.css';
</style>