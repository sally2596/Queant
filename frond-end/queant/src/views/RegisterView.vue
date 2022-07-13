!<template>
  <div class="container d-flex align-items-center">
    <div class="col py-5">
      <div class="row justify-content-center">
        <div class="col-md-8 col-lg-6">
          <div>
            <!-- Text -->
            <div class="text-center">
              <h3>회원가입</h3>
            </div>
            <!-- Social Register -->

            <!-- Register Form -->
            <form class="my-3" @submit.prevent="register(credentials)">
              <div class="my-3">
                <label class="form-label" for="name">이름</label>
                <input
                  class="form-control-lg form-control"
                  type="text"
                  id="name"
                  placeholder="이름을 입력해주세요."
                  required>
              </div>

              <div class="my-3">
                <label class="form-label" for="email">이메일 주소</label>
                <input
                  class="form-control-lg form-control"
                  type="text"
                  id="email"
                  placeholder="ID@example.com"
                  required>
              </div>

              <div class="my-3">
                <label class="form-label" for="password1">비밀번호</label>
                <input
                  class="form-control-lg form-control"
                  type="password"
                  id="password1"
                  placeholder="비밀번호"
                  required>
              </div>

              <div class="my-3">
                <label class="form-label" for="password2">비밀번호 확인</label>
                <input
                  class="form-control-lg form-control"
                  type="password"
                  id="password2"
                  placeholder="비밀번호 확인"
                  required>
              </div>

              <!-- Submit Button -->
              <div class="my-5">
                <button class="btn btn-primary">가입하기</button>
              </div>
            </form>

          </div>
        </div>
      
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex'
export default {
  name: 'RegisterView',
  data() {
    return {
      credentials: {
        username: '',
        email: '',
        password1: '',
        password2: ''
      }
    }
  },
  methods: {
    ...mapActions(['register'])
  }
}
</script>

<style>
label {
  width: 110px;
}
</style>