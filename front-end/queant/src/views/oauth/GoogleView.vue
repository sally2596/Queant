<template>
  <div>
  </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'GoogleView',
  methods: {
    ...mapActions(['getGoogleAuthorizationCode'])
  },
  created() {
    this.getGoogleAuthorizationCode(this.$route.query.code)
  }
}
</script>

<style>

</style>