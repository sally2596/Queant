<template>
  <div>

  </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'ProductDetailView',
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