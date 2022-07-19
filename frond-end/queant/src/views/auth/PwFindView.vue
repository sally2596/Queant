<template>
  <section class="login-form">
    <h1>이메일을 입력하세요.</h1>
    <br><br><br>
      <div class="int-area">
        <input 
        type="text"
        id="email"
        autocomplete="off" required/>
        <label for="email">EMAIL</label>
        <router-link to="/emailauthen"><button class='confirm'>확인</button></router-link>
      </div>
      <div class="caption">
        <router-link to="/login"><a href="">비밀번호가 생각나셨나요?</a></router-link>
      </div>
  </section>
</template>

<script>
export default {
  name: 'PwFindView'
}
</script>

<style>
@import '@/assets/css/user.css';
</style>