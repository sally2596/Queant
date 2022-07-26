<template>
  <div>
    <admin-user-item
      v-for="user in users"
      :key="user.member_id"
      :user="user">
    </admin-user-item>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import AdminUserItem from '@/components/AdminUserItem.vue'

export default {
  name: 'AdminUserListView',
  components: { AdminUserItem },
  computed: {
    ...mapGetters(['users'])
  },
  methods: {
    ...mapActions(['fetchUsers'])
  },
  mounted() {
    this.fetchUsers()
  }
}
</script>

<style>

</style>