import spring from '@/api/spring'
import router from '@/router'
import axios from 'axios'

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
    fetchProduct({ commit }, productId) {
      axios({
        url: spring.product.detail(productId),
        method: 'get'
      })
    },
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
        commit('SET_PRODUCTS', res.data)
        router.push({ name: 'productSearch', params: { keyword: keyword }})
      })
      .catch(err => {
        console.log(err)
        commit('SET_PRODUCTS', [])
        router.push({ name: 'productSearch', params: { keyword: keyword }})
      })
    }
  }
}