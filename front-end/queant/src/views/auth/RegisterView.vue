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

        <!-- 이름 -->
        <div class="int-area">
          <input
            v-model="credentials.username"
            type="text"
            id="name"
            autocomplete="off"
            required>
          <label class="form-label" for="name">이름</label>
        </div>

        <!-- 이메일 -->
        <div class="int-area">
          <input
            v-model="credentials.email"
            v-bind:class="{error : error.email, complete:!error.email&&credentials.email.length!==0}"
            type="text"
            id="email"
            autocomplete="off"
            required>
          <label class="form-label" for="email">이메일 주소</label>
            
          <!-- 중복검사 성공전에 버튼 활성화 -->
          <div v-if="emailCheckedStatus !== 200">
            <button class="mail-send" id="check-email" @click="emailCheck(credentials.email)">중복검사</button>
          </div>
            
          <!-- 중복검사 결과 409 => 중복 이메일 -->
          <div v-else-if="emailCheckedStatus === 409">
            <p>이미 가입된 이메일입니다.</p>
          </div>
            
          <!-- 중복검사 결과 200 성공 => 인증 메일 발송 -->
          <div v-else-if="emailCheckedStatus === 200">
            <p>해당 메일로 인증번호를 보냈습니다.</p>

            <!-- 인증번호 검사  -->
            <div class="int-area">
              <input
                id="code"
                v-model="code"
                type="text"
                required>
              <label class="form-label" for="code">인증번호</label>
              
              <!-- 인증 성공전에 초기상태 -->
              <button v-if="isEmailVerified !== 200" class="verified" @click="emailVerify(code)">인증</button>
              
              <!-- 409 인증코드 불일치 -->
              <p v-if="isEmailVerified === 409">인증번호가 다릅니다.</p>

              <!-- 200 인증코드 일치 -->
              <button v-else-if="isEmailVerified === 200" id="verified">인증성공</button>
            </div>
          </div>
        </div>

        <!-- 재익이가 처음에 해놓은 설정 -->
        <!-- <button class='mail-send' v-if="isStatusOff" @click="toggleOnOff" id="check-email">인증</button> -->
        <div class="error-text" v-if="error.email">{{error.email}}</div>
      
        <!-- 비밀번호 -->
        <div class="int-area">
          <input
            v-model="credentials.password1"
            v-bind:class="{error : error.password1, complete:!error.password1&&credentials.password1.length!==0}"
            type="password"
            id="password1"
            autocomplete="off"
            required>
          <label for="password1">비밀번호</label>
          <div class="error-text" v-if="error.password1">{{error.password1}}</div>
        </div>

        <div class="int-area">
          <input
            v-model="credentials.password2"
            v-bind:class="{error : error.password2, complete:!error.password2&&credentials.password2.length!==0}"
            type="password"
            id="password2"
            autocomplete="off"
            required>
          <label for="password2">비밀번호 확인</label>
          <div class="error-text" v-if="error.password2">{{error.password2}}</div>
        </div>  

        <!-- Submit Button -->
        <div class="btn-area">
          <button type="submit">가입하기</button>
        </div>
      </form>
      
      <div class="register-comeback">
        <router-link :to="{ name: 'login' }">이미 회원이신가요?</router-link>
      </div>
  </section>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import * as EmailValidator from "email-validator";
import PV from "password-validator"
export default {
  name: 'RegisterView',
  beforeCreate: function() {
        document.body.className = 'auth';
    },
  computed: {
    ...mapGetters(['emailCheckedStatus', 'isEmailVerified'])
  },
  created() {
    this.component = this;
    this.isEmailVerified = ''
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
    credentials: {
      deep: true,
      handler() {
        this.checkForm()
      }
    },
  },
  methods: {
    ...mapActions(['register', 'emailCheck', 'emailVerify']),
    checkForm() {
      
      if (this.credentials.email.length > 0 && !EmailValidator.validate(this.credentials.email))
        this.error.email = "올바른 이메일 형식이 아닙니다.";
      else this.error.email = "";
      if (this.credentials.password1.length > 0 && !this.passwordSchema.validate(this.credentials.password1))
        this.error.password1 = "영문,숫자 포함 8 자리이상이어야 합니다.";
      else this.error.password1 = "";
      if (this.credentials.password1 !== this.credentials.password2)
        this.error.password2 = "비밀번호가 일치하지 않습니다."
      else this.error.password2 = "";
    },
      toggleOnOff: function() {
      this.isStatusOn = !this.isStatusOn;
      this.isStatusOff = !this.isStatusOff;
    },
  },
  data() {
    return {
      credentials: {
        username: '',
        email: '',
        password1: '',
        password2: ''
      },
      code: "",
      passwordSchema: new PV(),
      error: {
        email : "",
        password1 : "",
        password2 : ""
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