import spring from "@/api/spring"
import axios from "axios"

export default {
  state: {},
  getters: {},
  mutations: {},
  actions: {
    getAllUserInfo() {
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
      })
      .catch(err => {
        console.log(err)
      })
    }
  }
}