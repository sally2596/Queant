<template>

  <div class="wrapper d-flex justify-content-center align-items-center">
    <div class="container">
      <h1>QueÆnt</h1>
      <form @submit.prevent="login({ credentials, nextPath })" class="form">
        <input
          v-model="credentials.email"
          v-bind:class="{error : error.email, complete:!error.email&&credentials.email.length!==0}"
          type="text"
          placeholder="Email"
          autocomplete="off"
          required>
        <div class="error-text" v-if="error.email">{{error.email}}</div>

        <input
          style="font-family: NanumSquareRound;"
          v-model="credentials.password"
          v-bind:class="{error : error.password, complete:!error.password&&credentials.password.length!==0}"
          type="password" 
          placeholder="비밀번호"
          autocomplete="off"
          required>

        <p v-if="authError === 404">이메일을 다시 확인해주세요.</p>
        <p v-else-if="authError === 409">비밀번호가 일치하지 않습니다.</p>
        <p v-else-if="authError === 423">비활성화 계정입니다.</p>

        <button type="submit" style="color: darkslategrey; font-family: NanumSquareRound;" id="login-button" :disabled="!isCheckedForm">로그인</button>
        <br>
        <router-link style="text-decoration: none;" :to="{ name: 'register' }"><button type="button" style="color: darkslategrey; font-family: NanumSquareRound;" class="my-2">회원가입</button></router-link>
        <br><br>
        <router-link style="color: darkslategrey; text-decoration: none;" :to="{ name: 'passwordFind' }">비밀번호를 잊으셨나요?</router-link>
        <br><br>
        
        <div>
          <span class="mx-3" @click="kakaoLogin()"><img style="width: 10%; cursor: pointer;" src="@/assets/image/kakao_icon.png" alt="kakao login"></span>
          
          <span class="mx-3" @click="naverLogin()"><img style="width: 10%; cursor: pointer;" src="@/assets/image/naver_icon.png" alt="naver login"></span>

          <span class="mx-3" @click="googleLogin()"><img style="width: 10%; cursor: pointer;" src="@/assets/image/google_icon.png" alt="google login"></span>
        </div>
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
    <h1>QueÆnt</h1>
    Login Form
    <form @submit.prevent="login({ credentials, nextPath })">

      <div class="int-area">
        <input 
        v-model="credentials.email"
        v-bind:class="{error : error.email, complete:!error.email&&credentials.email.length!==0}"
        type="text"
        id="email"
        autocomplete="off" required>
        <label for="email">이메일</label>
        <div class="error-text" v-if="error.email">{{error.email}}</div>
      </div>

      <div class="int-area">
        <input
        v-model="credentials.password"
        v-bind:class="{error : error.password, complete:!error.password&&credentials.password.length!==0}"
        type="password"
        id="pw"
        autocomplete="off" required>
        <label for="pw">비밀번호</label>
        <div class="error-text" v-if="error.password">{{error.password}}</div>
      </div>

      <p v-if="authError === 404">이메일을 다시 확인해주세요.</p>
      <p v-else-if="authError === 409">비밀번호가 일치하지 않습니다.</p>
      <p v-else-if="authError === 423">비활성화 계정입니다.</p>

      <div class="btn-area">
        <button :disabled="!isCheckedForm" type="submit">LOGIN</button>
      </div>

    </form>
    <div class="caption">
      <router-link :to="{ name: 'register' }">아직 회원이 아니신가요?</router-link>
      <br><br>
      <router-link :to="{ name: 'passwordFind' }">비밀번호를 잊으셨나요?</router-link>
      <br><br>
      <router-link :to="{ name: 'home' }">비회원으로 이용하기</router-link>
    </div>

    <div>
      <p>Social Login</p>
      <div class="auth-area">
        <button @click="kakaoLogin()"><img src="@/assets/image/kakao_icon.png" alt="kakao login"> </button>
        
        <div id="naver_id_login">
          <button @click="naverLogin()"><img src="@/assets/image/naver_icon.png" alt="naver login"> </button>
        </div>

        <button @click="googleLogin()"><img src="@/assets/image/google_icon.png" alt="google login"> </button>
      </div>
    </div>
  </section> -->
