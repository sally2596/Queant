<template>
  <div class="wrapper d-flex justify-content-center align-items-center">
    <div class="container">
      <h1 style="font-family: NanumSquareRound;">회원가입</h1>
      <form @submit.prevent="register(credentials)" class="form">
        <!-- 이름 -->
        <input
          style="font-family: NanumSquareRound;"
          v-model="credentials.name"
          type="text"
          placeholder="이름"
          autocomplete="off"
          required>

        <!-- 이메일 -->
        <input
          v-model="credentials.email"
          v-bind:class="{error : error.email, complete:!error.email&&credentials.email.length!==0}"
          type="text"
          placeholder="Email"
          autocomplete="off"
          required
          :readonly="emailCheckedStatus === 200">
        <div class="error-text" v-if="error.email" style="margin-bottom:0px;">{{error.email}}</div>

        <!-- 중복검사 성공전에 버튼 활성화 주석 -->
        <div v-if="emailCheckedStatus !== 200">
          <button type="button" class="mail-send" style="color: darkslategrey; font-family: NanumSquareRound;"  @click="emailCheck(credentials.email)">Email 중복검사</button>
          <!-- 중복검사 결과 409 => 중복 이메일 주석 -->
          <p v-if="emailCheckedStatus === 409" style="margin-bottom:0px; margin-top:1px" >이미 가입된 이메일입니다.</p>
        </div>
        <!-- 중복검사 결과 200 성공 => 인증 메일 발송 주석 -->
        <div v-else-if="emailCheckedStatus === 200">
          <button type="button" class="mail-send" style="color: darkslategrey; font-family: NanumSquareRound;" @click="emailCheck(credentials.email)">재전송</button>
          <br>
          {{ time }}
          <p style="margin-bottom:0; margin-top:1px;">이메일로 인증 번호가 전송되었습니다.</p>
          <!-- 인증번호 검사 주석 -->
          <input
            v-model="code"
            type="text"
            required>
          
          <!-- 인증 성공전에 초기상태 주석 -->
          <button type="button" style="font-family: NanumSquareRound;" v-if="emailVerifiedStatus !== 200" @click="emailVerify(code)">인증번호 검사</button>
          
          <!-- 409 인증코드 불일치 주석 -->
          <p v-if="emailVerifiedStatus === 409" style="margin-bottom:0px; margin-top:1px;">인증번호가 다릅니다.</p>

          <!-- 200 인증코드 일치 주석 -->
          <button disabled v-else-if="emailVerifiedStatus === 200" style="font-family: NanumSquareRound;">성공</button>

        </div>

        <!-- 비밀번호 -->
        <input
          class="mt-4"
          style="font-family: NanumSquareRound;"
          v-model="credentials.password1"
          v-bind:class="{error : error.password1, complete:!error.password1&&credentials.password1.length!==0}"
          type="password"
          placeholder="비밀번호"
          autocomplete="off"
          required>
        <div class="error-text" v-if="error.password1" style="margin-bottom:10px;">{{ error.password1 }}</div>
        <!-- 비밀번호 확인 -->
        <input
          style="font-family: NanumSquareRound;"
          v-model="credentials.password2"
          v-bind:class="{error : error.password2, complete:!error.password2&&credentials.password2.length!==0}"
          type="password"
          placeholder="비밀번호 확인"
          autocomplete="off"
          required>
        <div class="error-text" v-if="error.password2" style="margin-bottom:0px;">{{ error.password2 }}</div>

        <!-- 성별 -->
        <select @change="setGender($event)">
          <option selected disabled>성별</option>
          <option value="Female">여자</option>
          <option value="Male">남자</option>
          <option value="">선택안함</option>
        </select>

        <!-- 생년월일 -->
        <input
          v-model="credentials.birthdate"
          type="date"
          id="birthdate">

        <button v-if="isCheckedForm && emailCheckedStatus === 200 && emailVerifiedStatus === 200" style="color: darkslategrey;" type="submit">Register</button>

        <button v-else disabled style="color: darkslategrey; font-family: NanumSquareRound; opacity: 70%;">회원가입</button>
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
  <!-- <section class="register-form">
    <h1>QueÆnt 회원 가입</h1>
    <br><br><br><br>
    Register Form 주석
    <form @submit.prevent="register(credentials)">
  
      name 주석
      <div class="int-area">
        <input
          v-model="credentials.name"
          type="text"
          id="name"
          autocomplete="off"
          required>
        <label class="form-label" for="name">* 이름</label>
      </div>
        
      email 주석
      <div class="int-area">
        <input
          v-model="credentials.email"
          v-bind:class="{error : error.email, complete:!error.email&&credentials.email.length!==0}"
          type="text"
          id="email"
          autocomplete="off"
          required
          :readonly="emailCheckedStatus === 200">
        <label class="form-label" for="email" :readonly="emailCheckedStatus === 200">* 이메일</label>
        <div class="error-text" v-if="error.email" style="margin-bottom:0px;">{{error.email}}</div>
          
        중복검사 성공전에 버튼 활성화 주석
        <div v-if="emailCheckedStatus !== 200">
          <button type="button" class="mail-send" id="check-email" @click="emailCheck(credentials.email)">중복검사</button>
          중복검사 결과 409 => 중복 이메일 주석
          <p v-if="emailCheckedStatus === 409" style="margin-bottom:0px; margin-top:1px" >이미 가입된 이메일입니다.</p>
        </div>
        중복검사 결과 200 성공 => 인증 메일 발송 주석
        <div v-else-if="emailCheckedStatus === 200">
          {{ time }}
          <button type="button" class="mail-send" id="check-email" @click="emailCheck(credentials.email)">재전송</button>
          <p style="margin-bottom:0; margin-top:1px;">이메일로 인증 번호가 전송되었습니다.</p>
          인증번호 검사 주석
          <div class="int-area">
            <input
              id="code"
              v-model="code"
              type="text"
              required>
            <label class="form-label" for="code">인증번호</label>
            
            인증 성공전에 초기상태 주석
            <button type="button" v-if="emailVerifiedStatus !== 200" class="verified" @click="emailVerify(code)">인증</button>
            
            409 인증코드 불일치 주석
            <p v-if="emailVerifiedStatus === 409" style="margin-bottom:0px; margin-top:1px;">인증번호가 다릅니다.</p>

            200 인증코드 일치 주석
            <button disabled v-else-if="emailVerifiedStatus === 200" id="verified">인증성공</button>
          </div>
        </div>
      </div>

      password 주석
      <div class="int-area">
        <input
          v-model="credentials.password1"
          v-bind:class="{error : error.password1, complete:!error.password1&&credentials.password1.length!==0}"
          type="password"
          id="password1"
          autocomplete="off"
          required>
        <label for="password1">* 비밀번호</label>
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
        <label for="password2">* 비밀번호 확인</label>
        <div class="error-text" v-if="error.password2" style="margin-bottom:0px;">{{ error.password2 }}</div>
      </div>

      gender 주석
      <div class="choose-area">
        <select @change="setGender($event)">
          <option selected disabled>성별</option>
          <option value="Female">여자</option>
          <option value="Male">남자</option>
          <option value="">선택안함</option>
        </select>
        <label>성별</label>
      </div>

      birthdate 주석
      <div class="date-area">
        <input
          v-model="credentials.birthdate"
          type="date"
          id="birthdate">
        <label for="birthdate">생년월일</label>
      </div>

      Submit Button 주석
      <div class="btn-area">
        가입 활성화 주석
        <button v-if="isCheckedForm && emailCheckedStatus === 200 && emailVerifiedStatus === 200" type="submit">가입하기</button>
        
        가입 비활성화 주석
        <button v-else disabled>가입하기</button>
      </div>
    </form>
      
    <div class="register-comeback">
      <router-link :to="{ name: 'login' }">이미 회원이신가요?</router-link>
    </div>

  </section> -->
