<template>
  <Navbar/>
  <div>
    <h1>PortfolioEditView</h1>
    <h1>PortfolioEditView</h1>
    <!-- 테스트 -->
    <button @click="addProductToPortfolio()">상품 추가하기</button>

    <portfolio-edit-item
      v-for="product in portfolio"
      :key="product.portfolio_id"
      :product="product">
    </portfolio-edit-item>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import Navbar from '@/components/Navbar.vue'
import PortfolioEditItem from '@/components/PortfolioEditItem.vue'

export default {
  name: 'PortfolioEditView',
  components: { Navbar, PortfolioEditItem },
  computed: {
    ...mapGetters(['portfolio'])
  },
  methods: {
    ...mapActions(['editPortfolio', 'addProductToPortfolio'])
  }
}
</script>

<style>

</style>