<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">추천 결과</h1>
  </header>
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
  name: 'ProductDepositResultView',
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