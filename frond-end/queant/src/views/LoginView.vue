<template>
  <div class="d-grid gap-2 col-6 mx-auto">
    <!-- 서비스명 & 슬로건 -->
    <h1>Queant</h1>
    <h3>Invest Your Money Safely</h3>
    <br><br>
    <!-- 로그인폼 -->
    <form @submit.prevent="login(credentials)">
      <div>
        <label class="col-6">이메일</label>
        <input
          class="col-6"
          type="text"
          id="username"
          required>
      </div>
      <div>
        <label class="col-6">비밀번호</label>
        <input
          class="col-6"
          type="text"
          id="password"
          required>
      </div>
      <button class="btn btn-outline-dark" type="button">로그인</button>
      <button class="btn btn-outline-primary" type="button">비회원 이용하기</button>
  </form>
  
  <hr>

  <button class="btn--text">회원가입</button>
  <button class="btn--text">KAKAO</button>
  <button class="btn--text">GOOGLE</button>
  <button class="btn--text">NAVER</button>
  
  </div>
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