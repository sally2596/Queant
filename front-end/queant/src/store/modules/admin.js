import spring from '@/api/spring'
import router from '@/router'
import axios from 'axios'

export default {
  state: {
    users: [],
    roleUsers: [],
    socialUsers: [],
    totalPage: null
  },
  getters: {
    users: state => state.users,
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
        if (err.response.status === 403)
          alert('접근 권한이 없습니다.')
          history.back()
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
  }
}
