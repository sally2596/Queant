<template>
  <div class="main" id="app">
    <nav class="navbar navbar-expand-lg bg-none">
      <router-link to="/">
        <img class="logo mr-4" src="../assets/image/퀸트_로고.png" alt="">
      </router-link>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbar" aria-controls="navbar" aria-expanded="false" aria-label="Toggle navigation">
        <i class="fa-solid fa-bars"></i>
      </button>
      <div class="collapse navbar-collapse" id="navbar">
        <ul class="navbar-nav col">
          <div>
            <li class="m-auto content">
              <router-link to="/">Bank Info</router-link>
            </li>
          </div>
          <div>
            <li class="dropdown">
              <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false" style="padding-left:8px;">
                Product
              </a>
              <ul class="dropdown-menu dropdown-menu-white">
                <li><a class="dropdown-item" href="#">맞춤 상품 추천</a></li>
                <li><a class="dropdown-item" href="#">장바구니</a></li>
                <li><a class="dropdown-item" href="#">특판 상품 추가</a></li>
              </ul>
            </li>
          </div>
          <div>
            <li class="m-auto content">
              <router-link to="/">Content</router-link>
            </li>
          </div>
          <div class="ms-lg-auto mr-2 m-2" id="login-button">
            <router-link to="/login">로그인</router-link>
          </div>
        </ul>
      </div>
    </nav>
  </div>
</template>

<script>
export default {
 data() {
     return {
         mobile: null,
         mobileNav: null,
         windowWidth: null,
     }
 },
 created() {
     window.addEventListener('resize', this.checkScreen);
     this.checkScreen();
 },
 methods: {
     toogleMobileNav(){
         this.mobileNav = !this.mobileNav
         this.mobileNav
     },
     checkScreen(){
         this.windowWidth = window.innerWidth;
         if(this.windowWidth <= 750){
             return this.mobile = true;
         }
         this.mobile = false;
         this.mobileNav = false;
     }
 },
}
</script>

<style>
@import '../assets/css/home.css';
</style>