<template>
    <section class="login-form">
        <h1>입력하신 이메일로</h1>
        <h1>메일을 보내드렸습니다.</h1>
        <br><br>
        <div class="int-area">
            <input
            type="text"
            id="confirm"
            autocomplete="off"
            required>
            <label class="form-label" for="email">인증번호</label>
            <router-link to="/pwedit"><button class='confirm'>확인</button></router-link>
        </div>
    </section>
</template>
<script>
export default {
    name: 'EmailAuthentication'
}
</script>
<style>
@import '@/assets/css/user.css';
</style>