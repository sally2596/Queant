<template>
  <Navbar/>
  <div>
    <h1>PortfolioEditView</h1>
    <h1>PortfolioEditView</h1>
    <router-link :to="{ name: 'portfolioAdd' }"><button>상품 추가하기</button></router-link>
    <portfolio-edit-item
      v-for="product in portfolio"
      :key="product.product_id"
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
    ...mapActions(['editPortfolio'])
  }
}
</script>

<style>

</style>