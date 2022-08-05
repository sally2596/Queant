import spring from '@/api/spring'
import router from '@/router'
import axios from 'axios'

export default {
  state: {
    portfolio: [],
    customPortfolio: []
  },
  getters: {
    portfolio: state => state.portfolio
  },
  mutations: {
    SET_PORTFOLIO: (state, portfolio) => state.portfolio = portfolio
  },
  actions: {
    fetchPortfolio({ commit, getters }) {
      axios({
        url: spring.portfolio.posession(),
        method: 'post',
        data: {
          email: getters.userInfo.email
        }
      })
      .then(res => {
        console.log(res)
        commit('SET_PORTFOLIO', res.data.portfolio_list)
      })
      .catch(err => {
        console.log(err)
      })
    }
  }
};
