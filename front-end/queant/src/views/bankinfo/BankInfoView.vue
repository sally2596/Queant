<template>
  <div>
    <Navbar/>
    <header id="title-div">
      <h1 class="title" id="title">은행 정보</h1>
    </header>
  </div>
</template>

<script>
import Navbar from '@/components/Navbar.vue'
export default {
  name: 'BankInfoView',
  components : {
    Navbar : Navbar
  },
    beforeCreate: function() {
        document.body.className = 'home_body';
    },
}
</script>

<style>
@import '../../assets/css/home.css';
</style>