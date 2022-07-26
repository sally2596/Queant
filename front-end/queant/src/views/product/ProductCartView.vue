<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">상품 저장소</h1>
  </header>
</template>

<script>
import Navbar from '@/components/Navbar.vue'
export default {
  name: 'ProductCartView',
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