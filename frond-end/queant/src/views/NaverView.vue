!<template>
  <div>
    <p>Naver Login Access Pages</p>
  </div>
</template>

<script>
export default {
  name: 'NaverView',
  mounted() {
    const naver_id_login = new window.naver_id_login(process.env.VUE_APP_NAVER, "http://localhost:8080/naver");
    // console.log("access token", naver_id_login.getAccessToken());
    this.$store.dispatch('naverLogin', naver_id_login.getAccessToken())
    // this.$router.push('/')
  }
}
</script>

<style>

</style>