<template>

  <div class="wrapper d-flex justify-content-center align-items-center">
    <div class="container">
      <h1 style="font-family: NanumSquareRound;">비밀번호 변경</h1>
      <form @submit.prevent="passwordChange(credentials)" class="form">
        <input
          style="font-family: NanumSquareRound;"
          v-model="credentials.temporaryPassword"
          type="password"
          placeholder="임시비밀번호"
          autocomplete="off"
          required>

        <input
          style="font-family: NanumSquareRound;"
          v-model="credentials.password1"
          v-bind:class="{error : error.password1, complete:!error.password1&&credentials.password1.length!==0}"
          type="password"
          placeholder="새 비밀번호"
          autocomplete="off"
          required>
        <div class="error-text" v-if="error.password1" style="margin-bottom:10px;">{{ error.password1 }}</div>

        <input
          style="font-family: NanumSquareRound;"
          v-model="credentials.password2"
          v-bind:class="{error : error.password2, complete:!error.password2&&credentials.password2.length!==0}"
          type="password"
          placeholder="새 비밀번호 확인"
          autocomplete="off"
          required>
        <div class="error-text" v-if="error.password2" style="margin-bottom:0px;">{{ error.password2 }}</div>

        <p v-if="passwordCheckedStatus === 409">임시 비밀번호를 다시 확인해주세요.</p>
        
        <!-- 변경 활성화 주석 -->
        <button v-if="isCheckedForm" style="color: darkslategrey; font-family: NanumSquareRound;" type="submit">변경</button>
        <!-- 변경 비활성화 주석 -->
        <button v-else disabled style="color: darkslategrey; font-family: NanumSquareRound; opacity: 70%;">변경</button>
      </form>

    </div>
   
    <ul class="bg-bubbles">
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
    </ul>
  </div>   

  <!-- <section class="login-form">
    <form @submit.prevent="passwordChange(credentials)">

      <div class="int-area">
        <input
          v-model="credentials.temporaryPassword"
          type="password"
          id="temporary-password"
          autocomplete="off"
          required>
        <label for="temporary-password">임시 비밀번호</label>
      </div>
          
      <div class="int-area">
        <input
          v-model="credentials.password1"
          v-bind:class="{error : error.password1, complete:!error.password1&&credentials.password1.length!==0}"
          type="password"
          id="password1"
          autocomplete="off"
          required>
        <label for="password1">새 비밀번호</label>
        <div class="error-text" v-if="error.password1" style="margin-bottom:10px;">{{ error.password1 }}</div>
      </div>

      <div class="int-area">
        <input
          v-model="credentials.password2"
          v-bind:class="{error : error.password2, complete:!error.password2&&credentials.password2.length!==0}"
          type="password"
          id="password2"
          autocomplete="off"
          required>
        <label for="password2">새 비밀번호 확인</label>
        <div class="error-text" v-if="error.password2" style="margin-bottom:0px;">{{ error.password2 }}</div>
      </div>

      <p v-if="passwordCheckedStatus === 409">임시 비밀번호를 다시 확인해주세요.</p>

      <div class="btn-area">
        변경 활성화 주석
        <button v-if="isCheckedForm" type="submit">변경하기</button>
        
        변경 비활성화 주석
        <button v-else disabled>변경하기</button>
      </div>
    </form>
  </section> -->
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import PV from 'password-validator'

export default {
  name: 'PasswordEdit',
  props: {
    email: String
  },
  computed: {
    ...mapGetters(['passwordCheckedStatus'])
  },
  data() {
    return {
      isCheckedForm: false,
      credentials: {
        email: this.email,
        temporaryPassword: '',
        password1: '',
        password2: '',
      },
      passwordSchema: new PV(),
      error: {
        email : '',
        password1 : '',
        password2 : ''
      },
    }
  },
  methods: {
    ...mapActions(['passwordChange']),
    checkForm() {
      if (this.credentials.password1.length > 0 && !this.passwordSchema.validate(this.credentials.password1))
        this.error.password1 = '영문,숫자 포함 8 자리이상이어야 합니다.'
      else this.error.password1 = ''
    
      if (this.credentials.password1 !== this.credentials.password2 && this.credentials.password2.length > 0) 
        this.error.password2 = '비밀번호가 일치하지 않습니다.'
      else this.error.password2 = ''

      if (!this.error.password1 && !this.error.password2 && this.credentials.email && this.credentials.password1 && this.credentials.password2)
        this.isCheckedForm = true
      else this.isCheckedForm = false
    }
  },
  watch: {
    credentials: {
      deep: true,
      handler() {
        this.checkForm()
      }
    },
  },
  beforeCreate: function() {
    // document.body.className = 'auth'
  },
  created() {
    this.isCheckedForm = false
    this.passwordSchema
      .is()
      .min(8)
      .is()
      .max(100)
      .has()  
      .digits()
      .has()
      .letters();
  }
}
</script>

<style scoped>
@import '@/assets/css/auth.css';
</style>