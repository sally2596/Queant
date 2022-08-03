<template>
  <div class="nav_header fixed-top" id="nav_header">
    <nav class="navbar navbar-expand-lg bg-none" id="admin_navbar">
      <router-link to="/">
        <img class="logo mr-4" src="../assets/image/퀸트_로고.png" alt="">
      </router-link>
      
      <div>
      <li class="right_content content" style="list-style: none;">
          <p>ADMIN</p>
      </li></div>
      <div>
      <li class="m-auto content" style="list-style: none;">
        <router-link :to="{ name : 'admin' }">유저 관리</router-link>
      </li></div>
      <div>
      <li class="m-auto content" style="list-style: none;">
        <router-link :to="{ name : 'adminSpecial' }">특판상품 관리</router-link>
      </li></div>
    </nav>
  </div>
</template>

<script>
export default {
  name:"Navbar_admin",
}
</script>

<style>
@import '../assets/css/navbar.css';
</style>