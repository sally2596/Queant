
<!--
    가입하기는 기본적인 폼만 제공됩니다
    기능명세에 따라 개발을 진행하세요.
    Sub PJT I에서는 UX, 디자인 등을 포함하여 백엔드를 제외하여 개발합니다.
 -->
<template>

  <div class="user join wrap position-absolute top-50 start-50 translate-middle">
    <div class="wrapC">
    <br><br>
    <div class="text-center wrap">
      <i class="fa-solid fa-coins fa-2x"></i><h1 class="text-center">QueÆnt</h1>
    </div>
    <p class="text-center">Inverst Your Money Safely</p>
    <br><br>
    <div class="form-wrap">
      <div class="input-with-label">
        <input 
        v-model="nickName"
        v-bind:class="{error : error.nickName, complete:!error.nickName&&nickName.length!==0}" 
        id="nickname" 
        placeholder="닉네임을 입력하세요." 
        type="text" />
        <label for="nickname">닉네임</label>
        <div class="error-text" v-if="error.nickName">{{error.nickName}}</div>
      </div>

      <div class="input-with-label">
        <input 
        v-model="email"
        v-bind:class="{error : error.email, complete:!error.email&&email.length!==0}" 
        @keyup.enter="Join"
        id="email" 
        placeholder="이메일을 입력하세요." 
        type="text" />
        <label for="email">이메일</label>
        <div class="error-text" v-if="error.email">{{error.email}}</div>
      </div>

      <div class="input-with-label">
        <input 
        v-model="password"
        v-bind:class="{error : error.password, complete:!error.password&&password.length!==0}"
        @keyup.enter="Join"
        id="password" 
        :type="passwordType" 
        placeholder="비밀번호를 입력하세요." />
        <label for="password">비밀번호</label>
        <div class="error-text" v-if="error.password">{{error.password}}</div>
      </div>

      <div class="input-with-label">
        <input
          v-model="passwordConfirm"
          :type="passwordConfirmType"
          v-bind:class="{error : error.passwordConfirm, complete:!error.passwordConfirm&&passwordConfirm.length!==0}"
          @keyup.enter="Join"
          id="password-confirm"
          placeholder="비밀번호를 다시한번 입력하세요."
        />
        <label for="password-confirm">비밀번호 확인</label>
        <div class="error-text" v-if="error.passwordConfirm">{{error.passwordConfirm}}</div>
      </div>
    </div>

    <div>
      <label>
        <input v-model="isTerm" type="checkbox" id="term" />
        <span>약관을 동의합니다.</span>
      </label>
      <span class="text-center" @click="termPopup=true">약관보기</span>
    </div>
    <button class="btn-bottom"
    @click="join">가입하기</button>
    </div>
  </div>
</template>

<script>
import "../../components/css/user.scss";
import PV from "password-validator";
import * as EmailValidator from "email-validator";

export default {
  created() {
    this.component = this;

    this.passwordSchema
      .is()
      .min(8)
      .is()
      .max(100)
      .has()
      .digits()
      .has()
      .letters();
  },
  watch: {
    nickName() {
      this.checkForm();
    },
    password() {
      this.checkForm();
    },
    email() {
      this.checkForm();
    },
    passwordConfirm() {
      this.checkForm();
    }
  },
  methods: {
    checkForm() {
      if (this.nickName.length >= 8)
        this.error.nickName = "8자 이하의 닉네임만 설정 가능합니다.";
      else this.error.nickName = false;
      
      if (this.email.length >= 1 && !EmailValidator.validate(this.email))
        this.error.email = "이메일 형식이 아닙니다.";
      else this.error.email = false;

      if (
        this.password.length >= 1 &&
        !this.passwordSchema.validate(this.password)
      )
        this.error.password = "영문,숫자 포함 8 자리이상이어야 합니다.";
      else this.error.password = false;
      
      if (
        this.passwordConfirm.length >= 1 &&
        !(this.passwordConfirm == this.password)
      )
        this.error.passwordConfirm = "입력한 패스워드와 다릅니다.";
      else this.error.passwordConfirm = false;

      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
    join() {
      if (this.isSubmit) {
        this.$router.push('/user/joincomplete')
      }
    }
  },
  data: () => {
    return {
      email: "",
      password: "",
      passwordSchema: new PV(),
      passwordConfirm: "",
      nickName: "",
      isTerm: false,
      isLoading: false,
      error: {
        email: false,
        password: false,
        nickName: false,
        passwordConfirm: false,
        term: false
      },
      passwordType: "password",
      passwordConfirmType: "password",
      termPopup: false
    };
  }
};
</script>
<style>
.user {
  background-image: url('~@/assets/images/login_background.jpg');
  background-size: cover;
  height: 100vh;
  width: 100wh;
}
</style>


