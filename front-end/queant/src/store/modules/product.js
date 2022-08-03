import spring from "@/api/spring"
import router from "@/router"
import axios from "axios"

export default {
  state: {
    products: []
  },
  getters: {
    products: state => state.products
  },
  mutations: {
    SET_PRODUCTS: (state, products) => state.products = products
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
        commit('SET_PRODUCTS', res.data)
        console.log(res)
        router.push({ name: 'productSearch', params: { keyword: keyword }})
      })
      .catch(err => {
        console.log(err)
      })
    }
  }
}