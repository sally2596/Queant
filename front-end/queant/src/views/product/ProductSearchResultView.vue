<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">'{{this.$route.params.text}}' 상품 검색 결과</h1>
  </header>
  <section class="product_section">
  <div id="cart-item">
    <table class="border table table--block">
      <thead class="border">
        <tr>
          <th>은행</th>
          <th>상품명</th>
          <th>기본 금리</th>
          <th>최소 가입 기간(개월)</th>
        </tr>
      </thead>
      <tbody class="border" v-for="product in tenProducts" :key="product.product_id">
        <td><router-link :to="{ name: 'bankInfoDetail' , params: { bankId: product.bank_id }}"><img :src="product.picture" alt=""></router-link></td>
        <td><router-link :to="{ name: 'productDetail' , params: { productId: product.product_id }}">{{product.name}}</router-link></td>
        <td>{{product.base_rate}}</td>
        <td>{{product.term_min}}</td>
      </tbody>
    </table>
  </div>
  </section>

  <!-- 페이지네이션 -->
  <div
    v-for="page in totalPage"
    :key="page">
    <button class="btn btn-sm" @click="changePage(page)">{{ page }}</button>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import Navbar from '@/components/Navbar.vue'

export default {
  name: 'ProductSearchResultView',
  components: { Navbar },
  computed: {
    ...mapGetters(['products']),
    tenProducts() {
      return this.products.slice(this.productIdx, this.productIdx + 10)
    },
    totalPage() {
      let productsLength = this.products.length
      if (productsLength % 10)
        return ((productsLength - (productsLength % 10)) / 10) + 1
      else
        return (productsLength - (productsLength % 10)) / 10
    }
  },
  methods: {
    ...mapActions(['fetchProduct']),
    changePage(page) {
      this.productIdx = (page - 1) * 10 
    }
  },
  data() {
    return {
      productIdx: 0
    }
  }
}
</script>
<style>
@import '@/assets/css/home.css';
@import '@/assets/css/product.css';
</style>