import spring from "@/api/spring"
import axios from "axios"

export default {
  state: {
  },
  getters: {
  },
  mutations: {
  },
  actions: {
    searchProduct({ commit }, keyword) {
      console.log(keyword)
      axios({
        url: spring.search.search(),
        method: 'get',
        params: {
          keyword: keyword
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