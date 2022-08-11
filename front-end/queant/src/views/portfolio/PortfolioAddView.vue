<template>
  <Navbar></Navbar>
  <header id="title-div">
    <h1 class="title" id="title">상품 추가하기</h1>
  </header>

  <!-- 테스트 -->
  <div>
    <button @click="addPortfolio()">상품 추가</button>
    <button @click="deletePortfolio()">포트폴리오 삭제</button>
    <button @click="editPortfolio()">포트폴리오 변경</button>
  </div>
</template>

<script>
import Navbar from '@/components/Navbar.vue'
import { mapActions } from 'vuex'

export default {
  name: 'PortfolioAddView',
  components: { Navbar },
  methods: {
    ...mapActions(['addPortfolio', 'deletePortfolio', 'editPortfolio'])
  }
}
</script>

<style>

</style>