<template>
  <div>
    <!-- 은행 정보 (bank) -->
    {{ bank }}
    <hr>
    
    <!-- 은행 상품 목록 (product_list) -->
    <div 
      v-for="product in products"
      :key="product.product_id">
      {{ product }}
      <hr>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'

export default {
  name: 'BankInfoDetailView',
  computed: {
    ...mapGetters(['bank', 'products'])
  },
  methods: {
    ...mapActions(['fetchBank'])
  },
  created() {
    this.fetchBank(this.$route.params.bankId)
  }
}
</script>

<style>

</style>