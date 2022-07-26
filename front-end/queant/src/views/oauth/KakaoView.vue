<template>
  <div>
    <h1>카카오 로그인</h1>
  </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'KakaoView',
  methods: {
    ...mapActions(['getKakaoAuthorizationCode'])
  },
  mounted() {
    this.getKakaoAuthorizationCode(this.$route.query.code)
  }
}
</script>

<style>

</style>