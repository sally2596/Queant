<template>
  <div>
    <h1>구글 로그인</h1>
  </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'GoogleView',
  methods: {
    ...mapActions(['getGoogleAuthorizationCode'])
  },
  mounted() {
    console.log('페이지를 못찾니?')
    // this.getGoogleAuthorizationCode(this.$route.query.code)
  }
}
</script>

<style>

</style>