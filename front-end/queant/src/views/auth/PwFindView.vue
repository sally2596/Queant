<template>
  <section class="login-form" v-if="emailCheckedStatus !== 200">
    <h1>이메일을 입력하세요.</h1>
    <br><br><br>
      <div class="int-area">
        <input
        v-model="email"
        type="text"
        id="email"
        autocomplete="off" required/>
        <label for="email">EMAIL</label>
        <button @click="emailCheck(email)" class='confirm'>확인</button>
      </div>
      
      <!-- 404는 없는 이메일, 200은 회원 맞음! -->
      <p>{{ emailCheckedStatus }}</p>

      <div class="caption">
        <router-link to="/login"><a href="">비밀번호가 생각나셨나요?</a></router-link>
      </div>
  </section>
  <section class="login-form" v-else>
    <br><br>
    <div class="int-area">
        <input
        type="text"
        id="confirm"
        autocomplete="off"
        required>
        <label class="form-label" for="email">인증번호</label>
        <router-link to="/pwedit"><button class='confirm'>확인</button></router-link>
    </div>
  </section>
</template>

<script>
import { mapActions, mapGetters, mapMutations } from 'vuex'

export default {
  name: 'PwFindView',
  beforeCreate: function() {
    document.body.className = 'auth';
  },
  methods: {
    ...mapActions(['emailCheck']),
  },
  computed: {
    ...mapGetters(['emailCheckedStatus']),
    ...mapMutations(['SET_EMAIL_CHECKED_STATUS'])
  },
  data() {
    return {
      email: "",
    }
  },
  created() {
    this.$store.commit("SET_EMAIL_CHECKED_STATUS", "")
  }
}
</script>

<style scoped src='../../assets/css/user.css'>
</style>