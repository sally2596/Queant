<template>
  <div class="nav_header" id="nav_header">
    <nav class="navbar navbar-expand-lg bg-none">
      <router-link to="/">
        <img class="logo mr-4" src="../assets/image/퀸트_로고.png" alt="">
      </router-link>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
       data-bs-target="#navbar" aria-controls="navbar"
        aria-expanded="false" aria-label="Toggle navigation" id="NavbarToggler">
        <i class="fa-solid fa-bars"></i>
      </button>
      <div class="collapse navbar-collapse" id="navbar">
        <ul class="navbar-nav col">
          <div>
            <li class="m-auto content">
              <router-link :to="{ name : 'bankInfoList' }">Bank Info</router-link>
            </li>
          </div>
          <div>
            <li class="dropdown content">
              <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
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
          <li class="ms-lg-auto content" id="searching_place">
            <input 
            type="text"
            placeholder="빠른 상품 검색"
            v-model="keyword" 
            @keyup.enter="searchresultshow(keyword)"
            >
            <button class="searching" @click="searchresultshow(keyword)"><i class="fa-solid fa-magnifying-glass"></i></button>
          </li>
          <!-- 로그아웃 했을 때만 보임 -->
          <li class="right_content content" v-show="!isLoggedIn">
            <router-link :to="{ name : 'login' }">로그인</router-link>
          </li>
          <!-- 로그인 했을 때만 보임 -->
          <li class="dropdown right_content content" v-show="isLoggedIn">
              <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-display="static" data-bs-toggle="dropdown" aria-expanded="false">
                Profile
              </a>
              <ul class="dropdown-menu dropdown-menu-white dropdown-menu-lg-end">
                <li style="margin:8px"><router-link :to="{ name : 'profile' }">내 프로필 관리</router-link></li>
                <hr>
                <li style="margin:8px"><router-link :to="{ name : 'portfolio' }">내 포트폴리오 보기</router-link></li>
                <hr>
                <li style="margin:8px; cursor: pointer" @click="logout">로그아웃</li>
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
    ...mapActions(['logout']),
    isCollapsed: function() {
      this.searching_place = !this.searching_place
    },
    searchresultshow(keyword) {
      if (keyword !== ''){ //검색어를 입력한 경우
        this.$router.push({
          name: "productSearching",
          params: {
            keyword: this.keyword,
          },
        });
        this.keyword = ''
      } else {
        alert('검색어를 입력해주세요!')  //검색어를 입력하지 않은 경우
      }
    },
  },
  data() {
    return {
      searching_place : true,
      keyword : ''
    }
  },
}
</script>

<style>
@import '../assets/css/navbar.css';
</style>