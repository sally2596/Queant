import spring from "@/api/spring"
import axios from "axios"

export default {
  state: {
    banks: [],
    bank: {}
  },
  getters: {
    banks: state => state.banks,
    bank: state => state.bank
  },
  mutations: {
    SET_BANKS: (state, banks) => state.banks = banks,
    SET_BANK: (state, bank) => state.bank = bank
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
    fetchBank({ commit }, bankId) {
      axios({
        url: spring.bank.bank(bankId),
        method: 'get'
      })
      .then(res => {
        console.log(res)
        commit('SET_BANK', res.data.bank)
        commit('SET_PRODUCTS', res.data.product_list)
      })
      .catch(err => {
        console.log(err)
      })
    }
  }
}