<template>
  <div>
    <h1>회원가입에 성공하셨습니다.</h1>
    <h1>다시 로그인 해주세용~~</h1>
    <router-link :to="{ name: 'login'}">로그인 페이지로~</router-link>
  </div>
</template>

<script>
export default {
  name: 'RegisterCompleted'
}
</script>

<style>

</style>