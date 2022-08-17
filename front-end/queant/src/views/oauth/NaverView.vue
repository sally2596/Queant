<template>
  <div>
  </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'NaverView',
  methods: {
    ...mapActions(['getNaverAuthorizationCode'])
  },
  created() {
    this.getNaverAuthorizationCode(this.$route.query)
  }
}
</script>

<style>

</style>