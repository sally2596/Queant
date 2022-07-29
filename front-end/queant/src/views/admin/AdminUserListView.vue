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
  
      <!-- 필터 초기화 -->
      <button @click="this.$router.go()">필터 초기화</button>
    </div>

    <admin-user-item
      class="d-flex justify-content-center"
      v-for="user in orderedUsers"
      :key="user.member_id"
      :user="user">
    </admin-user-item>


    <!-- 자체 페이지네이션 테스트 -->
    <!-- <div
      v-for="num in 10"
      :key="num">
      {{ users[(currentPage-1) * 10 + num - 1] }}
    </div>

    <div
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
    ...mapGetters(['users']),
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
      currentPage: 1
    }
  },
  methods: {
    ...mapActions(['fetchUserInfo', 'fetchUsers', 'fetchUsersThroughRole', 'fetchUsersThroughSocial']),
    nextPage(num) {
      this.currentPage = num
      console.log(this.currentPage)
    }
  },
  mounted() {
    this.fetchUsers()
  }
}
</script>

<style>

</style>