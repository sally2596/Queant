<template>
  <div>
    <!-- 은행 정보 (bank) -->
    <!-- {{ bank }} -->

    <!-- 은행 상품 목록 (product_list) -->
    <!-- {{ products }} -->
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
    // this.fetchBank(this.$route.params.bank_id)
  }
}
</script>

<style>

</style>