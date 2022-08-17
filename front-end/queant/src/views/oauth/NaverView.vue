<template>
  <div>
    <img src="@/assets/image/퀸트로고단일.png" alt="" style="width: 100%; height:100%;">
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