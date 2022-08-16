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
@import url('../../assets/css/user.css');
@import '@/assets/css/auth.css';
</style>