import spring from "@/api/spring"
import router from "@/router"
import axios from "axios"

export default {
  state: {
    // users: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11],
    users: [],
    roleUsers: [],
    socialUsers: [],
    roleStatus: null,
    socialStatus: null,
    // test
    // testTotalPage: null
  },
  getters: {
    users: state => state.users,
    // test
    // testTotalPage: state => state.testTotalPage
  },
  mutations: {
    SET_USERS: (state, users) => state.users = users,
    SET_ROLE_USERS: (state, roleUsers) => state.roleUsers = roleUsers,
    SET_SOCIAL_USERS: (state, socialUsers) => state.socialUsers = socialUsers,
    SET_ROLE_STATUS: (state, res) => state.roleStatus = res,
    SET_SOCIAL_STATUS: (state, res) => state.socialStatus = res,
    // test
    // SET_TEST_TOTAL_PAGE: (state, res) => state.testTotalPage = res
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
        // test
        // commit('SET_TEST_TOTAL_PAGE', res.data.total_page)
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
        console.log(res)
        router.go()

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
        router.go()
      })
      .catch(err => {
        console.log(err)
      })
    }
  }
}