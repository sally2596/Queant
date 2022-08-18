<template>
  <header class="header">
    <h1 class="logo">
      <router-link
        :to="{ name: 'home' }">
        <img class="logo mr-4" src="@/assets/image/퀸트_로고.png" alt="logo image">
      </router-link>
    </h1>
    <ul class="main-nav">
      
      <li class="ms-lg-auto content" id="searching_place">
        <input 
          type="text"
          placeholder="SEARCH"
          v-model="text"
          style="width: 180px;
          font-style: italic;"
          @keyup.enter="fetchProductsByText(text)"
        >
        <button class="searching" @click="fetchProductsByText(text)"><i class="fa-solid fa-magnifying-glass"></i></button>
      </li>

      <li v-show="isAdmin">
        <router-link :to="{ name : 'admin' }">Admin</router-link>
      </li>

      <li>
        <router-link :to="{ name : 'bankInfoList' }">Banks</router-link>
      </li>

      <li class="dropdown">
        <a 
          class="nav-link dropdown-toggle" 
          href="#" 
          role="button" 
          data-bs-toggle="dropdown" 
          aria-expanded="false">
          Product
        </a>
        <ul class="dropdown-menu" id="ProductDropdownMenu">
          <li class="mt-3">
            <router-link class="dropdown-item" :to="{ name : 'productRecommend' }">맞춤상품검색</router-link>
          </li>
          <hr>
          <li>
            <router-link class="dropdown-item" :to="{ name : 'productCart' }">상품저장소</router-link>
          </li>
          <hr>
          <li>
            <router-link class="dropdown-item" :to="{ name : 'productComparison' }">가상 포트폴리오</router-link>
          </li>
          <hr>
          <li class="mb-3">
            <router-link class="dropdown-item" :to="{ name : 'productSpecialPlus' }">상품제보</router-link>
          </li>
        </ul>
      </li>

      <li>
        <router-link :to="{ name : 'contents' }">Content</router-link>
      </li>

      <!-- 로그아웃 했을 때만 보임 주석 -->
      <li v-show="!isLoggedIn">
        <router-link :to="{ name : 'login' }">Login</router-link>
      </li>

      <!-- 로그인 했을 때만 보임 주석 -->
      <li class="dropdown" v-show="isLoggedIn">
        <a 
          class="nav-link dropdown-toggle"
          href="#" 
          role="button" 
          data-bs-display="static" 
          data-bs-toggle="dropdown" 
          aria-expanded="false">
          Profile
        </a>
        <ul class="dropdown-menu dropdown-menu-white dropdown-menu-lg-end"  id="ProfileDropdownMenu" >
          <li class="pt-3">
            <router-link :to="{ name : 'profile' }" class="dropdown-item">회원정보수정</router-link>
          </li>
          <hr>
          <li>
            <router-link :to="{ name : 'portfolio' }" class="dropdown-item">My 포트폴리오</router-link>
          </li>
          <hr>
          <li class="pb-3" @click="logout()">
            <a href="#" class="dropdown-item">로그아웃</a>
          </li>
        </ul>
      </li>
    </ul>
	</header>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'

export default {
  name: 'Navbar',
  computed: {
    ...mapGetters(['isLoggedIn', 'isAdmin'])
  },
  methods: {
    ...mapActions(['logout', 'fetchProductsByText']),
    isCollapsed: function() {
      this.searching_place = !this.searching_place
    },
  },
  data() {
    return {
      searching_place: true,
      text: ''
    }
  },
}
</script>

<style scoped>
@import '../assets/css/navbar.css';
* {
  z-index: 8;
	box-sizing: border-box;
}
body {
	font-family: 'Montserrat', sans-serif;
	line-height: 1.6;
	margin: 0;
	min-height: 100vh;
}
ul {
  margin: 0;
  padding: 0;
  list-style: none;
}


h2,
h3,
a {
	color: #34495e;
}

a {
	text-decoration: none;
}



.logo {
	margin: 0;
	font-size: 1.45em;
}

.main-nav {
	margin-top: 5px;

}
.logo a,
.main-nav a {
	padding: 10px 15px;
	text-transform: uppercase;
	text-align: center;
	display: block;
}

.main-nav a {
	color: #34495e;
	font-size: .99em;
}

.main-nav a:hover {
	color: #718daa;
}



.header {
	padding-top: .5em;
	padding-bottom: .5em;
	border: 1px solid #a2a2a2;
	background-color: #f4f4f4;
	-webkit-box-shadow: 0px 0px 14px 0px rgba(0,0,0,0.75);
	-moz-box-shadow: 0px 0px 14px 0px rgba(0,0,0,0.75);
	box-shadow: 0px 0px 14px 0px rgba(0,0,0,0.75);
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	border-radius: 5px;
}


/* ================================= 
  Media Queries
==================================== */
input {
  -webkit-appearance: none;
     -moz-appearance: none;
          appearance: none;
  outline: 0;
  border: 1px solid darkgray;
  background-color: white;
  width: 250px;
  border-radius: 3px;
  display: block;
  text-align: center;
  font-size: 1.2rem;
  color: black;
  -webkit-transition-duration: 0.25s;
          transition-duration: 0.25s;
  font-weight: 300;
}
input:hover {
  background-color: rgba(255, 255, 255, 0.4);
}
input:focus {
  background-color: white;
  width: 300px;
  color: #34495e;
}



@media (min-width: 769px) {
	.header,
	.main-nav {
		display: flex;
	}
	.header {
    top: 0;
    position: fixed;
		flex-direction: column;
		align-items: center;
  }
  .header{
		width: 100%;
		margin: 0 auto;
		/* max-width: 1150px; */
	}

}

@media (min-width: 1025px) {
	.header {
		flex-direction: row;
		justify-content: space-between;
	}

}

</style>