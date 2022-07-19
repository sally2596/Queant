<template>
  <section class="login-form" v-if="isStatusOff">
    <h1>이메일을 입력하세요.</h1>
    <br><br><br>
      <div class="int-area">
        <input 
        type="text"
        id="email"
        autocomplete="off" required/>
        <label for="email">EMAIL</label>
        <button @click="toggleOnOff" class='confirm'>확인</button>
      </div>
      <div class="caption">
        <router-link to="/login"><a href="">비밀번호가 생각나셨나요?</a></router-link>
      </div>
  </section>
  <section class="login-form" v-if="isStatusOn">
    <h1>입력하신 이메일로</h1>
    <h1>메일을 보내드렸습니다.</h1>
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
export default {
  name: 'PwFindView',
  methods: {
  toggleOnOff: function() {
      this.isStatusOn = !this.isStatusOn;
      this.isStatusOff = !this.isStatusOff;
    },
  },
  data() {
      return {
      isStatusOn : false,
      isStatusOff : true,
    }
  },
}
</script>

<style>
@import '@/assets/css/user.css';
</style>