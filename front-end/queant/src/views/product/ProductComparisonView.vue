<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">상품 비교하기</h1>
  </header>
  
  <div
    v-for="(customPortfolio, index) in customPortfolios"
    :key="index">
    <h3>{{ index+1 }}번 포트폴리오</h3>
    <button @click="deletePortfolio(index)">포트폴리오 삭제하기</button>
    <div
      v-for="product in customPortfolio"
      :key="product.product_id">
      {{ product }}
      <button>상품 삭제하기</button>
      <hr>
    </div>

  </div>

</template>

<script>
import Navbar from '@/components/Navbar.vue'
import { mapActions, mapGetters, mapMutations } from 'vuex'

export default {
  name: 'ProductComparisonView',
  components : { Navbar },
  computed: {
    ...mapGetters(['customPortfolios'])
  },
  methods: {
    ...mapActions(['addProductToMyPortfolio']),
    ...mapMutations(['POP_CUSTOM_PORTFOLIO_FROM_CUSTOM_PORTFOLIOS']),
    deletePortfolio(index) {
      this.POP_CUSTOM_PORTFOLIO_FROM_CUSTOM_PORTFOLIOS(index)
    }
  },
  beforeCreate: function() {
    document.body.className = 'home_body'
  }
}
</script>

<style>
@import '../../assets/css/home.css';
</style>