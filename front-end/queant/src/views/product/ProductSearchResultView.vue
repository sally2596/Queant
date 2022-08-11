<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">'{{this.$route.params.text}}' 상품 검색 결과</h1>
  </header>
  <div 
    v-for="product in products"
    :key="product.product_id">
     <router-link
      :to="{ name: 'productDetail', params: { productId: product.product_id } }">
      {{ product }}
    </router-link>
    <button @click="pushProductToCart(product)">장바구니에 넣기</button>
    <hr>
  </div>
</template>
<script>
import { mapGetters, mapMutations } from 'vuex'
import Navbar from '@/components/Navbar.vue'

export default {
    name: 'ProductSearchResultView',
    components: { Navbar },
    computed: {
      ...mapGetters(['products'])
    },
    methods: {
      ...mapMutations(['PUSH_PRODUCT_TO_CART']),
      pushProductToCart(product) {
        this.PUSH_PRODUCT_TO_CART(product)
      }
    }
}
</script>
<style>
@import '@/assets/css/home.css';
</style>