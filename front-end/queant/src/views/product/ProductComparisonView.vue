<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">상품 비교하기</h1>
  </header>
</template>

<script>
import Navbar from '@/components/Navbar.vue'

export default {
  name: 'ProductComparisonView',
  components : { Navbar },
  beforeCreate: function() {
    document.body.className = 'home_body'
  },
}
</script>

<style>
@import '../../assets/css/home.css';
</style>