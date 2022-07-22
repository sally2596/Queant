<template>
  <Navbar/>
  <section class="home_profile">
    <checking v-if="isStatusOff">
      <h5>비밀번호를 한 번 더 확인합니다.</h5>
      <br><br><br>
      <div class="int-area">
        <input
          type="password"
          id="password"
          autocomplete="off"
          required>
        <label for="password">비밀번호</label>
        <button type="button" class="password-poast" id="check-password" @click="toggleOnOff">확인</button>
      </div>
    </checking>
    <profile v-if="!isStatusOff">
      프로필 보여줄 곳(아이디, 성별같은 것은 ReadOnly/비밀번호는 바꿀 수 있게)
    </profile>
  </section>
</template>

<script>
import Navbar from '@/components/Navbar.vue';
export default {
    name: "ProfileView",
    beforeCreate: function () {
        document.body.className = "home_body";
    },
    methods: {
        // 임시로 넣어놓은 기능임(나중에 vuex과 연계해서 삭제할 것)
        toggleOnOff: function () {
            this.isStatusOff = !this.isStatusOff;
        },
    },
    data() {
        return {
            isStatusOff: true
        };
    },
    components: { Navbar }
}
</script>

<style>
</style>