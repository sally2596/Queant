<template>

  <div>
    <table class="table table-hover">
      <thead>
        <tr class="text-center">
          <th scope="col">이름</th>
          <th scope="col">이메일</th>
          <th scope="col">성별</th>
          <th scope="col">생년월일</th>
          <th scope="col">가입유형</th>
          <th scope="col">권한</th>
          <th scope="col">계정 상태</th>
        </tr>
      </thead>

      <tbody>
        <tr scope="row">
          <td class="col-1 text-center">{{ user.name }}</td>
          <td class="col-1 text-center">{{ user.email }}</td>
          <td class="col-1 text-center">{{ user.gender }}</td>
          <td class="col-1 text-center">{{ user.birthdate }}</td>
          <td class="col-1 text-center">{{ user.social }}</td>
          <td class="col-1 text-center">
            <div>
              <label :for="`role-user-${user.member_id}`">ROLE_USER</label>
              <input
                v-model="credentials.role_set"
                type='checkbox'
                value='ROLE_USER'
                :id="`role-user-${user.member_id}`"
                disabled>
            </div>

            <div>
              <label :for="`role-super-${user.member_id}`">ROLE_SUPER</label>
              <input
                v-model="credentials.role_set"
                type='checkbox'
                value='ROLE_SUPER'
                :id="`role-super-${user.member_id}`">
            </div>

            <div>
              <label :for="`role-admin-${user.member_id}`">ROLE_ADMIN</label>
              <input
                v-model="credentials.role_set"
                type='checkbox'
                value='ROLE_ADMIN'
                :id="`role-admin-${user.member_id}`">
            </div>

            <button @click="editRoleSet(credentials)">수정</button>
          </td>
          <td class="col-1 text-center">
            <div>
              {{ user.enabled }}
              <button v-if="user.enabled" @click="editEnabled(user.email)">비활성화</button>
              <button v-else @click="editEnabled(user.email)">활성화</button>
            </div>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- <div>
      이름: {{ user.name }}
    </div>
    
    <div>
      이메일 {{ user.email }} -->
    <!-- </div>

    <div>
      성별 {{ user.gender }}
    </div>

    <div>
      생년월일 {{ user.birthdate }}
    </div>

    <div>
      가입유형 {{ user.social }}
    </div> -->

    <!-- ROLE_USER: default, ROLE_SUPER 또는 ROLE_ADMIN 체크/해제하고 수정 누르면 반영 -->

    <!-- <div>
      <input
        v-model="credentials.role_set"
        type='checkbox'
        value='ROLE_USER'
        :id="`role-user-${user.member_id}`"
        disabled>
      <label :for="`role-user-${user.member_id}`">ROLE_USER</label>
      
      <input
        v-model="credentials.role_set"
        type='checkbox'
        value='ROLE_SUPER'
        :id="`role-super-${user.member_id}`">
      <label :for="`role-super-${user.member_id}`">ROLE_SUPER</label>
      
      <input
        v-model="credentials.role_set"
        type='checkbox'
        value='ROLE_ADMIN'
        :id="`role-admin-${user.member_id}`">
      <label :for="`role-admin-${user.member_id}`">ROLE_ADMIN</label>
      <button @click="editRoleSet(credentials)">수정</button>
      
    </div> -->

    <!-- <div>
      계정 활성화 여부 {{ user.enabled }}
      <button v-if="user.enabled" @click="editEnabled(user.email)">비활성화</button>
      <button v-else @click="editEnabled(user.email)">활성화</button>
    </div> -->
    <hr>
  </div>

</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'AdminUserItem',
  props: {
    user: Object
  },
  data() {
    return {
      credentials: {
        email: this.user.email,
        role_set: this.user.role_set,
      }
    }
  },
  methods: {
    ...mapActions(['editRoleSet', 'editEnabled'])
  }
}
</script>

<style>

</style>