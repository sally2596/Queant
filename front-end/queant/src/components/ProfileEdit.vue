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

		<div class="btn-profile-area" style="border-bottom: 1px solid #000;">
			<label for="gender" style="font-size: 18px; color: #999; margin:0px 220px 25px 10px;">성별</label>
			<input
				v-model="credentials.gender"
				value="Female"
				type="radio"
				class="btn-check" 
				id="Female-select">
			<label class="btn btn-outline-success" for="Female-select">여성</label>
			<input
				v-model="credentials.gender"
				value="Male"
				type="radio"
				class="btn-check" 
				id="Male-select">
			<label class="btn btn-outline-success" for="Male-select">남성</label>
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
      <button class="btn btn-sm btn-outline-success" @click="confirmEditUserInfo()">정보 변경하기</button>
      <router-link to="/passwordEditByProfile" class="btn btn-sm btn-outline-success">비밀번호 변경</router-link>
      <button class="btn btn-sm btn-outline-danger" @click="confirmUnregister()">회원탈퇴</button>
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
    confirmUnregister() {
      if (confirm('정말 QueÆnt를 떠나시겠어요?') == true) {
        this.unregister(this.userInfo.email)
      } else {
        return
      }
    },
    confirmEditUserInfo() {
      if (confirm('입력하신 정보로 변경하시겠어요?') == true) {
        this.editUserInfo(this.credentials)
      } else {
        return
      }
    },
  },
  beforeCreate: function() {
    document.body.className = 'profile_edit_body'
  },
}
</script>

<style scoped>
@import '../assets/css/user.css';
</style>