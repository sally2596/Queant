<template>
  <section class="login-form">
    <form action="">
      <div class="int-area">
        <input
          type="password"
          id="password1"
          autocomplete="off"
          required>
        <label for="password1">새 비밀번호</label>
      </div>

      <div class="int-area">
        <input
          type="password"
          id="password2"
          autocomplete="off"
          required>
        <label for="password2">새 비밀번호 확인</label>
      </div>

      <div class="btn-area">
        <router-link to="/login"><button type="submit">수정</button></router-link>
      </div>
    </form>
  </section>
</template>

<script>
export default {
  name: 'PwEditView',
  beforeCreate: function() {
        document.body.className = 'auth';
    },
}
</script>

<style scoped src='../../assets/css/user.css'>
</style>