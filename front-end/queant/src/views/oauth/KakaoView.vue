<template>
  <div>
  </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'KakaoView',
  methods: {
    ...mapActions(['getKakaoAuthorizationCode'])
  },
  created() {
    this.getKakaoAuthorizationCode(this.$route.query.code)
  }
}
</script>

<style>

</style>