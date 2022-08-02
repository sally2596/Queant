<template>
  <div class="header" id="header">
    <nav class="navbar navbar-expand-lg bg-none" id="admin_navbar">
      <router-link to="/">
        <img class="logo mr-4" src="../assets/image/퀸트_로고.png" alt="">
      </router-link>
      <p>ADMIN PAGE</p>
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