</template>

<script>
import { mapActions, mapGetters, mapMutations } from 'vuex'
import * as EmailValidator from 'email-validator'
import PV from 'password-validator'

export default {
  name: 'LoginView',
  computed: {
    ...mapGetters(['authError']),
  },
  data() {
    return {
      isCheckedForm: false,
      credentials: {
        email: '',
        password: '',
      },
      nextPath: this.$route.redirectedFrom?this.$route.redirectedFrom.fullPath:'/',
      passwordSchema: new PV(),
      error: {
        email : '',
        password : ''
      }
    }
  },
  methods: {
    ...mapActions(['login', 'googleLogin', 'kakaoLogin', 'naverLogin']),
    ...mapMutations(['SET_AUTH_ERROR']),
    checkForm() {
      if (this.credentials.email.length > 4 && !EmailValidator.validate(this.credentials.email))
        this.error.email = '올바른 이메일 형식이 아닙니다.'
      else this.error.email = ''
      
      if (this.credentials.password.length > 4 && !this.passwordSchema.validate(this.credentials.password))
        this.error.password = '비밀번호는 영문,숫자 포함 8 자리이상이어야 합니다.'
      else this.error.password = ''

      if (!this.error.email && !this.error.password && this.credentials.email && this.credentials.password)
        this.isCheckedForm = true
    }
  },
  watch: {
    credentials: {
      deep: true,
      handler() {
        this.checkForm()
      }
    }
  },
  beforeCreate: function() {
    // document.body.className = 'auth'
  },
  created() {
    this.SET_AUTH_ERROR('')
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
  }
}
</script>

