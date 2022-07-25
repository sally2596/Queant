<template>
  <div>
    <h1>이름: {{ currentUser.name }}</h1>
    <h1>이메일: {{ currentUser.email }}</h1>
    <h1>성별: {{ currentUser.gender }}</h1>
    <h1>생년월일: {{ currentUser.birthdate }}</h1>
    <h1>현재비밀번호: </h1> 
    <input type="text">
    <h1>새로운 비밀번호: </h1> <input v-model="password1" type="password">
    <h1>새로운 비밀번호 확인: </h1> <input v-model="password2" type="password">
    <br>
    <button @click="editUserInfo(password1)">변경하기</button>
    <br>
    <button @click="unregister(currentUser.email)">탈퇴하기</button>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
export default {
  name: 'ProfileEdit',
  computed: {
    ...mapGetters(['currentUser'])
  },
  methods: {
    ...mapActions(['editUserinfo', 'unregister'])
  },
  data() {
    return {
      password1: "",
      password2: ""
    }
  },
  created() {}
}
</script>

<style>

</style>