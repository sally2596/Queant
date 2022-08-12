<template>
  <Navbar/>
  <div>
    <h1>PortfolioEditView</h1>
    <h1>PortfolioEditView</h1>
    <!-- 테스트 -->
    <input 
      type="text"
      placeholder="Queant에서 상품찾기"
      v-model="text"
      @keyup.enter="fetchProductsByText(text)">
    <button class="searching" @click="fetchProductsByText(text)"><i class="fa-solid fa-magnifying-glass"></i></button>

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
    ...mapActions(['editPortfolio', 'addProductToPortfolio', 'fetchProductsByText'])
  },
  data() {
    return {
      text: ''
    }
  }
}
</script>

<style>

</style>