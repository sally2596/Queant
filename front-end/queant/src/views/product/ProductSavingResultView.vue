<template>
  <Navbar/>
  <!-- 사용자가 검색한 조건 -->
  <!-- <h3>사용자가 검색한 조건</h3>
  <filters-form
    :filters="filters"
    :totalPage="products?.total_page">
  </filters-form>
  <hr> -->

  <!-- 검색 결과 나온 상품들 -->
  <h3>검색 결과 나온 상품들</h3>
  <div
    v-for="product in products"
    :key="product">
     <router-link
      :to="{ name: 'productDetail', params: { productId: product.product_id } }">
      {{ product }}
    </router-link>
    <button @click="pushProductToCart(product)">장바구니에 넣기</button>
    <hr>
  </div>
</template>

<script>
import {  mapGetters, mapMutations } from 'vuex'
import FiltersForm from '@/components/FiltersForm.vue'
import Navbar from '@/components/Navbar.vue'

export default {
  name: 'ProductSavingResultView',
  components: { FiltersForm, Navbar },
  computed: {
    ...mapGetters(['products', 'filters'])
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

</style>