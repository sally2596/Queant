<template>
  <section class="register-form">
    <h1>QueÆnt 회원 가입</h1>
    <br><br><br><br>
    <!-- Register Form -->
    <form @submit.prevent="register(credentials)">
  
      <!-- name -->
      <div class="int-area">
        <input
          v-model="credentials.name"
          type="text"
          id="name"
          autocomplete="off"
          required>
        <label class="form-label" for="name">이름</label>
      </div>
        
      <!-- email -->
      <div class="int-area">
        <input
          v-model="credentials.email"
          v-bind:class="{error : error.email, complete:!error.email&&credentials.email.length!==0}"
          type="text"
          id="email"
          autocomplete="off"
          required
          :readonly="emailCheckedStatus === 200">
        <label class="form-label" for="email" :readonly="emailCheckedStatus === 200">이메일 주소</label>
        <div class="error-text" v-if="error.email" style="margin-bottom:0px;">{{error.email}}</div>
          
        <!-- 중복검사 성공전에 버튼 활성화 -->
        <div v-if="emailCheckedStatus !== 200">
          <button type="button" class="mail-send" id="check-email" @click="emailCheck(credentials.email)">중복검사</button>
          <!-- 중복검사 결과 409 => 중복 이메일 -->
          <p v-if="emailCheckedStatus === 409" style="margin-bottom:0px; margin-top:1px" >이미 가입된 이메일입니다.</p>
        </div>
        <!-- 중복검사 결과 200 성공 => 인증 메일 발송 -->
        <div v-else-if="emailCheckedStatus === 200">
          <p style="margin-bottom:0; margin-top:1px;">메일로 인증 번호가 전송되었습니다.</p>
          <!-- 인증번호 검사  -->
          <div class="int-area">
            <input
              id="code"
              v-model="code"
              type="text"
              required>
            <label class="form-label" for="code">인증번호</label>
            
            <!-- 인증 성공전에 초기상태 -->
            <button type="button" v-if="emailVerifiedStatus !== 200" class="verified" @click="emailVerify(code)">인증</button>
            
            <!-- 409 인증코드 불일치 -->
            <p v-if="emailVerifiedStatus === 409" style="margin-bottom:0px; margin-top:1px;">인증번호가 다릅니다.</p>

            <!-- 200 인증코드 일치 -->
            <button disabled v-else-if="emailVerifiedStatus === 200" id="verified">인증성공</button>
          </div>
        </div>
      </div>

      <!-- password -->
      <div class="int-area">
        <input
          v-model="credentials.password"
          v-bind:class="{error : error.password, complete:!error.password&&credentials.password.length!==0}"
          type="password"
          id="password"
          autocomplete="off"
          required>
        <label for="password">비밀번호</label>
        <div class="error-text" v-if="error.password" style="margin-bottom:10px;">{{error.password}}</div>
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
        <div class="error-text" v-if="error.password2" style="margin-bottom:0px;">{{error.password2}}</div>
      </div>

      <!-- gender -->
      <div class="choose-area">
        <input 
          v-model="credentials.gender"
          type="radio"
          value="Female"
          class="gender-input-female">
        <p class="female">여성</p>
        <input
          v-model="credentials.gender"
          type="radio"
          value="Male"
          class="gender-input-male">
        <p class="male">남성</p>
        <label for="gender">성별</label>
      </div>

      <!-- birthdate -->
      <div class="date-area">
        <input
          v-model="credentials.birthdate"
          type="date"
          id="birthdate">
        <label for="birthdate">생년월일</label>
      </div>

      <!-- Submit Button -->
      <div class="btn-area">
        <!-- 가입 활성화 -->
        <button v-if="isCheckedForm && emailCheckedStatus === 200 && emailVerifiedStatus === 200" type="submit">가입하기</button>
        
        <!-- 가입 비활성화 -->
        <button v-else disabled>가입하기</button>
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
    ...mapGetters(['emailCheckedStatus', 'emailVerifiedStatus'])
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
        this.error.email = "올바른 이메일 형식이 아닙니다."
      else this.error.email = ""
      
      if (this.credentials.password.length > 0 && !this.passwordSchema.validate(this.credentials.password))
        this.error.password = "영문,숫자 포함 8 자리이상이어야 합니다."
      else this.error.password = ""
    
      if (this.credentials.password !== this.credentials.password2) 
        this.error.password2 = "비밀번호가 일치하지 않습니다."
      else this.error.password2 = ""

      if (!this.error.email && !this.error.password && !this.error.password2 && this.credentials.name && this.credentials.email && this.credentials.password && this.credentials.password2)
        this.isCheckedForm = true
      else this.isCheckedForm = false
    },
      toggleOnOff: function() {
      this.isStatusOn = !this.isStatusOn;
      this.isStatusOff = !this.isStatusOff;
    },
  },
  data() {
    return {
      isCheckedForm: false,
      credentials: {
        name: "",
        email: "",
        password: "",
        password2: "",
        birthdate: "",
        gender: ""
      },
      code: "",
      passwordSchema: new PV(),
      error: {
        email : "",
        password : "",
        password2 : ""
      },
      timerCount : 300,
      isStatusOn : false,
      isStatusOff : true
    }
  },
  created() {
    this.component = this
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
  },
};
</script>
<style>
@import '@/assets/css/user.css';
</style>