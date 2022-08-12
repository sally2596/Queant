<template>
  <Navbar/>
  <div>
    <h1>PortfolioEditView</h1>
    <h1>PortfolioEditView</h1>

    <input 
      type="text"
      placeholder="Queant에서 상품찾기"
      v-model="text"
      @keyup.enter="fetchProductsByText(text)">
    <button class="searching" @click="fetchProductsByText(text)"><i class="fa-solid fa-magnifying-glass"></i></button>

    <CustomProductModal v-if="isModalViewed"><p>안녕하십니까</p></CustomProductModal>
    <button @click="modal()">사용자 정의 상품 추가</button>

    <portfolio-edit-item
      v-for="product in portfolio"
      :key="product.portfolio_id"
      :myProduct="product">
    </portfolio-edit-item>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import Navbar from '@/components/Navbar.vue'
import PortfolioEditItem from '@/components/PortfolioEditItem.vue'
import CustomProductModal from '@/views/portfolio/CustomProductModal.vue'

export default {
  name: 'PortfolioEditView',
  components: { Navbar, PortfolioEditItem, CustomProductModal },
  computed: {
    ...mapGetters(['portfolio'])
  },
  methods: {
    ...mapActions(['editPortfolio', 'addProductToPortfolio', 'fetchProductsByText']),
    modal(){
      this.isModalViewed = !this.isModalViewed
    }
  },
  data() {
    return {
      text: '',
      isModalViewed: false,      
    }
  }
}
</script>

<style>

</style>