<template>
  <div v-if="emailCheckedStatus !== 200" class="wrapper d-flex justify-content-center align-items-center">
    <div class="container">
      <h1 style="font-family: NanumSquareRound;">비밀번호 찾기</h1>

      <form class="form">
        <input
          style="font-family: NanumSquareRound;"
          v-model="email"
          type="text"
          placeholder="Email"
          autocomplete="off" 
          required>

        <!-- 404 없는 회원 주석 -->
        <p v-if="emailCheckedStatus === 404">가입된 이메일이 아닙니다.</p>
        <button style="font-family: NanumSquareRound; color: darkslategrey;" type="button" @click="sendTemporaryPassword(email)">임시비밀번호 발급</button>
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

  <!-- <section v-if="emailCheckedStatus !== 200" class="login-form">
    <h1>이메일을 입력하세요.</h1>
    <p>이메일로 임시 비밀번호가 전송됩니다.</p>
    <br><br><br>
      <div class="int-area">
        <input
        v-model="email"
        type="text"
        id="email"
        autocomplete="off" 
        required/>
        <label for="email">EMAIL</label>
        <button @click="sendTemporaryPassword(email)" class='confirm'>확인</button>
      </div>
      
      404는 없는 회원 주석
      <p v-if="emailCheckedStatus === 404">가입된 이메일이 아닙니다.</p>

      <div class="caption">
        <router-link :to="{ name: 'login' }">비밀번호가 생각나셨나요?</router-link>
      </div>
  </section> -->

  <password-edit v-else :email="email"></password-edit>
</template>

<script>
import { mapActions, mapGetters, mapMutations } from 'vuex'
import PasswordEdit from '@/components/PasswordEdit.vue'

export default {
  name: 'PasswordFindView',
  components: { PasswordEdit },
  computed: {
    ...mapGetters(['emailCheckedStatus']),
  },
  data() {
    return {
      code: '',
      email: '',
    }
  },
  methods: {
    ...mapActions(['sendTemporaryPassword', 'passwordCheck']),
    ...mapMutations(['SET_EMAIL_CHECKED_STATUS'])
  },
  beforeCreate: function() {
    // document.body.className = 'auth'
  },
  mounted() {
    this.SET_EMAIL_CHECKED_STATUS('')
  }
}
</script>

<style scoped>
@import '@/assets/css/auth.css';
</style>