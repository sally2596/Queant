import spring from '@/api/spring'
import router from '@/router'
import axios from 'axios'

export default {
  state: {
    portfolio: [],
    portfolios: []
  },
  getters: {
    portfolio: state => state.portfolio,
    portfolios: state => state.portfolios
  },
  mutations: {
    SET_PORTFOLIO: (state, portfolio) => state.portfolio = portfolio,
    PUSH_PRODUCT_TO_PORTFOLIO(state, value) {
      let portfolioNo = value.portfolioNo

      if (portfolioNo > state.portfolios.length) {
        state.portfolios.push([value])
        console.log('새로운 포트폴리오에 상품이 담겼습니다.')
      } else {
        state.portfolios[portfolioNo-1].push(value)
        console.log('기존 포트폴리오에 상품을 추가했습니다.')
      }
    },
    POP_PORTFOLIO_FROM_PORTFOLIOS(state, portfolioIdx) {
      state.portfolios.splice(portfolioIdx, 1)
      console.log(`${portfolioIdx+1}번 포트폴리오를 삭제했습니다.`)
    },
    POP_PRODUCT_FROM_PORTFOLIO(state, Idxs) {
      let portfolioIdx = Idxs[0]
      let productIdx = Idxs[1]

      state.portfolios[portfolioIdx].splice(productIdx, 1)
      console.log(`${portfolioIdx+1}번 포트폴리오의 ${productIdx+1}번 상품을 삭제했습니다.`)
    }
  },
  actions: {
    deletePortfolio({ dispatch, getters }, portfolio_id) {
      axios({
        url: spring.portfolio.single(),
        method: 'delete',
        data: {
          member_id: getters.userInfo.member_id,
          portfolio_id: portfolio_id
        }
      })
      .then(res => {
        console.log(res)
        dispatch('fetchMyPortfolio')
      })
      .catch(err => {
        console.log(err)
      })
    },
    editPortfolio({ dispatch, getters }, payload) {
      axios({
        url: spring.portfolio.single(),
        method: 'put',
        data: {
          member_id: getters.userInfo.member_id,
          portfolio_id: portfolioId,
          portfolio_dto: {
            amount: 100000,
            condition_ids: [479, 480],
            start_date: '2020-01-01',
            end_date: '2020-12-31',
            option_id: 2992,
            portfolio_no: 0,
            product_id: 1,
            portfolio_id: payload.portfolio_id
          }
        }
      })
      .then(res => {
        console.log(res)
        dispatch('fetchMyPortfolio')
      })
      .catch(err => {
        console.log(err)
      })
    },
    fetchMyPortfolio({ commit, getters }) {
      axios({
        url: spring.portfolio.posession(),
        method: 'post',
        data: {
          member_id: getters.userInfo.member_id
        }
      })
      .then(res => {
        console.log(res)
        commit('SET_PORTFOLIO', res.data.portfolio_list)
      })
      .catch(err => {
        console.log(err)
      })
    },
    addProductToPortfolio({ dispatch, getters }, payload) {
      axios({
        url: spring.portfolio.portfolio(),
        method: 'post',
        data: {
          member_id: getters.userInfo.member_id,
          portfolio_dto_list: [
            {
              amount: 100000,
              condition_ids: [],
              start_date: '2020-01-01',
              end_date: '2020-12-31',
              option_id: 1,
              portfolio_no: 0,
              product_id: 1
            }
          ]
        }
      })
      .then(res => {
        console.log(res)
        console.log('포트폴리오에 상품이 등록됐습니다.')
        dispatch('fetchMyPortfolio')
      })
      .catch(err => {
        console.log(err)
      })
    }
  }
};
