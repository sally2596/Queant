<template>
  <div>
    {{ realRate.toFixed(2) }}%
  </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  props: {
    productForRate: Object
  },
  methods: {
    ...mapActions(['fetchProduct'])
  },
  data() {
    return {
      realRate: this.productForRate.option.base_rate
    }
  },
  created() {
    for (let condition of this.productForRate.conditions) {
      this.realRate += condition.special_rate
    }
  }
}
</script>

<style>

</style>