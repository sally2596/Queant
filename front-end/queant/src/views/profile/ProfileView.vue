<template>
  <Navbar/>
  <section class="home_profile">
    <div v-if="passwordCheckedStatus !== 200 && userInfo.social === 'None'">
      <h5>비밀번호를 한 번 더 확인합니다.</h5>
      <div v-if="passwordCheckedStatus === 409">
        <p>비밀번호를 확인해주세요.</p>
      </div>
      <br><br><br>
      <div class="int-area">
        <input
          v-model="password"
          type="password"
          id="password"
          autocomplete="off"
          required>
        <label for="password">비밀번호</label>
        <button type="button" class="password-poast" id="check-password" @click="passwordCheck(password)">확인</button>
      </div>
    </div>
    <profile-edit v-else :userInfo="userInfo">
      프로필 보여줄 곳(아이디, 성별같은 것은 ReadOnly/비밀번호는 바꿀 수 있게)
    </profile-edit>
  </section>
</template>

<script>
import Navbar from '@/components/Navbar.vue'
import ProfileEdit from '@/components/ProfileEdit.vue'
import { mapActions, mapGetters, mapMutations } from 'vuex'

export default {
  name: 'ProfileView',
  components: { 
    Navbar,
    ProfileEdit 
  },
  computed: {
    ...mapGetters(['userInfo','passwordCheckedStatus'])
  },
  data() {
    return {
      password: '',
      isStatusOff: true
    }
  },
  methods: {
    ...mapActions(['passwordCheck']),
    ...mapMutations(['SET_PASSWORD_CHECKED_STATUS'])
  },
  beforeCreate: function () {
    document.body.className = 'home_body'
  },
  created() {
    // 들어올때마다 비밀번호 확인
    this.SET_PASSWORD_CHECKED_STATUS('')
  }
}
</script>

<style>
</style>