import spring from '@/api/spring'
import router from '@/router'
import axios from 'axios'

export default {
  state: {
    products: [],
    product: {},
    keywords: []
  },
  getters: {
    products: state => state.products,
    product: state => state.product,
    keywords: state => state.keywords
  },
  mutations: {
    SET_PRODUCTS: (state, products) => state.products = products,
    SET_PRODUCT: (state, product) => state.product = product,
    SET_KEYWORDS: (state, keywords) => state.keywords = keywords
  },
  actions: {
    fetchKeywords({ commit }) {
      axios({
        url: spring.search.keyword(),
        method: 'get'
      })
      .then(res => {
        console.log(res)
        commit('SET_KEYWORDS', res.data)
      })
      .catch(err => {
        console.log(err)
      })
    },
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
    fetchProductsByText({ commit }, text) {
      axios({
        url: spring.search.search(),
        method: 'get',
        params: {
          keyword: text
        }
      })
      .then(res => {
        console.log(res)
        commit('SET_PRODUCTS', res.data)
        router.push({ name: 'productSearch', params: { text: text }})
      })
      .catch(err => {
        console.log(err)
        commit('SET_PRODUCTS', [])
        router.push({ name: 'productSearch', params: { text: text }})
      })
    },
    fetchProductsByDepositSingleFilters({ commit }, filters) {
      axios({
        url: spring.search.deposit(),
        method: 'post',
        data: {
          amount: filters.amount,
          period: filters.period,
          isSimpleInterest: filters.isSimpleInterest,
          bank: filters.bank,
          joinway: filters.joinway,
          conditions: filters.conditions,
          bankType: filters.bankType,
          traitSet: filters.traitSet
        }
      })
      .then(res => {
        console.log(res)
        commit('SET_PRODUCTS', res.data)
      })
      .catch(err => {
        console.log(err)
      })
    },
    fetchProductsBySavingSingleFilters({ commit }, filters) {
      axios({
        url: spring.search.saving(),
        method: 'post',
        data: {
          amount: filters.amount,
          period: filters.period,
          isSimpleInterest: filters.isSimpleInterest,
          bank: filters.bank,
          joinway: filters.joinway,
          conditions: filters.conditions,
          bankType: filters.bankType,
          traitSet: filters.traitSet,
          isFixed: filters.isFixed
        }
      })
      .then(res => {
        console.log(res)
        commit('SET_PRODUCTS', res.data)
      })
      .catch(err => {
        console.log(err)
      })
    },
    fetchProductsBySavingSetFilters({ commit }, filters) {
      axios({
        url: spring.search.savings(),
        method: 'post',
        data: {
          amount: filters.amount,
          period: filters.period,
          isSimpleInterest: filters.isSimpleInterest,
          bank: filters.bank,
          joinway: filters.joinway,
          conditions: filters.conditions,
          bankType: filters.bankType,
          traitSet: filters.traitSet,
          isFixed: filters.isFixed
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