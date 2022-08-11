<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">'{{this.$route.params.text}}' 상품 검색 결과</h1>
  </header>
  <div id="cart-item">
    <table>
      <thead>
        <tr>
          <th>은행</th>
          <th>상품명</th>
          <th>기본 금리</th>
          <th>최소 가입 기간(개월)</th>
        </tr>
      </thead>
        <br>
      <tbody v-for="product in products" :key="product.product_id">
        <td><router-link :to="{ name: 'bankInfoDetail' , params: { bankId: product.bank_id }}"><img :src="product.picture" alt=""></router-link></td>
        <td><router-link :to="{ name: 'productDetail' , params: { productId: product.product_id }}">{{product.name}}</router-link></td>
        <td>{{product.base_rate}}</td>
        <td>{{product.term_min}}</td>
        <button @click="pushProductToCart(product)">장바구니에 넣기</button>
      </tbody>
    </table>
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
@import '@/assets/css/product.css';
</style>