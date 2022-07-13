<template>
  <div class="container d-flex align-items-center">
    <div class="global-cont col py-5">
      <div class="inner-cont row justify-content-center">
        <div class="sign-in-up col-md-8 col-lg-6">
          <div>
            <!-- Text -->
            <div class="text-center">
              <h2>회원가입</h2>
            </div>

            <!-- Social Register -->
            <div class="my-4">
              <h5>SNS계정으로 간편하게 회원가입</h5>
            </div>

            <div class="wrapper">
              <a href="" class="button button--kakao">카카오로 가입하기</a>
              <a href="" class="button button--google">구글로 가입하기</a>
              <a href="" class="button button--naver">네이버로 가입하기</a>
            </div>

            <!-- Register Form -->
            <form class="my-4" @submit.prevent="register(credentials)">
              <div class="my-3">
                <h5><label class="form-label" for="name">이름</label></h5>
                <input
                  class="form-control-lg form-control"
                  type="text"
                  id="name"
                  placeholder="이름을 입력해주세요."
                  required>
              </div>

              <div class="my-3">
                <h5><label class="form-label" for="email">이메일 주소</label></h5>
                <input
                  class="form-control-lg form-control"
                  type="text"
                  id="email"
                  placeholder="ID@example.com"
                  required>
              </div>

              <div class="my-3">
                <h5><label class="form-label" for="password1">비밀번호</label></h5>
                <input
                  class="form-control-lg form-control"
                  type="password"
                  id="password1"
                  placeholder="비밀번호"
                  required>
              </div>

              <div class="my-3">
                <h5><label class="form-label" for="password2">비밀번호 확인</label></h5>
                <input
                  class="form-control-lg form-control"
                  type="password"
                  id="password2"
                  placeholder="비밀번호 확인"
                  required>
              </div>

              <!-- Submit Button -->
              <div class="my-5">
                <button class="button button--queant">가입하기</button>
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
    ...mapActions(['register']),
    // GoogleLoginBtn: function() {
    //   var self = this;

    //   window.gapi.signin2.render('my-signin2', {
    //     scope: 'profile email',
    //     width: 240,
    //     height: 50,
    //     longtitle: true,
    //     theme: 'dark',
    //     onsuccess: this.GoogleLoginSuccess,
    //     onfailure: this.GoogleLoginFailure,
    //   })

    //   setTimeout(function () {
    //     if (!self.googleLoginCheck) {
    //       const auth = window.gapi.auth2.getAuthInstance();
    //       auth.isSignedIn.get();
    //       document.querySelector('.abcRioButton').click();
    //     }
    //   }, 1500)

    // },
    // async GoogleLoginSuccess(googleUser) {
    //   const googleEmail = googleUser.getBasicProfile().getEmail();
    //   if (googleEmail !== 'undefined') {
    //     console.log(googleEmail);
    //   }
    // },
    // //구글 로그인 콜백함수 (실패)
    // GoogleLoginFailure(error) {
    //   console.log(error);
    // },
  }
}
</script>

<style lang="scss">
* {
  color: white;
  font-family: sans-serif;
}

h2 {
  font-size: 45px;
  text-align: center;
  border-bottom:1px solid gray;
  margin:0px;
  padding:20px;
}

form {
  .form-label {
    justify-content: start;
  }

  .form-control {
    background-color: transparent;
  }
}

.wrapper {
  max-width: 360px;
  margin: 0 auto;
  border-bottom:1px solid gray;
  padding:10px;
}

// Social button styling
.button {
  display: block;
  padding: 1rem;
  text-decoration: none;
  margin: 1rem 0;
  transition: all 0.3s ease;
  border-radius: 12px;
  &--kakao {
    color: black !important;
    background-color: #fee500;
    &:hover {
      background-color: darken(#fee500, 10%);
    }
    &:before {
      content: '\f075';
      font-family: 'FontAwesome';
      padding-right: 1rem;
    }
  }
  &--google {
    color: white !important;
    background-color: #dd4b39;
    &:hover {
      background-color: darken(#dd4b39, 10%); 
    }
    &:before {
      content: '\f1a0';
      font-family: 'FontAwesome';
      padding-right: 1rem;
    }
  }
  &--naver {
    color: white !important;
    background-color: #03c75a;
    &:hover {
      background-color: darken(#03c75a, 10%); 
    }
    &:before {
      content: '\4e';
      font-family: 'FontAwesome';
      padding-right: 1rem;
    }
  }
  &--queant {
    color: black !important;
    background-color: #b9b9b9;
    &:hover {
      background-color: darken(#b9b9b9, 10%); 
    }
    &:before {
      content: '\4e';
      font-family: 'FontAwesome';
      padding-right: 1rem;
    }
  }
}
</style>