<style scoped>
@import url(http://fonts.useso.com/css?family=Source+Sans+Pro:200,300);
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
  font-weight: 300;
}
body {
  font-family: 'Source Sans Pro', sans-serif;
  color: white;
  font-weight: 300;
}
body ::-webkit-input-placeholder {
  /* WebKit browsers */
  font-family: 'Source Sans Pro', sans-serif;
  color: white;
  font-weight: 300;
}
body :-moz-placeholder {
  /* Mozilla Firefox 4 to 18 */
  font-family: 'Source Sans Pro', sans-serif;
  color: white;
  opacity: 1;
  font-weight: 300;
}
body ::-moz-placeholder {
  /* Mozilla Firefox 19+ */
  font-family: 'Source Sans Pro', sans-serif;
  color: white;
  opacity: 1;
  font-weight: 300;
}
body :-ms-input-placeholder {
  /* Internet Explorer 10+ */
  font-family: 'Source Sans Pro', sans-serif;
  color: white;
  font-weight: 300;
}
.wrapper {
  background: #50a3a2;
  background: -webkit-linear-gradient(top left, #50a3a2 0%, #53e3a6 100%);
  background: linear-gradient(to bottom right, #50a3a2 0%, #53e3a6 100%);
  opacity: 0.8;
  position: absolute;
  left: 0;
  width: 100%;
  height: 100%;
  overflow: hidden;
}

.wrapper.form-success .container h1 {
  -webkit-transform: translateY(85px);
      -ms-transform: translateY(85px);
          transform: translateY(85px);
}
.container {
  max-width: 600px;
  margin: 0 auto;
  /* padding: 80px 0; */
  height: 400px;
  text-align: center;
}
.container h1 {
  font-size: 40px;
  -webkit-transition-duration: 1s;
          transition-duration: 1s;
  -webkit-transition-timing-function: ease-in-put;
          transition-timing-function: ease-in-put;
  font-weight: 200;
}
form {
  padding: 20px 0;
  position: relative;
  z-index: 2;
}
form input {
  -webkit-appearance: none;
     -moz-appearance: none;
          appearance: none;
  outline: 0;
  border: 1px solid rgba(255, 255, 255, 0.4);
  background-color: rgba(255, 255, 255, 0.2);
  width: 250px;
  border-radius: 3px;
  padding: 10px 15px;
  margin: 0 auto 10px auto;
  display: block;
  text-align: center;
  font-size: 18px;
  color: white;
  -webkit-transition-duration: 0.25s;
          transition-duration: 0.25s;
  font-weight: 300;
}
form input:hover {
  background-color: rgba(255, 255, 255, 0.4);
}
form input:focus {
  background-color: white;
  width: 300px;
  color: #53e3a6;
}
form button {
  -webkit-appearance: none;
     -moz-appearance: none;
          appearance: none;
  outline: 0;
  background-color: white;
  border: 0;
  padding: 10px 15px;
  color: #53e3a6;
  border-radius: 3px;
  width: 250px;
  cursor: pointer;
  font-size: 18px;
  -webkit-transition-duration: 0.25s;
          transition-duration: 0.25s;
}
form button:hover {
  background-color: #f5f7f9;
}
.bg-bubbles {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 1;
}
.bg-bubbles li {
  position: absolute;
  list-style: none;
  display: block;
  width: 40px;
  height: 40px;
  background-color: rgba(255, 255, 255, 0.15);
  bottom: -160px;
  -webkit-animation: square 25s infinite;
  animation: square 25s infinite;
  -webkit-transition-timing-function: linear;
  transition-timing-function: linear;
}
.bg-bubbles li:nth-child(1) {
  left: 10%;
}
.bg-bubbles li:nth-child(2) {
  left: 20%;
  width: 80px;
  height: 80px;
  -webkit-animation-delay: 2s;
          animation-delay: 2s;
  -webkit-animation-duration: 17s;
          animation-duration: 17s;
}
.bg-bubbles li:nth-child(3) {
  left: 25%;
  -webkit-animation-delay: 4s;
          animation-delay: 4s;
}
.bg-bubbles li:nth-child(4) {
  left: 40%;
  width: 60px;
  height: 60px;
  -webkit-animation-duration: 22s;
          animation-duration: 22s;
  background-color: rgba(255, 255, 255, 0.25);
}
.bg-bubbles li:nth-child(5) {
  left: 70%;
}
.bg-bubbles li:nth-child(6) {
  left: 80%;
  width: 120px;
  height: 120px;
  -webkit-animation-delay: 3s;
          animation-delay: 3s;
  background-color: rgba(255, 255, 255, 0.2);
}
.bg-bubbles li:nth-child(7) {
  left: 32%;
  width: 160px;
  height: 160px;
  -webkit-animation-delay: 7s;
          animation-delay: 7s;
}
.bg-bubbles li:nth-child(8) {
  left: 55%;
  width: 20px;
  height: 20px;
  -webkit-animation-delay: 15s;
          animation-delay: 15s;
  -webkit-animation-duration: 40s;
          animation-duration: 40s;
}
.bg-bubbles li:nth-child(9) {
  left: 25%;
  width: 10px;
  height: 10px;
  -webkit-animation-delay: 2s;
          animation-delay: 2s;
  -webkit-animation-duration: 40s;
          animation-duration: 40s;
  background-color: rgba(255, 255, 255, 0.3);
}
.bg-bubbles li:nth-child(10) {
  left: 90%;
  width: 160px;
  height: 160px;
  -webkit-animation-delay: 11s;
          animation-delay: 11s;
}
@-webkit-keyframes square {
  0% {
    -webkit-transform: translateY(0);
            transform: translateY(0);
  }
  100% {
    -webkit-transform: translateY(-700px) rotate(600deg);
            transform: translateY(-700px) rotate(600deg);
  }
}
@keyframes square {
  0% {
    -webkit-transform: translateY(0);
            transform: translateY(0);
  }
  100% {
    -webkit-transform: translateY(-700px) rotate(600deg);
            transform: translateY(-700px) rotate(600deg);
  }
}
</style>