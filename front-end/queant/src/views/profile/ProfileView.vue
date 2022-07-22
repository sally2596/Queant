<template>
  <Navbar/>
  <section class="home_profile">
    <div v-if="isStatusOff">
      <h5>비밀번호를 한 번 더 확인합니다.</h5>
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
    <!-- <profile v-if="!isStatusOff">
      프로필 보여줄 곳(아이디, 성별같은 것은 ReadOnly/비밀번호는 바꿀 수 있게)
    </profile> -->
  </section>
</template>

<script>
import Navbar from '@/components/Navbar.vue';
import { mapActions, mapGetters } from 'vuex'

export default {
  name: "ProfileView",
  beforeCreate: function () {
    document.body.className = "home_body";
  },
  components: { Navbar : Navbar },
  methods: {
    ...mapActions(['passwordCheck']),
    // 임시로 넣어놓은 기능임(나중에 vuex과 연계해서 삭제할 것)
    // toggleOnOff: function () {
    //     this.isStatusOff = !this.isStatusOff;
    // },
  },
  computed: {
    ...mapGetters(['currentUser'])
  },
  data() {
    return {
      password: "",
      isStatusOff: true
    };
  },
  created() {

  }
}
</script>

<style>
</style>