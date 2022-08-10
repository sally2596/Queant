<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">상품 비교하기</h1>
  </header>

  <div>
    <h3>1번 가상 포트폴리오</h3>
    {{ customPortfolio1 }}
  </div>

  <div>
    <h3>2번 가상 포트폴리오</h3>
    {{ customPortfolio2 }}
  </div>

  <div>
    <h3>3번 가상 포트폴리오</h3>
    {{ customPortfolio3 }}
  </div>
</template>

<script>
import Navbar from '@/components/Navbar.vue'
import { mapGetters } from 'vuex'

export default {
  name: 'ProductComparisonView',
  components : { Navbar },
  computed: {
    ...mapGetters(['customPortfolio1', 'customPortfolio2', 'customPortfolio3'])
  },
  beforeCreate: function() {
    document.body.className = 'home_body'
  },
}
</script>

<style>
@import '../../assets/css/home.css';
</style>