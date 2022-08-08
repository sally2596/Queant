<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">'{{this.$route.params.text}}' 상품 검색 결과</h1>
  </header>
  <div 
    v-for="product in products"
    :key="product.product_id">
    {{ product }}
    <hr>
  </div>
</template>
<script>
import { mapGetters } from 'vuex'
import Navbar from '@/components/Navbar.vue'

export default {
    name: 'ProductSearchView',
    components: { Navbar },
    computed: {
      ...mapGetters(['products'])
    }
}
</script>
<style>
@import '@/assets/css/home.css';
</style>