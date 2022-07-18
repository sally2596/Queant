<template>
  <section class="login-form">
    <h1>QueÆnt</h1>
    <!-- Login Form -->
    <form action="">
      <div class="int-area">
        <input type="text" name="id" id="id"
        autocomplete="off" required>
        <label for="id">EMAIL</label>
      </div>
      <div class="int-area">
        <input type="password" name="pw" id="pw"
        autocomplete="off" required>
        <label for="pw">PASSWORD</label>
      </div>
      <div class="btn-area">
        <button type="submit">LOGIN</button>
      </div>
    </form>
    <div class="caption">
      <a href="">비밀번호를 잊으셨나요?</a>
      <br><br>
      <router-link to="/register"><a href="">아직 회원이 아니신가요?</a></router-link>
    </div>
{{ token }}
    <div>
      <p>Social Login</p>
      <div class="auth-area">
        <button v-on:click="kakaoLogin"><img src="../assets/image/kakao_icon.png" alt=""> </button>
        
        <div id="naver_id_login">
          <button v-on:click="naverLogin"><img src="../assets/image/naver_icon.png" alt=""> </button>
        </div>

        <button><img src="../assets/image/google_icon.png" alt=""> </button>

        <button @click="logout">logout</button>
      </div>
    </div>
  </section>
</template>

<script>
// @ is an alias to /src
import { mapActions, mapState } from 'vuex'

export default {
  name: 'LoginView',
  components: {},
  computed: {
    ...mapState(['token'])
  },
  data() {
    return {
      credentials: {
        username: '',
        password: '',
      }
    }
  },
  methods: {
    ...mapActions(['login', 'logout', 'kakaoLogin']),
    naverLogin() {
      const naver_id_login = new window.naver_id_login(process.env.VUE_APP_NAVER, "http://localhost:8080/naver");
      const state = naver_id_login.getUniqState();
      naver_id_login.setButton("white", 2,40); // 버튼 설정
      naver_id_login.setState(state);
      // naver_id_login.setPopup(); // popup 설정을 위한 코드
      naver_id_login.init_naver_id_login();
    }
  }
}
</script>
<style>
@import '../assets/css/user.css';
</style>