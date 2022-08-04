<template>
  <div>
    <!-- 단일상품 상세정보 -->
    {{ product }}
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'

export default {
  name: 'ProductDetailView',
  computed: {
    ...mapGetters(['product'])
  },
  methods: {
    ...mapActions(['fetchProduct'])
  },
  created() {
    this.fetchProduct(this.$route.params.productId)
  }
}
</script>

<style>

</style>