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
            <li class="dropdown m-auto">
              <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                Product
              </a>
              <ul class="dropdown-menu dropdown-menu-white">
                <li><a class="dropdown-item" href="#">상품 추천</a></li>
                <li><a class="dropdown-item" href="#">장바구니</a></li>
              </ul>
            </li>
          </div>
          <div>
            <li class="m-auto">
              <router-link to="/">Content</router-link>
            </li>
          </div>
          <div class="ms-lg-auto mr-2 m-1">
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
@font-face {
    font-family: 'InfinitySans-RegularA1';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/InfinitySans-RegularA1.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

@font-face {
    font-family: 'IBMPlexSansKR-Regular';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-07@1.0/IBMPlexSansKR-Light.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

@font-face {
    font-family: 'IBMPlexSansKR-Bold';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-07@1.0/IBMPlexSansKR-Bold.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

@font-face {
    font-family: 'SuseongDotum';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2205@1.0/SuseongDotum.woff2') format('woff2');
    font-weight: normal;
    font-style: normal;
}

@font-face {
    font-family: 'LeferiPoint-WhiteObliqueA';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2201-2@1.0/LeferiPoint-WhiteObliqueA.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

.main {
  background-color: #ffffff;
  height: 100vh;
  width: 100vw;
  background-size: cover;
}

.font-bold {
  font-family: SuseongDotum;
  font-size: 3rem;
}

.font-description {
  font-family: LeferiPoint-WhiteObliqueA;
  font-size: 1.15rem;
}

.text-large {
  font-size: 1.6rem;

}
.text-medium {
  font-size: 1.2rem !important;
}

.text-small {
  font-size: 0.9rem !important;
}

body {
  background-color: #26282B;
  box-sizing: border-box;
}

#app {
  font-family: IBMPlexSansKR-Regular, InfinitySans-RegularA1, Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #000000 !important;
}

nav {
  padding: 1.2rem !important;
  background-color: transparent;
  z-index: 1;
}

nav a {
  font-size: 1.2rem !important;
  color: #000000; 
  text-decoration: none !important;
}

nav a:hover {
  color: #000000 !important;
}

nav a.router-link-exact-active {
  color: #000000 !important;                     
}

.navbar-text,
.navbar-text a,
.nav-link {
  color: #000000;
}

.navbar-toggler {
  color: #000000 !important;
  border: none;
}

.dropdown-menu[data-bs-popper] {
  right: 0;
}

a { 
  color: #000000;
  text-decoration: none;
  cursor: pointer;
}

a:hover {
  color: #000000;
  text-decoration: underline;
}

.logo {
  max-height: 500px;
  max-width: 200px;
  margin-right: 1rem;
}

.account {
  flex-direction: column;
}
</style>