import spring from '@/api/spring'
import router from '@/router'
import axios from 'axios'

export default {
  state: {
    portfolio: {},
    customPortfolios: []
  },
  getters: {
    portfolio: state => state.portfolio,
    customPortfolios: state => state.customPortfolios
  },
  mutations: {
    SET_PORTFOLIO: (state, portfolio) => state.portfolio = portfolio,
    PUSH_PRODUCT_TO_CUSTOM_PORTFOLIOS(state, value) {
      let portfolioNo = value[0].target.value
      let product = value[1]

      if (portfolioNo > state.customPortfolios.length) {
        state.customPortfolios.push([product])
        console.log('새로운 포트폴리오에 상품이 담겼습니다.')
      } else {
        state.customPortfolios[portfolioNo-1].push(product)
        console.log('기존 포트폴리오에 상품을 추가했습니다.')
      }
    },
    POP_CUSTOM_PORTFOLIO_FROM_CUSTOM_PORTFOLIOS(state, index) {
      state.customPortfolios.splice(index, 1)
      console.log(`${index+1}번 포트폴리오를 삭제했습니다.`)
    }
  },
  actions: {
    editPortfolio({ commit, getters }) {
      axios({
        url: spring.portfolio.portfolio(),
        method: 'put',
        data: {
          member_id: getters.userInfo.member_id,
          portfolio_no: 0,
          portfolio_dto_list: [
            {
              amount: 100000,  // 예금일때 돈
              condition_ids: [479], // 우대사항 array
              start_date: '2022-08-01',
              end_date: '2022-08-10',
              option_id: 2992,  // 개월수에 따라 금리의 차이를 알려주는 속성
              portfolio_no: 0,  // 0은 마이포트폴리오, 1~5 가상포트폴리오, 생성할때 id 입력X, no 입력O (cnt +1)
              product_id: 1
              // portfolio_id: 0,  // 수정할때만 입력, 실존하는 포트폴리오 id, 수정 삭제가 반복될 때마다 오토인크
              // amount_fixed: 0,  // 적금일때 돈
            }
          ]
        }
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
    addProductToCustomPortfolio({ getters }, payload) {
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
    },
    addProductToMyPortfolio({ commit }, product) {
      console.log(product)
    }
  }
};
