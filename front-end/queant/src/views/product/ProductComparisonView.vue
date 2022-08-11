<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">상품 비교하기</h1>
  </header>
  
  <div
    v-for="(portfolio, portfolioIdx) in portfolios"
    :key="portfolioIdx">
    <h3>{{ portfolioIdx+1 }}번 포트폴리오</h3>
    <button @click="popPortfolioFromPortfolios(portfolioIdx)">포트폴리오 삭제하기</button>
    <div
      v-for="(product, productIdx) in portfolio"
      :key="productIdx">
      {{ product }}
      <button @click="popProductFromPortfolio([portfolioIdx, productIdx])">상품 삭제하기</button>
      <hr>
    </div>

  </div>

</template>

<script>
import Navbar from '@/components/Navbar.vue'
import { mapGetters, mapMutations } from 'vuex'

export default {
  name: 'ProductComparisonView',
  components : { Navbar },
  computed: {
    ...mapGetters(['portfolios'])
  },
  methods: {
    ...mapMutations(['POP_PORTFOLIO_FROM_PORTFOLIOS', 'POP_PRODUCT_FROM_PORTFOLIO']),
    popPortfolioFromPortfolios(portfolioIdx) {
      this.POP_PORTFOLIO_FROM_PORTFOLIOS(portfolioIdx)
    },
    popProductFromPortfolio(Idxs) {
      this.POP_PRODUCT_FROM_PORTFOLIO(Idxs)
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