<template>
  <div>

  </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'AdminView',
  methods: {
    ...mapActions(['getAllUserInfo'])
  },
  mounted() {
    this.getAllUserInfo()
  }
}
</script>

<style>

</style>