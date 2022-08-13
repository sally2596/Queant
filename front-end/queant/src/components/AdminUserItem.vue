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
              <label :for="`role-user-${user.email}`">ROLE_USER</label>
              <input
                v-model="credentials.role_set"
                type='checkbox'
                value='ROLE_USER'
                :id="`role-user-${user.email}`"
                disabled>
            </div>

            <div>
              <label :for="`role-super-${user.email}`">ROLE_SUPER</label>
              <input
                v-model="credentials.role_set"
                type='checkbox'
                value='ROLE_SUPER'
                :id="`role-super-${user.email}`">
            </div>

            <div>
              <label :for="`role-admin-${user.email}`">ROLE_ADMIN</label>
              <input
                v-model="credentials.role_set"
                type='checkbox'
                value='ROLE_ADMIN'
                :id="`role-admin-${user.email}`">
            </div>

            <button @click="editRoleSet(credentials)">수정</button>
          </td>
          <td class="col-1 text-center">
            <div>
              {{ memberStatus }}
              <button @click="editEnabled(user.email)">전환</button>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
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
  computed: {
    memberStatus() {
      if (this.user.enabled)
        return '활성화'
      else
        return '비활성화'
    }
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