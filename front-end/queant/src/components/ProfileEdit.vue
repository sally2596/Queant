<template>
  <div class="profile-view-form">
    <div class="choose-area">
      <input
        v-model="credentials.name"
        type="text"
        id="profile-name"
        autocomplete="off"
        class="profile">
      <label class="form-label" for="profile-name">이름</label>
    </div>
    <div class="choose-area">
      <input
        v-model="credentials.email"
        type="text"
        id="profile-email"
        class="profile"
        autocomplete="off"
        disabled>
      <label class="form-label" for="profile-email">이메일</label>
    </div>

    <div class="choose-area">
      <input 
        v-model="credentials.gender"
        type="radio"
        value="Female"
        class="gender-input-female"
        id="Female-select">
      <p class="female">여성</p>
      <input
        v-model="credentials.gender"
        type="radio"
        value="Male"
        class="gender-input-male"
        id="Male-select">
      <p class="male" style="margin-right:0;">남성</p>
      <label for="gender">성별</label>
    </div>

    <div class="choose-area">
      <input
        v-model="credentials.birthdate"
        type="date"
        id="profile-birthdate">
      <label for="profile-birthdate">생년월일</label>
    </div>
    
    <br>
    <div>
      <button class="btn btn-sm" @click="editUserInfo(credentials)">변경하기</button>
      <button class="btn btn-sm" @click="unregister(userInfo.email)">탈퇴하기</button>
    </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'ProfileEdit',
  props: {
    userInfo: Object
  },
  data() {
    return {
      isCheckedForm: false,
      credentials: {
        email: this.userInfo.email,
        name: this.userInfo.name,
        birthdate: this.userInfo.birthdate?this.userInfo.birthdate:'',
        gender: this.userInfo.gender?this.userInfo.gender:''
      },
    }
  },
  methods: {
    ...mapActions(['editUserInfo', 'unregister']),
  },
  beforeCreate: function() {
        document.body.className = 'profile_edit_body';
  },
}
</script>

<style>
@import '../assets/css/user.css';
</style>