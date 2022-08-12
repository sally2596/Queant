import spring from '@/api/spring'
import router from '@/router'
import axios from 'axios'

export default {
  state: {
    portfolio: {},
    portfolios: [],
    comparisonportfolios: []
    },
  getters: {
    portfolio: state => state.portfolio,
    portfolios: state => state.portfolios,
    comparisonportfolios: state => state.comparisonportfolios
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

    PUSH_CPORTFOLIO_TO_COMPARISONPORTFOLIOS(state, value) {
      let member_id = value.member_id
      if (state.comparisonportfolios.length === 0) {
        state.comparisonportfolios.push({
          "member_id" : member_id,
          "cportfolio_cnt" : 1,
          "products" : []
        }),
        console.log('새로운 가상 포트폴리오가 추가되었습니다.')
      } else if (state.comparisonportfolios.length < 4){
        state.comparisonportfolios.push({
          "member_id" : member_id,
          "cportfolio_cnt" : (state.comparisonportfolios.length) +1,
          "products" : []
        }),
        console.log('새로운 가상 포트폴리오가 추가되었습니다.')
      } else if ( state.comparisonportfolios.length >= 4) {
        alert('더 이상 가상 포트폴리오를 만들 수 없습니다.')
      }
    },

    PUSH_PRODUCT_TO_CPORTFOLIO(state, value) {
        let portfolioNo = value[0]
        let product = value[1]
        let cportfolios = state.comparisonportfolios
        if (cportfolios[portfolioNo-1].products.find(cportfolioItem => cportfolioItem.product.product_id === product.product.product_id)) {
          alert('이미 포트폴리오에 있는 상품입니다.')
        } else {
          state.comparisonportfolios[portfolioNo-1].products.push(product)
        console.log(`${portfolioNo}번 포트폴리오에 상품이 담겼습니다.`)
        }
    },

    POP_PRODUCT_FROM_CPORTFOLIO(state, Idxs) {
      let portfolioIdx = Idxs[0]
      let productIdx = Idxs[1]

      state.comparisonportfolios[portfolioIdx].product.splice(productIdx, 1)
      console.log(`${portfolioIdx+1}번 포트폴리오의 ${productIdx+1}번 상품을 삭제했습니다.`)
    },

    POP_CPORTFOLIO_FROM_CPORTFOLIOS(state, cportfolioIdx) {
      state.comparisonportfolios.splice(cportfolioIdx-1, 1)
      console.log(`${cportfolioIdx}번 포트폴리오를 삭제했습니다.`)
    },

    CLEAR_CPORTFOLIOS(state) {
      state.comparisonportfolios = []
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
    editPortfolio({ commit, getters }, product) {
      axios({
        url: spring.portfolio.portfolio(),
        method: 'put',
        data: {
          member_id: getters.userInfo.member_id,
          portfolio_dto_list: [
            {
              amount: product.amount,
              condition_ids: product.condition_ids,
              start_date: product.start_date,
              end_date: product.end_date,
              option_id: product.option_id,
              portfolio_no: product.portfolio_no,
              product_id: product.product_id
            }
          ]
        }
      })
      .then(res => {
        console.log(res)
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
    addProductToPortfolio({ getters }, payload) {
      axios({
        url: spring.portfolio.portfolio(),
        method: 'post',
        data: {
          member_id: getters.userInfo.member_id,
          portfolio_dto_list: [
            {
              amount: getters.filters.amount,
              condition_ids: getters.filters.conditions,
              start_date: payload.start_date,
              end_date: payload.end_date,
              option_id: payload.option_id,
              portfolio_no: payload.portfolio_no,
              product_id: payload.product_id
            }
          ]
        }
      })
      .then(res => {
        console.log(res)
        console.log('포트폴리오에 상품이 등록됐습니다.')
      })
      .catch(err => {
        console.log(err)
      })
    }
  }
};
