<template>
  <div>
    <Navbar/>
    <header id="title-div">
      <h1 class="title" id="title">상품 비교하기</h1>
    </header>
  </div>
</template>

<script>
import Navbar from '@/components/Navbar.vue'
export default {
  name: 'ComparisonView',
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