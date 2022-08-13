<template>
    <NavbarAdmin/>
    <header id="title-div">
      <h1 class="title" id="title">유저 관리</h1>
    </header>
    <!-- 검색바 & 필터 -->
    <div class="d-flex justify-content-center align-items-center">
      <!-- 이메일 검색 -->
      <div class="align-items-center">
        <input
          v-model="email" 
          type="text"
          style="height:30px;">
        <button @click="fetchUserInfo(email)" class="btn btn-sm my-0 mb-1 text-align-center" style="height:25px; font-size: 12px;">이메일 검색</button>
      </div>

      <!-- ROLE_SET 필터 -->
      <div class="m-2 align-items-center">
        <select @change="changeRoleSet($event)" style="height:30px; font-size: 12px;">
          <option selected disabled>권한</option>
          <option value='ROLE_USER'>ROLE_USER</option>
          <option value='ROLE_SUPER'>ROLE_SUPER</option>
          <option value='ROLE_ADMIN'>ROLE_ADMIN</option>
        </select>
      <!-- SOCIAL 필터 -->
        <select @change='changeSocial($event)' style="height:30px; font-size: 12px;">
          <option selected disabled>가입유형</option>
          <option value='None'>QueÆnt</option>
          <option value='Google'>Google</option>
          <option value='Naver'>Naver</option>
          <option value='Kakao'>Kakao</option>
        </select>
      </div>
      <!-- 필터 초기화 -->
      <div class="align-items-center">
        <button @click="this.$router.go()" class="btn btn-sm mt-0" style="height:30px; font-size: 12px;">필터 초기화</button>
      </div>
    </div>
    <br><br><br><br>
    <div id="section-float">
      <section id="adminUserList" class="container row">
        <admin-user-item
          class="col-12 d-flex justify-content-center align-items-center"
          v-for="user in users"
          :key="user.email"
          :user="user">
        </admin-user-item>
      </section>
    </div>
    <div id="admin-userlist-pagenation">
      <div
        v-for="page in totalPage"
        :key="page">
        <button @click="changeCurrentPage(page)" class="pages btn btn-sm">{{ page }}</button>
      </div>
    </div>
    <br><br>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import _ from 'lodash'
import AdminUserItem from '@/components/AdminUserItem.vue'
import NavbarAdmin from '@/components/NavbarAdmin.vue'

export default {
  name: 'AdminUserListView',
  components: { AdminUserItem, NavbarAdmin },
  beforeCreate: function() {
    document.body.className = 'admin_body'
  },
  computed: {
    ...mapGetters(['users', 'totalPage']),
  },
  data() {
    return {
      email: '',
      payload: {
        social: '',
        role: 'ROLE_USER',
        page: 1
      }
    }
  },
  watch: {
    payload: {
      deep: true,
      handler(value) {
        this.fetchUsers(value)
      }
    }
  },
  methods: {
    ...mapActions(['fetchUserInfo', 'fetchUsers']),
    changeCurrentPage(page) {
      this.payload.page = page
    },
    changeRoleSet(event) {
      this.payload.role = event.target.value
      this.payload.page = 1
    },
    changeSocial(event) {
      this.payload.social = event.target.value
      this.payload.page = 1
    }
  },
  created() {
    this.fetchUsers(this.payload)
  },
}
</script>

<style>
@import '../../assets/css/admin.css';
</style>