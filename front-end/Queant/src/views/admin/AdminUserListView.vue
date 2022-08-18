<template>
  <NavbarAdmin />
  <header id="title-div">
    <h1 class="title" id="title">유저 관리</h1>
  </header>
  <!-- 검색바 & 필터 -->
  <div class="d-flex justify-content-center align-items-center" id="content-header-section">
    <!-- 이메일 검색 -->
    <div class="align-items-center">
      <input v-model="email" type="text" style="height: 30px" />
      <button
        @click="fetchUserInfo(email)"
        class="btn btn-sm my-0 mb-1 text-align-center"
        style="height: 25px; font-size: 12px">
        이메일 검색
      </button>
    </div>

    <!-- ROLE_SET 필터 -->
    <div class="m-2 align-items-center">
      <select
        @change="changeRoleSet($event)"
        style="height: 30px; font-size: 12px"
      >
        <option selected disabled>권한</option>
        <option value="ROLE_USER">ROLE_USER</option>
        <option value="ROLE_SUPER">ROLE_SUPER</option>
        <option value="ROLE_ADMIN">ROLE_ADMIN</option>
      </select>
      <!-- SOCIAL 필터 -->
      <select
        @change="changeSocial($event)"
        style="height: 30px; font-size: 12px"
      >
        <option selected disabled>가입유형</option>
        <option value="None">QueÆnt</option>
        <option value="Google">Google</option>
        <option value="Naver">Naver</option>
        <option value="Kakao">Kakao</option>
      </select>
    </div>
    <!-- 필터 초기화 -->
    <div class="align-items-center">
      <button
        @click="this.$router.go()"
        class="btn btn-sm mt-0"
        style="height: 30px; font-size: 12px"
      >
        필터 초기화
      </button>
    </div>
  </div>
  <div id="section-float">
    <section id="adminUserList" class="container row">
      
       <table class="rwd-table">
        <tbody>
          <tr>
            <th class="text-center">이름</th>
            <th class="text-center">이메일</th>
            <th class="text-center">성별</th>
            <th class="text-center">생년월일</th>
            <th class="text-center">소셜</th>
            <th class="text-center">권한</th>
            <th class="text-center">상태</th>
            <th class="text-center">관리</th>
          </tr>
          <tr 
            v-for="user in users"
            :key="user.email">
            <td style="font-family: NanumSquareRound;" class="col-1 text-center" data-th="Supplier Code">
              {{ user.name }}
            </td>
            <td class="col-3 text-center" data-th="Supplier Name">
              {{ user.email }}
            </td>
            <td class="col-1 text-center" data-th="Invoice Number">
              {{ user.gender }}
            </td>
            <td class="col-2 text-center" data-th="Invoice Date">
              {{ user.birthdate }}
            </td>
            <td class="col-1 text-center" data-th="Due Date">
              {{ user.social }}
            </td>
            <td class="col-1 text-center" data-th="Due Date" v-if="user.role_set.length === 3">
							Admin
						</td>
						<td class="col-1 text-center" data-th="Due Date" v-if="user.role_set.length === 2">
							Manager
						</td>
						<td class="col-1 text-center" data-th="Due Date" v-if="user.role_set.length === 1">
							User
						</td> 
            <td class="col-2 text-center" style="font-family: NanumSquareRound;" v-if="user.enabled">
							활성화
						</td>
						<td class="col-2 text-center" style="font-family: NanumSquareRound;" v-if="!user.enabled">
							비활성화
            </td>
            <td class="col-1 text-center" data-th="Net Amount">
              <button
                @click="openUserModal(user)"
                class="btn btn-outline-success"
                id="show-modal">
                <i class="fa-solid fa-user-gear"></i>
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </section>
  </div>

  <!-- 모달 -->
  <user-change-modal
    v-if="showModal" 
    @close="showModal = false" 
    :modalData="modalData">
  </user-change-modal>

  <!-- 페이지네이션 -->
  <div id="admin-userlist-pagenation">
    <div v-for="page in totalPage" :key="page">
      <button @click="changeCurrentPage(page)" class="pages btn btn-sm">
        {{ page }}
      </button>
    </div>
  </div>
  <br /><br />
</template>

<script>
import { mapActions, mapGetters } from "vuex";
import NavbarAdmin from "@/components/NavbarAdmin.vue";
import UserChangeModal from "@/components/UserChangeModal.vue";

export default {
  name: "AdminUserListView",
  components: { NavbarAdmin, UserChangeModal },
  beforeCreate: function () {
    document.body.className = "admin_body";
  },
  computed: {
    ...mapGetters(["users", "totalPage"]),
  },
  data() {
    return {
      email: "",
      payload: {
        social: "",
        role: "ROLE_USER",
        page: 1,
      },
      showModal: false,
      modalData: null
    };
  },
  watch: {
    payload: {
      deep: true,
      handler(value) {
        this.fetchUsers(value);
      },
    },
  },
  methods: {
    ...mapActions(["fetchUserInfo", "fetchUsers"]),
    changeCurrentPage(page) {
      this.payload.page = page;
    },  
    changeRoleSet(event) {
      this.payload.role = event.target.value;
      this.payload.page = 1;
    },
    changeSocial(event) {
      this.payload.social = event.target.value;
      this.payload.page = 1;
    },
    openUserModal(user) {
      this.modalData = user,
      this.showModal = true
    }
  },
  created() {
    this.fetchUsers(this.payload);
  }
};
</script>

<style scoped>
@import "../../assets/css/admin.css";
</style>
