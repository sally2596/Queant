import spring from "@/api/spring"
import router from "@/router"
import axios from "axios"

export default {
  state: {

  },
  getters: {

  },
  mutations: {

  },
  actions: {
    fetchBanks({ commit }) {
      axios({
        url: spring.bank.bank(),
        method: 'get'
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