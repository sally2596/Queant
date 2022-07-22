<template>
  <div class="main" id="app">
    
    <nav class="navbar navbar-expand-lg bg-none">
      <router-link to="/">
        <img class="logo mr-4" src="../assets/image/퀸트_로고.png" alt="">
      </router-link>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
       data-bs-target="#navbar" aria-controls="navbar"
        aria-expanded="false" aria-label="Toggle navigation">
        <i class="fa-solid fa-bars"></i>
      </button>
      <div class="collapse navbar-collapse" id="navbar">
        <ul class="navbar-nav col">
          <div>
            <li class="m-auto content">
              <router-link :to="{ name : 'bankInfo'}">Bank Info</router-link>
            </li>
          </div>
          <div>
            <li class="dropdown">
              <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false" style="padding-left:8px;">
                Product
              </a>
              <ul class="dropdown-menu dropdown-menu-white">
                <li style="margin:8px"><router-link :to="{ name : 'productRecommend' }">맞춤 상품 추천</router-link></li>
                <hr>
                <li style="margin:8px"><router-link :to="{ name : 'productCart' }">장바구니</router-link></li>
                <hr>
                <li style="margin:8px"><router-link :to="{ name : 'productSpecialPlus' }">특판 상품 추가</router-link></li>                
                <hr>
                <li style="margin:8px"><router-link :to="{ name : 'productComparison' }">상품 비교</router-link></li>
              </ul>
            </li>
          </div>
          <div>
            <li class="m-auto content">
              <router-link :to="{ name : 'contents'}">Contents</router-link>
            </li>
          </div>
          <!-- 여기는 로그아웃 했을 때만 보여야 함 -->
          <div class="ms-lg-auto" id="login-button"
           style="margin-right:30px; margin-top: 8px; margin-left:8px;"
           v-show="!isLoggedIn">
            <router-link :to="{ name : 'login' }">로그인</router-link>
          <!-- 여기는 로그인 했을 때만 보여야 함 -->
          </div>
          <!-- v-show="isLoggedIn" 추가해야 함 -->
            <li class="dropdown" >
              <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-display="static" data-bs-toggle="dropdown" aria-expanded="false" style="padding-left:8px;">
                Profile
              </a>
              <ul class="dropdown-menu dropdown-menu-white dropdown-menu-lg-end">
                <li style="margin:8px"><router-link :to="{ name : 'profile' }">내 프로필 관리</router-link></li>
                <hr>
                <li style="margin:8px"><router-link :to="{ name : 'portfolio' }">내 포트폴리오 보기</router-link></li>
                <hr>
                <li style="margin:8px" @click="logout">로그아웃</li>
              </ul>
            </li>
        </ul>
      </div>
    </nav>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
export default {
  name:"Navbar",
  computed: {
    ...mapGetters(['isLoggedIn'])
  },
  methods: {
    ...mapActions(['logout'])
  },
}
</script>

<style>
@import '../assets/css/navbar.css';
</style>