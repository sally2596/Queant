<template>
  <div>
    <img src="../../assets/image/퀸트_로고.png" alt="" style="width: 100%; height:100%;">
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