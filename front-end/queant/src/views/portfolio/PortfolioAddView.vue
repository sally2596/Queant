<template>
  <Navbar></Navbar>
  <header id="title-div">
    <h1 class="title" id="title">상품 추가하기</h1>
  </header>

  <!-- 테스트 -->
  <div>
    <CustomProductModal v-if="isModalViewed" ><p>안녕하십니까</p></CustomProductModal>
    <button @click="modal()">사용자 정의 상품 추가</button>
  </div>
</template>

<script>
import Navbar from '@/components/Navbar.vue'
import CustomProductModal from '@/views/portfolio/CustomProductModal.vue'
import { mapActions } from 'vuex'

export default {
  name: 'PortfolioAddView',
  components: { Navbar, CustomProductModal },
  methods: {
    ...mapActions(['addProductToPortfolio']),
    modal(){
      this.isModalViewed = !this.isModalViewed
    }
  },
  data(){
    return {
      isModalViewed: false,
      testObject:{
        product_id:3,
        amount: 888,
        base_rate: 2.3,
        institution_name: "elice",
        deposit: true,
        product_name: "elice"
      }
    }
  }
}
</script>

<style>
@import '@/assets/css/user.css';
</style>