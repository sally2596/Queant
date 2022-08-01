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
      <select @change="fetchUsersThroughRole($event)">
        <option selected disabled>권한</option>
        <option value='ROLE_USER'>ROLE_USER</option>
        <option value='ROLE_SUPER'>ROLE_SUPER</option>
        <option value='ROLE_ADMIN'>ROLE_ADMIN</option>
      </select>
  
      <!-- SOCIAL 필터 -->
      <select @change='fetchUsersThroughSocial($event)'>
        <option selected disabled>가입유형</option>
        <option value='None'>QueÆnt</option>
        <option value='Google'>Google</option>
        <option value='Naver'>Naver</option>
        <option value='Kakao'>Kakao</option>
      </select>

      <!-- ROLE_SET 필터 (test) -->
      <!-- <select @change="changeTestRoleSet($event)">
        <option selected disabled>권한</option>
        <option value='ROLE_USER'>ROLE_USER</option>
        <option value='ROLE_SUPER'>ROLE_SUPER</option>
        <option value='ROLE_ADMIN'>ROLE_ADMIN</option>
      </select> -->
  
      <!-- SOCIAL 필터 (test) -->
      <!-- <select @change='changeTestSocial($event)'>
        <option selected disabled>가입유형</option>
        <option value='None'>QueÆnt</option>
        <option value='Google'>Google</option>
        <option value='Naver'>Naver</option>
        <option value='Kakao'>Kakao</option>
      </select> -->
  
      <!-- 필터 초기화 -->
      <button @click="this.$router.go()">필터 초기화</button>
    </div>

    <!-- 자체 페이지네이션 -->
    <admin-user-item
      class="d-flex justify-content-center"
      v-for="user in orderedUsers.slice((currentPage-1) * 10, (currentPage-1) * 10 + 10)"
      :key="user.email"
      :user="user">
    </admin-user-item>

    <div
      v-for="page in totalPage"
      :key="page">
      <button @click="nextPage(page)">{{ page }}</button>
    </div>

    <!-- test -->
    <!-- <div
      v-for="testPage in testTotalPage"
      :key="testPage">
      <button @click="changeTestCurrentPage(testPage)">{{ testPage }}</button>
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
    ...mapGetters(['users', 'testTotalPage']),
    orderedUsers() {
      return _.orderBy(this.users, 'member_id')
    },
    totalPage() {
      return (this.users.length - (this.users.length % 10)) / 10 + 1
    }
  },
  data() {
    return {
      email: '',
      currentPage: 1,
      // test
      // testCurrentPage: 1,
      // payload: {
      //   social: null,
      //   role: 'ROLE_USER',
      //   page: 1
      // }
    }
  },
  watch: {
    // payload: {
    //   deep: true,
    //   handler() {
    //     fetchUsers(payload)
    //   }
    // }
  },
  methods: {
    ...mapActions(['fetchUserInfo', 'fetchUsers', 'fetchUsersThroughRole', 'fetchUsersThroughSocial']),
    nextPage(num) {
      this.currentPage = num
      console.log(this.currentPage)
    },
    // changeTestCurrentPage(testPage) {
    //   this.payload.page = testPage
    // },
    // changeTestRoleSet(role) {
    //   this.payload.role = role
    // },
    // changeTestSocial(social) {
    //   tihs.payload.social = social
    // }
    
  },
  mounted() {
    this.fetchUsers()
  }
}
</script>

<style>

</style>