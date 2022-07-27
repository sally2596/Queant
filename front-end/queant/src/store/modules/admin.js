import spring from "@/api/spring"
import axios from "axios"

export default {
  state: {
    users: [],
    roleUsers: [],
    socialUsers: []
  },
  getters: {
    users: state => state.users,
    roleUsers: state => state.roleUsers,
    socialUsers: state => state.socialUsers
  },
  mutations: {
    SET_USERS: (state, users) => state.users = users,
    SET_ROLE_USERS: (state, roleUsers) => state.roleUsers = roleUsers,
    SET_SOCIAL_USERS: (state, socialUsers) => state.socialUsers = socialUsers
  },
  actions: {
    fetchUsers({ commit }) {
      axios({
        url: spring.member.roles(),
        method: 'get',
        params: {
          role: 'ROLE_USER',
          page: 1
        }
      })
      .then(res => {
        commit('SET_USERS', res.data.member_dto_list)
      })
      .catch(err => {
        console.log(err)
      })
    },
    
    fetchUsersThroughRole({ commit, state }, event) {
      axios({
        url: spring.member.roles(),
        method: 'get',
        params: {
          role: event.target?event.target.value:event,
          page: 1
        }
      })
      .then(res => {
        commit('SET_ROLE_USERS', res.data.member_dto_list)
        if (state.socialUsers.length !== 0) {
          const newUsers = []
          state.socialUsers.forEach(socialUser => {
            state.roleUsers.forEach(roleUser => {
              if (roleUser.member_id === socialUser.member_id)
                newUsers.push(roleUser)
            })
          })
          commit('SET_USERS', newUsers)
        } else {
          commit('SET_USERS', res.data.member_dto_list)
        }
      })
      .catch(err => {
        console.log(err)
      })
    },

    fetchUsersThroughSocial({ commit, state }, event) {
      axios({
        url: spring.member.social(),
        method: 'get',
        params: {
          social: event.target.value,
          page: 1
        }
      })
      .then(res => {
        commit('SET_SOCIAL_USERS', res.data.member_dto_list)
        if (state.roleUsers.length !== 0) {
          const newUsers = []
          state.roleUsers.forEach(roleUser => {
            state.socialUsers.forEach(socialUser => {
              if (roleUser.member_id === socialUser.member_id)
                newUsers.push(socialUser)
            })
          })
          commit('SET_USERS', newUsers)
        } else {
          commit('SET_USERS', res.data.member_dto_list)
        }
      })
      .catch(err => {
        console.log(err)
      })
    },
    
    editRoleSet({ commit }, credentials) {
      axios({
        url: spring.member.roles(),
        method: 'put',
        data: {
          email: credentials.email,
          roles: credentials.role_set
        }
      })
      .then(res => {
        console.log(res)
      })
      .catch(err => {
        console.log(err)
      })
    }
  }
}