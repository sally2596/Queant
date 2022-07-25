<template>
  <div>
    <h1>네이버 로그인</h1>
  </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'NaverView',
  methods: {
    ...mapActions(['getNaverAuthorizationCode'])
  },
  mounted() {
    this.getNaverAuthorizationCode(this.$route.query.code)
  }
}
</script>

<style>

</style>