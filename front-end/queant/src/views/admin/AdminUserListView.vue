<template>
  <div>
    <Navbar/>
    <header id="title-div">
      <h1 class="title" id="title">유저 관리</h1>
    </header>

    <!-- 검색바 & 필터 -->
    <div class="d-flex justify-content-center">
      <!-- 이메일 검색 -->
      <input
        v-model="email" 
        type="text">
      <button @click="fetchUserInfo(email)">이메일 검색</button>
  

      <!-- ROLE_SET 필터 -->
      <select @change="changeRoleSet($event)">
        <option selected disabled>권한</option>
        <option value='ROLE_USER'>ROLE_USER</option>
        <option value='ROLE_SUPER'>ROLE_SUPER</option>
        <option value='ROLE_ADMIN'>ROLE_ADMIN</option>
      </select>
  
      <!-- SOCIAL 필터 -->
      <select @change='changeSocial($event)'>
        <option selected disabled>가입유형</option>
        <option value='None'>QueÆnt</option>
        <option value='Google'>Google</option>
        <option value='Naver'>Naver</option>
        <option value='Kakao'>Kakao</option>
      </select>
  
      <!-- 필터 초기화 -->
      <button @click="this.$router.go()">필터 초기화</button>
    </div>

      <admin-user-item
        class="d-flex justify-content-center"
        v-for="user in users"
        :key="user.email"
        :user="user">
      </admin-user-item>
  
      <div
        v-for="page in totalPage"
        :key="page">
        <button @click="changeCurrentPage(page)">{{ page }}</button>
      </div>

    <!-- 주어진 데이터 길이만 가지고 자체 페이지네이션 -->
    <!-- ROLE_SET 필터 -->
    <!-- <select @change="fetchUsersThroughRole($event)">
      <option selected disabled>권한</option>
      <option value='ROLE_USER'>ROLE_USER</option>
      <option value='ROLE_SUPER'>ROLE_SUPER</option>
      <option value='ROLE_ADMIN'>ROLE_ADMIN</option>
    </select> -->

    <!-- SOCIAL 필터 -->
    <!-- <select @change='fetchUsersThroughSocial($event)'>
      <option selected disabled>가입유형</option>
      <option value='None'>QueÆnt</option>
      <option value='Google'>Google</option>
      <option value='Naver'>Naver</option>
      <option value='Kakao'>Kakao</option>
    </select> -->

    <!-- <admin-user-item
      class="d-flex justify-content-center"
      v-for="user in orderedUsers.slice((currentPage-1) * 10, (currentPage-1) * 10 + 10)"
      :key="user.email"
      :user="user">
    </admin-user-item> -->

    <!-- <div
      v-for="page in totalPage"
      :key="page">
      <button @click="nextPage(page)">{{ page }}</button>
    </div> -->


  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import _ from 'lodash'
import AdminUserItem from '@/components/AdminUserItem.vue'
import Navbar from '@/components/Navbar.vue'

export default {
  name: 'AdminUserListView',
  components: { AdminUserItem, Navbar },
  computed: {
    ...mapGetters(['users', 'totalPage']),
    // orderedUsers() {
    //   return _.orderBy(this.users, 'member_id')
    // },
    // totalPage() {
    //   return (this.users.length - (this.users.length % 10)) / 10 + 1
    // }
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
    // nextPage(num) {
      //   this.currentPage = num
    //   console.log(this.currentPage)
    // },
    
  },
  mounted() {
    this.fetchUsers(this.payload)
  }
}
</script>

<style>

</style>