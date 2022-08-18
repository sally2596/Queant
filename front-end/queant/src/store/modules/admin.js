import spring from '@/api/spring'
import router from '@/router'
import axios from 'axios'

export default {
  state: {
    users: [],
    // totalPage: null,
    // roleUsers: [],
    // socialUsers: []
  },
  getters: {
    users: state => state.users,
    // totalPage: state => state.totalPage
  },
  mutations: {
    SET_USERS: (state, users) => state.users = users,
    // SET_TOTAL_PAGE: (state, res) => state.totalPage = res
  },
  actions: {
    // 어드민이 유저 관리를 위해 유저 정보를 가져오는 요청
    fetchUsers({ dispatch, commit, getters }, payload) {
      // 리프레쉬나 액세스토큰이 만료됐으면 재발급 요청
      if (getters.isRefreshTokenExpired || getters.isAccessTokenExpired)
        dispatch('updateAccessToken')

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
        console.log(`${payload.page} 페이지`)
        console.log(res.data)
        commit('SET_USERS', res.data.member_dto_list)
        commit('SET_TOTAL_PAGE', res.data.total_page)
      })
      .catch(err => {
        console.log(err)
        if (err.response.status === 403) {
          alert('접근 권한이 없습니다.')
          history.back()
        }
      })
    },
    // 회원의 계정 상태가 활성화면 => 비활성화, 비활성화면 => 활성화
    editEnabled({ dispatch, getters }, email) {
      // 리프레쉬나 액세스토큰이 만료됐으면 재발급 요청
      if (getters.isRefreshTokenExpired || getters.isAccessTokenExpired)
        dispatch('updateAccessToken')

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
    // 회원의 계정 권한을 변경
    editRoleSet({ dispatch, getters }, { email, role_set }) {
      // 리프레쉬나 액세스토큰이 만료됐으면 재발급 요청
      if (getters.isRefreshTokenExpired || getters.isAccessTokenExpired)
        dispatch('updateAccessToken')
        
      if (role_set.length === 2 && role_set[1] === 'ROLE_ADMIN') {
        role_set = ['ROLE_USER', 'ROLE_SUPER', 'ROLE_ADMIN']
      }
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
