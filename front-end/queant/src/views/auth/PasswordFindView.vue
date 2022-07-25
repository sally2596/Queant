<template>
  <section v-if="emailCheckedStatus !== 200" class="login-form">
    <h1>이메일을 입력하세요.</h1>
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
      
      <!-- 200은 회원 맞음, 404는 없는 회원 -->
      <p>{{ emailCheckedStatus }}</p>

      <div class="caption">
        <router-link to="/login">비밀번호가 생각나셨나요?</router-link>
      </div>
  </section>

  <password-edit v-else class="login-form" :email="email"></password-edit>
</template>

<script>
import { mapActions, mapGetters, mapMutations } from 'vuex'
import PasswordEdit from '@/components/PasswordEdit.vue'

export default {
  name: 'PasswordFindView',
  beforeCreate: function() {
    document.body.className = 'auth';
  },
  components: {
    PasswordEdit
  },
  methods: {
    ...mapActions(['sendTemporaryPassword', 'passwordCheck']),
  },
  computed: {
    ...mapGetters(['emailCheckedStatus']),
    ...mapMutations(['SET_EMAIL_CHECKED_STATUS'])
  },
  data() {
    return {
      code: '',
      email: '',
    }
  },
  created() {
    this.$store.commit('SET_EMAIL_CHECKED_STATUS', '')
  }
}
</script>

<style scoped src='../../assets/css/user.css'>
</style>