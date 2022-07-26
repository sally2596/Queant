import spring from "@/api/spring"
import axios from "axios"

export default {
  state: {
    users: []
  },
  getters: {
    users: state => state.users
  },
  mutations: {
    SET_USERS: (state, users) => state.users = users
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
        console.log(res)
        commit('SET_USERS', res.data.member_dto_list)
      })
      .catch(err => {
        console.log(err)
      })
    }
  }
}