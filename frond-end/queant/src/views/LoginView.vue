<template>
  <div class="login">
    <!-- 서비스명 & 슬로건 -->
    <h1>Queant</h1>
    <h3>Invest Your Money Safely</h3>
    <img alt="Vue logo" src="../assets/logo.png">
    
    <!-- 로그인폼 -->
    <form @submit.prevent="login(credentials)">
      <div>
        <label for="username"><i class="fa-solid fa-id-badge"></i> </label>
        <input 
          type="text"
          id="username"
          placeholder="아이디"
          required>
      </div>

      <div>
        <label for="password"><i class="fa-solid fa-key"></i> </label>
        <input
          type="text"
          id="password"
          placeholder="비밀번호"
          required>
      </div>

      <div>
        <button class="btn btn-primary">로그인</button>
        <button class="btn btn-outline-primary">비회원 이용하기</button>
      </div>
    </form>

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
