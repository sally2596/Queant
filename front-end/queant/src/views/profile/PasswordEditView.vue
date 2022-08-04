<template>
  <section v-if="emailCheckedStatus !== 200" class="login-form">
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

      
    <div class="caption">
      <router-link :to="{ name: 'profile' }">음. 안 바꿀래요.</router-link>
    </div>
      
      <!-- 404는 없는 회원 -->
      <p v-if="emailCheckedStatus === 404">가입된 이메일이 아닙니다.</p>
  </section>

  <password-edit v-else class="login-form" :email="email"></password-edit>
</template>

<script>
import { mapActions, mapGetters, mapMutations } from 'vuex'
import PasswordEdit from '@/components/PasswordEdit.vue'
import Navbar from '@/components/Navbar.vue'

export default {
  name: 'PasswordFindView',
  components: { PasswordEdit, Navbar },
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
    document.body.className = 'auth'
  },
  created() {
    this.SET_EMAIL_CHECKED_STATUS('')
  }
}
</script>

<style scoped>
@import '../../assets/css/user.css';
</style>