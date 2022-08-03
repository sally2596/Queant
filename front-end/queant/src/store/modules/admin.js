import spring from "@/api/spring"
import router from "@/router"
import axios from "axios"

export default {
  state: {
    users: [],
    roleUsers: [],
    socialUsers: [],
    totalPage: null
  },
  getters: {
    users: state => state.users,
    // test
    totalPage: state => state.totalPage
  },
  mutations: {
    SET_USERS: (state, users) => state.users = users,
    SET_TOTAL_PAGE: (state, res) => state.totalPage = res
  },
  actions: {
    fetchUsers({ commit, getters }, payload) {
      console.log(`${payload.page} 페이지`)
      axios({
        url: spring.member.list(),
        method: 'get',
        headers: getters.authHeader,
        params: {
          social: payload.social,
          memberRole: payload.role,
          page: payload.page
        }
      })
      .then(res => {
        commit('SET_USERS', res.data.member_dto_list)
        commit('SET_TOTAL_PAGE', res.data.total_page)
      })
      .catch(err => {
        console.log(err)
      })
    },
    
    editEnabled({ getters }, email) {
      axios({
        url: spring.member.status(),
        method: 'put',
        headers: getters.authHeader,
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
    
    editRoleSet({ getters }, { email, role_set }) {
      if (role_set.length === 2 && role_set[1] === 'ROLE_ADMIN') {
        role_set = ['ROLE_USER', 'ROLE_SUPER', 'ROLE_ADMIN']
      }
      console.log(role_set)
      axios({
        url: spring.member.roles(),
        method: 'put',
        headers: getters.authHeader,
        data: {
          email: email,
          roles: role_set
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
    // fetchUsers({ commit }) {
    //   axios({
    //     url: spring.member.roles(),
    //     method: 'get',
    //     params: {
    //       role: 'ROLE_USER',
    //       page: 1
    //     }
    //   })
    //   .then(res => {
    //     commit('SET_USERS', res.data.member_dto_list)
    //   })
    //   .catch(err => {
    //     console.log(err)
    //   })
    // },

    // fetchUsersThroughRole({ commit, state }, event) {
    //   axios({
    //     url: spring.member.roles(),
    //     method: 'get',
    //     params: {
    //       role: event.target.value,
    //       page: 1
    //     }
    //   })
    //   .then(res => {
    //     commit('SET_ROLE_USERS', res.data.member_dto_list)
    //     commit('SET_ROLE_STATUS', event.target.value)
    //     if (state.socialStatus !== null) {
    //       const newUsers = []
    //       state.socialUsers.forEach(socialUser => {
    //         state.roleUsers.forEach(roleUser => {
    //           if (roleUser.member_id === socialUser.member_id)
    //             newUsers.push(roleUser)
    //         })
    //       })
    //       commit('SET_USERS', newUsers)
    //     } else {
    //       commit('SET_USERS', res.data.member_dto_list)
    //     }
    //   })
    //   .catch(err => {
    //     console.log(err)
    //   })
    // },

    // fetchUsersThroughSocial({ commit, state }, event) {
    //   axios({
    //     url: spring.member.social(),
    //     method: 'get',
    //     params: {
    //       social: event.target.value,
    //       page: 1
    //     }
    //   })
    //   .then(res => {
    //     commit('SET_SOCIAL_USERS', res.data.member_dto_list)
    //     commit('SET_SOCIAL_STATUS', event.target.value)
    //     if (state.roleStatus !== null) {
    //       const newUsers = []
    //       state.roleUsers.forEach(roleUser => {
    //         state.socialUsers.forEach(socialUser => {
    //           if (roleUser.member_id === socialUser.member_id)
    //             newUsers.push(socialUser)
    //         })
    //       })
    //       commit('SET_USERS', newUsers)
    //     } else {
    //       commit('SET_USERS', res.data.member_dto_list)
    //     }
    //   })
    //   .catch(err => {
    //     console.log(err)
    //   })
    // },
  }
}
