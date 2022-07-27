<template>

  <div>
    이름
    <input
      v-model="credentials.name"
      type="text"
      id="profile-name"
      autocomplete="off"
      class="profile"
      disabled>
    
    이메일
    <input
      v-model="credentials.email"
      type="text"
      id="profile-email"
      class="profile"
      autocomplete="off"
      disabled>

    성별
    <input 
      v-model="credentials.gender"
      type="text"
      disabled>

    생년월일
    <input
      v-model="credentials.birthdate"
      type="text"
      id="profile-birthdate"
      disabled>

    가입유형
    <input
      v-model="credentials.social"
      type="text"
      id="profile-social"
      disabled>

    <!-- ROLE_USER: default, ROLE_SUPER 또는 ROLE_ADMIN 체크/해제하고 수정 누르면 반영 -->
    <div>
      <input
        v-model="credentials.role_set"
        type='checkbox'
        value='ROLE_USER'
        :id="`role-user-${user.member_id}`"
        disabled>
      <label :for="`role-user-${user.member_id}`">ROLE_USER</label>
      
      <input
        v-model="credentials.role_set"
        type='checkbox'
        value='ROLE_SUPER'
        :id="`role-super-${user.member_id}`">
      <label :for="`role-super-${user.member_id}`">ROLE_SUPER</label>
      
      <input
        v-model="credentials.role_set"
        type='checkbox'
        value='ROLE_ADMIN'
        :id="`role-admin-${user.member_id}`">
      <label :for="`role-admin-${user.member_id}`">ROLE_ADMIN</label>
    </div>

    <button @click="editRoleSet(credentials)">수정</button>

    <hr>
  </div>

</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'AdminUserItem',
  props: {
    user: Object
  },
  data() {
    return {
      credentials: {
        name: this.user.name,
        email: this.user.email,
        gender: this.user.gender,
        birthdate: this.user.birthdate,
        social: this.user.social,
        role_set: this.user.role_set
      }
    }
  },
  methods: {
    ...mapActions(['editRoleSet'])
  }
}
</script>

<style>

</style>