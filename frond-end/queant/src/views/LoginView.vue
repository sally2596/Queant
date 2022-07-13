<template>
  <section class="login-form">
    <h1>QueÆnt</h1>
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
      <a href="">아직 회원이 아니신가요?</a>
    </div>
  
    <p>Social Login</p>
  <div class="auth-area">
    <button><img src="../assets/image/kakao_icon.png" alt=""> </button>
    <button><img src="../assets/image/naver_icon.png" alt=""> </button>
    <button><img src="../assets/image/google_icon.png" alt=""> </button>
  </div>
  </section>
</template>

<script>
// @ is an alias to /src
import { mapActions } from 'vuex'
export default {
  name: 'LoginView',
  components: {},
  data() {
    return {
      credentials: {
        username: '',
        password: '',
      }
    }
  },
  methods: {
    ...mapActions(['login'])
  }
}
</script>
<style>
@import '../assets/css/user.css';
</style>