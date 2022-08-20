<template>
    <transition on name="modal">
   <div class="modal-mask">
    <div class="modal-wrapper">
     <div class="modal-container">

      <div class="modal-header">
       <slot name="header">
        <h4 style="font-family: 'jua'; margin-top: 1rem;">{{ modalData.name }}</h4>
       </slot>
      </div>
      <p class="h1 m-0"><b-icon-x-circle type="button" class="modal-close-button"
      @click="$emit('close')"/>
			</p>
      <hr>

      <div class="modal-body">
       <slot name="body">
        <div class="d-flex justify-content-between">
          <label>이메일</label>
          <p>{{ modalData.email }}</p>
        </div>
        <hr>

        <div class="d-flex justify-content-between">
          <label>성별</label>
          <p>{{ modalData.gender }}</p>
        </div>
        <hr>

        <div class="d-flex justify-content-between">
          <label>생년월일</label>
          <p>{{ modalData.birthdate }}</p>
        </div>
        <hr>

        <div class="d-flex justify-content-between">
          <label>소셜</label>
          <p v-if="this.modalData.social==='None'">QueÆnt</p>
          <p v-else>{{ modalData.social }}</p>
        </div>
        <hr>

        <div class="d-flex justify-content-between">
          <label>권한</label>
          <div>
            <label :for="`role-user-${modalData.email}`">User</label>
            <input
              v-model="credentials.role_set"
              type='checkbox'
              value='ROLE_USER'
              :id="`role-user-${modalData.email}`"
              disabled>
          </div>

          <div>
            <label :for="`role-super-${modalData.email}`">Manager</label>
            <input
              v-model="credentials.role_set"
              type='checkbox'
              value='ROLE_SUPER'
              :id="`role-super-${modalData.email}`">
          </div>

          <div>
            <label :for="`role-admin-${modalData.email}`">Admin</label>
            <input
              v-model="credentials.role_set"
              type='checkbox'
              value='ROLE_ADMIN'
              :id="`role-admin-${modalData.email}`">
          </div>
        </div>
        <hr>

        <div class="d-flex justify-content-between">
          <label>상태</label>
          <select v-model="credentials.enabled">
            <option value="true">활성화</option>
            <option value="false">비활성화</option>
          </select>
        </div>
        <br>

       </slot>
      </div>

      <div class="modal-footer">
       <slot name="footer">
        <button class="btn btn-outline-success btn-sm mx-3" @click="[checkChange(), $emit('close')]">수정</button>
       </slot>
      </div>
     </div>
    </div>
   </div>
  </transition>
</template>

<script>
import { mapActions } from 'vuex'
import { BIconXCircle } from 'bootstrap-icons-vue';

export default {
  name: 'UserChangeModal',
  components: {
		BIconXCircle
  },
  props: {
    modalData: Object
  },
  methods: {
    ...mapActions(['editRoleSet', 'editEnabled']),
    checkChange() {
      if(this.modalData.enabled === this.credentials.enabled) {
        this.editRoleSet(this.credentials)
      } else {
        this.editRoleSet(this.credentials)
        this.editEnabled(this.credentials.email)
      }
    }
  },
  data() {
    return {
      credentials: {
        email: this.modalData.email,
        role_set: this.modalData.role_set,
        enabled: this.modalData.enabled
      }
    }
  },
}
</script>

<style>

</style>