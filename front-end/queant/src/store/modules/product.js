import spring from '@/api/spring'
import router from '@/router'
import axios from 'axios'

export default {
  state: {
    products: [],
    searchProducts: [],
    product: {},
    keywords: [],
    cart: []
  },
  getters: {
    products: state => state.products,
    searchProducts: state => state.searchProducts,
    product: state => state.product,
    keywords: state => state.keywords,
    cart: state => state.cart
  },
  mutations: {
    SET_PRODUCTS: (state, products) => state.products = products,
    SET_SEARCH_PRODUCTS: (state, products) => state.searchProducts = products,
    SET_PRODUCT: (state, product) => state.product = product,
    SET_KEYWORDS: (state, keywords) => state.keywords = keywords,
    CLEAR_CART: state => state.cart = [],
    PUSH_PRODUCT_TO_CART(state, payload) {
			let product = payload.product
			if (state.cart.length===10) {
				alert('장바구니에는 최대 10개의 상품만 담을 수 있습니다.');
			} else if (state.cart.find(cartItem => cartItem.product.product_id === product.product_id))
        alert('이미 장바구니에 담긴 상품입니다.')
      else {
        state.cart.push(payload)
        console.log(`${product.product_id}번 상품을 장바구니에 추가했습니다.`)
        if (confirm('장바구니에 담겼습니다. 장바구니로 이동하시겠습니까?') === true) {
          router.push({ name: 'productCart' })
        } else {
          return
        }
      }
    },
    // 카트에서 프로덕트 삭제
    POP_PRODUCT_FROM_CART(state, product) {
        let deleteproductIdx = state.cart.indexOf('product')
        state.cart.splice(deleteproductIdx, 1)
        alert(`${product.product.name} 상품을 장바구니에서 제거했습니다.`)
      }
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
        router.push({ name: 'productSearchResult', params: { text: text }})
      })
      .catch(err => {
        console.log(err)
        commit('SET_PRODUCTS', [])
        router.push({ name: 'productSearchResult', params: { text: text }})
      })
    },
    fetchProductsByDepositFilters({ commit }, filters) {
      axios({
        url: spring.search.deposit(),
        method: 'post',
        data: {
          amount: filters.amount?filters.amount:null,
          bank: filters.bank?filters.bank:[],
          bank_type: filters.bankType?filters.bankType:[],
          conditions: filters.conditions?filters.conditions:[],
          is_simple_interest: filters.isSimpleInterest?filters.isSimpleInterest:null,
          joinway: filters.joinway?filters.joinway:[],
          period: filters.period?filters.period:null,
          trait_set: filters.traitSet?filters.traitSet:[]
        },
      })
      .then(res => {
        console.log(res)
        for (const product of res.data) {
          product.amount = filters.amount?filters.amount:null
        }
        commit('SET_PRODUCTS', res.data)
        router.push({ name: 'productDepositResult' })
      })
      .catch(err => {
        console.log(err)
        if (err.response.status === 500) {
          alert('필수 입력사항(*)을 확인해주세요.')
        } else if (err.response.status === 404) {
          alert('해당 조건의 상품이 존재하지 않습니다. 다른 조건으로 검색해주세요.')
        }
      })
    },
    fetchProductsBySavingFilters({ commit }, filters) {
      axios({
        url: spring.search.saving(),
        method: 'post',
        data: {
          amount: filters.amount?filters.amount:null,
          bank: filters.bank?filters.bank:[],
          bank_type: filters.bankType?filters.bankType:[],
          conditions: filters.conditions?filters.conditions:[],
          is_simple_interest: filters.isSimpleInterest?filters.isSimpleInterest:null,
          joinway: filters.joinway?filters.joinway:[],
          period: filters.period?filters.period:null,
          trait_set: filters.traitSet?filters.traitSet:[],
          is_fixed: filters.isFixed?filters.isFixed:null
        }
      })
      .then(res => {
        console.log(res)
        for (const product of res.data) {
          product.amount = filters.amount?filters.amount:null
        }
        commit('SET_PRODUCTS', res.data)
        router.push({ name: 'productSavingResult' })
      })
      .catch(err => {
        console.log(err)
        if (err.response.status === 500) {
          alert('필수 입력사항(*)을 확인해주세요.')
        }
        else if (err.response.status === 404) {
          alert('해당 조건의 상품이 존재하지 않습니다. 다른 조건으로 검색해주세요.')
        }
      })
    }
  }
}