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

    <CustomProductModal 
      v-if="showModal" 
      @close="showModal=false">
    </CustomProductModal>
    <button @click="openModal()">사용자 정의 상품 추가</button>

    <h3>퀸트에서 등록한 상품목록</h3>
    <portfolio-edit-item
      v-for="product in portfolio"
      :key="product.portfolio_id"
      :myProduct="product">
    </portfolio-edit-item>
    
    <h3>사용자 정의 상품목록</h3>
    <custom-product-item
      v-for="customProduct in customProducts"
      :key="customProduct.product_id"
      :customProduct="customProduct">
    </custom-product-item>


  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import Navbar from '@/components/Navbar.vue'
import PortfolioEditItem from '@/components/PortfolioEditItem.vue'
import CustomProductModal from '@/components/CustomProductModal.vue'
import CustomProductItem from '@/components/CustomProductItem.vue'

export default {
  name: 'PortfolioEditView',
  components: { Navbar, PortfolioEditItem, CustomProductModal, CustomProductItem },
  computed: {
    ...mapGetters(['portfolio', 'customProducts'])
  },
  methods: {
    ...mapActions(['editPortfolio', 'addProductToPortfolio', 'fetchProductsByText']),
    openModal(){
      this.showModal = !this.showModal
    }
  },
  data() {
    return {
      text: '',
      showModal: false,   
    }
  }
}
</script>

<style>

</style>