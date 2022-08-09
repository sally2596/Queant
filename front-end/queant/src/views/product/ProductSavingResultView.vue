<template>
  <!-- 사용자가 검색한 조건 -->
  <h3>사용자가 검색한 조건</h3>
  <filters-form
    :filters="filters"
    :totalPage="products?.total_page">
  </filters-form>
  <hr>

  <!-- 검색 결과 나온 상품들 -->
  <h3>검색 결과 나온 상품들</h3>
  <div
    v-for="product in products.product_dto_list"
    :key="product">
    {{ product }}
    <hr>
  </div>
</template>

<script>
import {  mapGetters } from 'vuex'
import FiltersForm from '@/components/FiltersForm.vue'

export default {
  name: 'ProductSavingResultView',
  components: { FiltersForm },
  computed: {
    ...mapGetters(['products', 'filters'])
  }
}
</script>

<style>

</style>