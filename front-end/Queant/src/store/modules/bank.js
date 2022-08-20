import spring from '@/api/spring'
import axios from 'axios'

export default {
  state: {
    bank: {},
    theFirstBanks: [],
    theSecondBanks: [],
    theThirdBanks: []
    // banks: [],
  },
  getters: {
    bank: state => state.bank,
    theFirstBanks: state => state.theFirstBanks,
    theSecondBanks: state => state.theSecondBanks,
    theThirdBanks: state => state.theThirdBanks
    // banks: state => state.banks,
  },
  mutations: {
    SET_BANK: (state, bank) => state.bank = bank,
    SET_BANKS(state, banks) {
      for (let bank of banks) {
        if (bank.scode_id === 'C001') {
          if (state.theFirstBanks.find(theFirstBank => theFirstBank.bank_id === bank.bank_id))
            console.log(theFirstBank)
          else
            state.theFirstBanks.push(bank)
        } else if (bank.scode_id === 'C002') {
          if (state.theSecondBanks.find(theSecondBank => theSecondBank.bank_id === bank.bank_id))
            console.log(theSecondBank)
          else
            state.theSecondBanks.push(bank)
        } else if (bank.scode_id === 'C003') {
          if (state.theThirdBanks.find(theThirdBank => theThirdBank.bank_id === bank.bank_id))
            console.log(theThirdBank)
          else
            state.theThirdBanks.push(bank)
        }
      }
    },
        // SET_BANKS: (state, banks) => state.banks = banks,
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