</template>

<script>
import { mapActions, mapGetters, mapMutations } from 'vuex'
import * as EmailValidator from 'email-validator'
import PV from 'password-validator'

export default {
  name: 'RegisterView',
  computed: {
    ...mapGetters(['emailCheckedStatus', 'emailVerifiedStatus']),
    time() {
      //3항 연산자를 이용하여 10보다 작을 경우 0을 붙이도록 처리 하였다.
      var min = parseInt((this.timerCount%3600)/60) < 10 ? '0'+ parseInt((this.timerCount%3600)/60) : parseInt((this.timerCount%3600)/60)
      var sec = this.timerCount % 60 < 10 ? '0'+this.timerCount % 60 : this.timerCount % 60
  
      //연산한 값을 화면에 뿌려주는 코드
      return min+':' + sec
    }
  },
  data() {
    return {
      isCheckedForm: false,
      credentials: {
        name: '',
        email: '',
        password1: '',
        password2: '',
        birthdate: '',
        gender: ''
      },
      code: '',
      passwordSchema: new PV(),
      error: {
        email : '',
        password1 : '',
        password2 : ''
      },
      timerCount : 300,
    }
  },
  methods: {
    ...mapActions(['register', 'emailCheck', 'emailVerify']),
    ...mapMutations(['SET_EMAIL_CHECKED_STATUS', 'SET_EMAIL_VERIFIED_STATUS']),
    checkForm() {
      if (this.credentials.email.length > 0 && !EmailValidator.validate(this.credentials.email))
        this.error.email = '올바른 이메일 형식이 아닙니다.'
      else this.error.email = ''
      
      if (this.credentials.password1.length > 0 && !this.passwordSchema.validate(this.credentials.password1))
        this.error.password1 = '영문,숫자 포함 8 자리이상이어야 합니다.'
      else this.error.password1 = ''
    
      if (this.credentials.password1 !== this.credentials.password2 && this.credentials.password2.length > 0) 
        this.error.password2 = '비밀번호가 일치하지 않습니다.'
      else this.error.password2 = ''

      if (!this.error.email && !this.error.password1 && !this.error.password2 && this.credentials.name && this.credentials.email && this.credentials.password1 && this.credentials.password2)
        this.isCheckedForm = true
      else this.isCheckedForm = false
    },
    countDown() {
      if (this.timerCount > 0)
        this.timerCount--
    },
    setGender(event) {
      this.credentials.gender = event.target.value
    }
  },
  watch: {
    credentials: {
      deep: true,
      handler() {
        this.checkForm()
      }
    },
    emailCheckedStatus: {
      handler() {
        this.timerCount = 300
        clearTimeout(this.timer)
        if (this.emailCheckedStatus === 200)
          this.timerCount--
      }
    },
    timerCount: {
      handler() {
        if (this.timerCount === 0)
          alert('인증번호가 만료되었습니다. 재발급 받아주세요.')
        else if (this.timerCount < 300)
          this.timer = setTimeout(this.countDown, 1000)
      }
    }
  },
  beforeCreate: function() {
    // document.body.className = 'auth'
  },
  created() {
    this.SET_EMAIL_CHECKED_STATUS('')
    this.SET_EMAIL_VERIFIED_STATUS('')
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
}
</script>
<style>
@import '@/assets/css/user.css';
</style>