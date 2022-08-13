import spring from '@/api/spring'
import router from '@/router'
import axios from 'axios'

export default {
  state: {
    portfolio: [],
    customProducts: [],
    portfolios: [],
    comparisonportfolios: [],
    newlyAddedPortfolio:[],
    },
  getters: {
    portfolio: state => state.portfolio,
    customProducts: state => state.customProducts,
    portfolios: state => state.portfolios,
    comparisonportfolios: state => state.comparisonportfolios
  },
  mutations: {
    SET_PORTFOLIO: (state, portfolio) => state.portfolio = portfolio,
    SET_CUSTOM_PRODUCTS: (state, customProducts) => state.customProducts = customProducts,

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
        state.newlyAddedPortfolio.push(1);
        state.comparisonportfolios.push({
          "member_id" : member_id,
          "cportfolio_cnt" : 1,
          "products" : []
        }),
        console.log('새로운 가상 포트폴리오가 추가되었습니다.')
      } else if (state.comparisonportfolios.length <= 4) {
        state.newlyAddedPortfolio.push(state.comparisonportfolios.length+1)
        state.comparisonportfolios.push({
          "member_id" : member_id,
          "cportfolio_cnt" : (state.comparisonportfolios.length) +1,
          "products" : []
        }),
        console.log('새로운 가상 포트폴리오가 추가되었습니다.')
      } else if ( state.comparisonportfolios.length > 4) {
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

    POP_PRODUCT_FROM_CPORTFOLIO(state, value) {
      let portfolioNo = value[0]
      let FindProduct = value[1]
      let cportfolios = state.comparisonportfolios
      let item = cportfolios[portfolioNo-1].products.indexOf('FindProduct')

      cportfolios[portfolioNo-1].products.splice(item, 1)
      console.log(`${portfolioNo}번 포트폴리오의 ${FindProduct.product.name} 상품을 삭제했습니다.`)
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
    deleteCustomProduct({ dispatch }, product_id) {
      console.log(product_id)
      axios({
        url: spring.portfolio.custom(),
        method: 'delete',
        data: {
          product_id: product_id
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
    updateCustomProduct({ dispatch }, payload) {
      payload.fixed_rsrv = false
      axios({
        url: spring.portfolio.custom(),
        method: 'put',
        data: payload
      })
      .then(res => {
        console.log(res)
        alert('사용자정의 상품 수정 완료')
        dispatch('fetchMyPortfolio')
      })
      .catch(err => {
        console.log(err)
      })
    },
    addCustomProduct({ dispatch, getters }, payload) {
      console.log(payload)
      axios({
        url: spring.portfolio.custom(),
        method: 'post',
        data: {
          member_id: getters.userInfo.member_id,
          custom_product_dto: {
            amount: payload.amount,
            base_rate: payload.base_rate,
            deposit: payload.deposit,
            end_date: payload.end_date,
            etc: payload.etc,
            fixed_rsrv: payload.fixed_rsrv,
            institution_name: payload.institution_name,
            product_name: payload.product_name,
            special_rate: payload.special_rate,
            start_date: payload.start_date
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
    deletePortfolio({ dispatch }, portfolio_id) {
      console.log(portfolio_id)
      axios({
        url: spring.portfolio.single(),
        method: 'delete',
        data: {
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
    editPortfolio({ dispatch }, payload) {
      axios({
        url: spring.portfolio.single(),
        method: 'put',
        data: {
          portfolio_dto: {
            amount: payload.amount,
            condition_ids: payload.condition_ids,
            start_date: payload.start_date,
            end_date: payload.end_date,
            option_id: payload.option_id,
            portfolio_id: payload.portfolio_id,
            portfolio_no: 0,
            product_id: payload.product_id
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
        commit('SET_CUSTOM_PRODUCTS', res.data.custom_product_list)
        // 포트폴리오 수정일때 동기
        if (window.location.pathname === '/portfolio/edit')
          location.reload()
      })
      .catch(err => {
        console.log(err)
      })
    },
    pushProductToPortfolio({ dispatch, getters }, payload) {
      console.log(payload)
      axios({
        url: spring.portfolio.portfolio(),
        method: 'post',
        data: {
          member_id: getters.userInfo.member_id,
          portfolio_dto_list: [
            {
              amount: payload.amount,
              condition_ids: payload.condition_ids,
              start_date: payload.start_date,
              end_date: payload.end_date,
              option_id: payload.option_id,
              portfolio_no: 0,
              product_id: payload.product.product_id
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
    },
    saveToDb({ getters, state }) {
      for (var i = 1; i <= 5; i++){
        if (state.newlyAddedPortfolio.includes(i)) {
          var tempPortfolioList = [];
          for (const port of state.comparisonportfolios[i-1].products) {
            var temp = {
              portfolio_no:i,
              product_id:port.product.product_id,
              amount:port.amount,
              condition_ids: port.condition_ids,
              option_id:port.selected_option_id,
              start_date: port.start_date,
              end_date: port.end_date
            }
            tempPortfolioList.push(temp)
          }
          axios({
            url: spring.portfolio.portfolio(),
            method: 'post',
            data: {
              member_id: getters.userInfo.member_id,
              portfolio_dto_list:tempPortfolioList
            }
          })
        } else {
          var tempPortfolioList = [];
          for (const port of state.comparisonportfolios[i-1].products) {
            var temp = {
              portfolio_no:i,
              product_id:port.product.product_id,
              amount:port.amount,
              condition_ids: port.condition_ids,
              option_id:port.selected_option_id,
              start_date: port.start_date,
              end_date: port.end_date
            }
            tempPortfolioList.push(temp)
          }
          axios({
            url: spring.portfolio.portfolio(),
            method: 'put',
            data: {
              member_id: getters.userInfo.member_id,
              portfolio_no: i,
              portfolio_dto_list: tempPortfolioList
            }
          })
        }
      }
      
    }
  }
};
