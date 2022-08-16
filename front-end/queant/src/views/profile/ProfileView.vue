<template>
  <Navbar/>
	<section id="hero">
		<h1 class="title" id="title">내 프로필 관리</h1>
		<section class="home_profile">
			<div v-if="passwordCheckedStatus !== 200 && userInfo.social === 'None'">
				<h5 style="font-family: 'jua';">비밀번호를 한 번 더 확인합니다.</h5>
				<br><br><br>
				<div class="int-area">
					<input
						v-model="password"
						type="password"
						id="password"
						autocomplete="off"
						@keyup.enter="passwordCheck(password)"
						required>
					<label for="password">비밀번호</label>
					<button type="button" class="password-post" id="check-password" @click="passwordCheck(password)">확인</button>
					<div v-if="passwordCheckedStatus === 409">
					<br><br>
					<h6 style="font-weight:bold">비밀번호를 확인해주세요.</h6>
				</div>
				</div>
			</div>
			<profile-edit v-else :userInfo="userInfo">
			</profile-edit>
		</section>
	</section>
    
</template>

<script>
import Navbar from '@/components/Navbar.vue'
import ProfileEdit from '@/components/ProfileEdit.vue'
import { mapActions, mapGetters, mapMutations } from 'vuex'

export default {
  name: 'ProfileView',
  components: { Navbar, ProfileEdit },
  computed: {
    ...mapGetters(['userInfo','passwordCheckedStatus'])
  },
  data() {
    return {
      password: '',
      isStatusOff: true
    }
  },
  methods: {
    ...mapActions(['passwordCheck']),
    ...mapMutations(['SET_PASSWORD_CHECKED_STATUS'])
  },
  beforeCreate: function () {
    document.body.className = 'home_body'
  },
  created() {
    // 들어올때마다 비밀번호 확인
    this.SET_PASSWORD_CHECKED_STATUS('')
  }
}
</script>

<style>
</style>