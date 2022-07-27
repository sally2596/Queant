import spring from "@/api/spring"
import axios from "axios"

export default {
  state: {
    usera: {},
    users: [],
    roleUsers: [],
    socialUsers: [],
    roleStatus: null,
    socialStatus: null
  },
  getters: {
    usera: state => state.usera,
    users: state => state.users
  },
  mutations: {
    SET_USER: (state, usera) => state.usera = usera,
    SET_USERS: (state, users) => state.users = users,
    SET_ROLE_USERS: (state, roleUsers) => state.roleUsers = roleUsers,
    SET_SOCIAL_USERS: (state, socialUsers) => state.socialUsers = socialUsers,
    SET_ROLE_STATUS: (state, res) => state.roleStatus = res,
    SET_SOCIAL_STATUS: (state, res) => state.socialStatus = res
  },
  actions: {
    fetchUser({ commit }, res) {
      commit('SET_USER', res)
    },

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
          role: event.target.value,
          page: 1
        }
      })
      .then(res => {
        commit('SET_ROLE_USERS', res.data.member_dto_list)
        commit('SET_ROLE_STATUS', event.target.value)
        if (state.socialStatus !== null) {
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
        commit('SET_SOCIAL_STATUS', event.target.value)
        if (state.roleStatus !== null) {
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

    editEnabled({ commit }, email) {
      axios({
        url: spring.member.status(),
        method: 'put',
        data: {
          email: email
        }
      })
      .then(res => {
        axios({
          url: spring.member.info(),
          method: 'post',
          data: {
            email: email
          }
        })
        .then(res => {
          commit('SET_USER', res.data)
        })
        .catch(err => {
          console.log(err)
        })
        console.log(res)
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
        commit('SET_USER', res.data)
        console.log(res)
      })
      .catch(err => {
        console.log(err)
      })
    }
  }
}