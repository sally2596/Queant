import spring from "@/api/spring"
import router from "@/router"
import axios from "axios"

export default {
  state: {
    banks: [],
    bank: {},
    products: []
  },
  getters: {
    banks: state => state.banks,
    bank: state => state.bank,
    products: state => state.products
  },
  mutations: {
    SET_BANKS: (state, banks) => state.banks = banks,
    SET_BANK: (state, bank) => state.bank = bank,
    SET_PRODUCTS: (state, products) => state.products = products 
  },
  actions: {
    fetchBanks({ commit }) {
      axios({
        url: spring.bank.banks(),
        method: 'get'
      })
      .then(res => {
        console.log(res)
        commit('SET_BANKS', res.data)
      })
      .catch(err => {
        console.log(err)
      })
    },
    // fetchBank({ commit }, bankId) {
    //   axios({
    //     url: spring.bank.bank(bankId),
    //     method: 'get'
    //   })
    //   .then(res => {
        // console.log(res)
        // commit('SET_BANK', res.data.bank)
        // commit('SET_PRODUCT_LIST', res.data.product_list)
    //   })
    //   .catch(err => {
    //     console.log(err)
    //   })
    // }
  }
}