<template>
  <section class="login-form">
    <h1>QueÆnt</h1>
    <!-- Login Form -->
    <form action="">

      <div class="int-area">
        <input 
        v-model="email"
        v-bind:class="{error : error.email, complete:!error.email&&email.length!==0}"
        type="text"
        id="email"
        autocomplete="off" required>
        <label for="email">EMAIL</label>
        <div class="error-text" v-if="error.email">{{error.email}}</div>
      </div>

      <div class="int-area">
        <input
        v-model="password"
        v-bind:class="{error : error.password, complete:!error.password&&password.length!==0}"
        type="password"
        id="pw"
        autocomplete="off" required>
        <label for="pw">PASSWORD</label>
        <div class="error-text" v-if="error.password">{{error.password}}</div>
      </div>

      <div class="btn-area">
        <button type="submit">LOGIN</button>
      </div>

    </form>
    <div class="caption">
      <router-link to="/pwfind"><a href="">비밀번호를 잊으셨나요?</a></router-link>
      <br><br>
      <router-link to="/register"><a href="">아직 회원이 아니신가요?</a></router-link>
      <br><br>
      <router-link to="/"><a href="">비회원으로 이용하시고 싶으신가요?</a></router-link>
    </div>

    <div>
      <p>Social Login</p>
      <div class="auth-area">
        <button v-on:click="kakaoLogin"><img src="@/assets/image/kakao_icon.png" alt=""> </button>
        
        <div id="naver_id_login">
          <button v-on:click="naverLogin"><img src="@/assets/image/naver_icon.png" alt=""> </button>
        </div>

        <button><img src="@/assets/image/google_icon.png" alt=""> </button>
      </div>
    </div>
  </section>
</template>

<script>
// @ is an alias to /src
import { mapActions, mapState } from 'vuex'
import * as EmailValidator from "email-validator";
import PV from "password-validator"

export default {
  name: 'LoginView',
  components: {},
  beforeCreate: function() {
        document.body.className = 'auth';
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
    }
  },
  computed: {
    // ...mapState(['token'])
  },
  methods: {
    // ...mapActions(['login', 'logout', 'kakaoLogin']),
    naverLogin() {
      const naver_id_login = new window.naver_id_login(process.env.VUE_APP_NAVER, "http://localhost:8080/naver");
      const state = naver_id_login.getUniqState();
      naver_id_login.setButton("white", 2,40); // 버튼 설정
      naver_id_login.setState(state);
      // naver_id_login.setPopup(); // popup 설정을 위한 코드
      naver_id_login.init_naver_id_login();
    },
    checkForm() {
      
      if (this.email.length > 4 && !EmailValidator.validate(this.email))
        this.error.email = "올바른 이메일 형식이 아닙니다.";
      else this.error.email = false;
      if (this.password.length > 4 && !this.passwordSchema.validate(this.password))
        this.error.password = "비밀번호는 영문,숫자 포함 8 자리이상이어야 합니다.";
      else this.error.password = false;
    },
  },
  data() {
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
        password: '',
      }
    }
  },
}
</script>
<style scoped>
@import url('../../assets/css/user.css');
</style>