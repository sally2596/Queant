<template>
  <div>
    <h1>이름: 
      <input
       type="text"
       v-model="credentials.name"
       required
      ></h1>

    <h1>이메일: 
      <input
       type="text"
       v-model="credentials.email"
       disabled
       ></h1>

    <h1>성별:
    <div class="choose-area">
      <input 
        v-model="credentials.gender"
        type="radio"
        value="Female"
        class="gender-input-female">
      <p class="female">여성</p>
      
      <input
        v-model="credentials.gender"
        type="radio"
        value="Male"
        class="gender-input-male">
      <p class="male">남성</p>
    </div></h1>

    <h1>생년월일:
      <input
       type="date" 
       v-model="credentials.birthdate"
       required
       ></h1>
    
    <br>
    
    <button @click="editUserInfo(credentials)">변경하기</button>
    <br>
    <button @click="unregister(userInfo.email)">탈퇴하기</button>
  </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'ProfileEdit',
  props: {
    userInfo: Object
  },
  methods: {
    ...mapActions(['editUserInfo', 'unregister']),
  },
  data() {
    return {
      isCheckedForm: false,
      credentials: {
        email: this.userInfo.email,
        name: this.userInfo.name,
        birthdate: this.userInfo.birthdate,
        gender: this.userInfo.gender
      },
    }
  },
  created() {}
}
</script>

<style>

</style>