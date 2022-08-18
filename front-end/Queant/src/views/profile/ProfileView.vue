<template>
	<Navbar />
	<section id="content-header-section">
		<h1 class="title" id="title" style="font-size:xx-large;">회원정보수정</h1>
		<section class="search_filter" style="padding:0; width:600px;">
				<div class="filter_button_wrap">
					<div v-if="passwordCheckedStatus !== 200 && userInfo.social === 'None'" style="width:100%; background-color: #3e8e41; height: 50px; text-align: center; line-height:3rem; font-size: 1.5rem; color: white;">
					<strong>비밀번호를 다시 확인합니다.</strong></div>
					<div v-else style="width:100%; background-color: #3e8e41; height: 50px; text-align: center; line-height:3rem; font-size: 1.5rem; color: white;">
					<strong>회원정보를 수정합니다.</strong></div>
				</div>
			<div class="product_section" style="padding: 60px 0;">

				<div v-if="userInfo.social !== 'None'">
					<h5 style="font-family: 'jua';">소셜 로그인 유저는 사용할 수 없습니다.</h5>
				</div>

				<div v-if="passwordCheckedStatus !== 200 && userInfo.social === 'None'">
				<div class="int-area">
					<input v-model="password" type="password" id="password" autocomplete="off"
						@keyup.enter="passwordCheck(password)" required>
					<label for="password">비밀번호</label>
					<button type="button" class="password-post" id="check-password"
						@click="passwordCheck(password)">확인</button>
					<div v-if="passwordCheckedStatus === 409">
						<br><br>
						<h6 style="font-weight:bold">비밀번호를 확인해주세요.</h6>
					</div>
				</div>
				</div>
				<profile-edit v-if="passwordCheckedStatus === 200" :userInfo="userInfo">
			</profile-edit>

			</div>
			
			
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