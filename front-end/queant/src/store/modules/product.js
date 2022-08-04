import spring from '@/api/spring'
import router from '@/router'
import axios from 'axios'

export default {
  state: {
    products: [],
    product: {}
  },
  getters: {
    products: state => state.products,
    product: state => state.product
  },
  mutations: {
    SET_PRODUCTS: (state, products) => state.products = products,
    SET_PRODUCT: (state, product) => state.product = product
  },
  actions: {
    fetchProduct({ commit }, productId) {
      axios({
        url: spring.product.detail(productId),
        method: 'get',
        params: {
          productId: productId
        }
      })
      .then(res => {
        console.log(res)
        commit('SET_PRODUCT', res.data)
      })
      .catch(err => {
        console.log(err)
      })
    },
    searchProduct({ commit }, keyword) {
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