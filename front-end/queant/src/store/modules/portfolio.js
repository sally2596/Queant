import spring from '@/api/spring'
import router from '@/router'
import axios from 'axios'

export default {
  state: {
    portfolio: {},
    customPortfolio1: [],
    customPortfolio2: [],
    customPortfolio3: []
  },
  getters: {
    portfolio: state => state.portfolio,
    customPortfolio1: state => state.customPortfolio1,
    customPortfolio2: state => state.customPortfolio2,
    customPortfolio3: state => state.customPortfolio3
  },
  mutations: {
    SET_PORTFOLIO: (state, portfolio) => state.portfolio = portfolio,
    PUSH_PRODUCT_TO_COMPARISON(state, value) {
      let portfolioNo = value[0].target.value
      let product = value[1]

      if (portfolioNo === "1") {
        state.customPortfolio1.push(product)
        console.log(`${product.product_id}번 상품을 1번 가상 포트폴리오에 넣었습니다.`)
      }
      else if (portfolioNo === "2") {
        state.customPortfolio2.push(product)
        console.log(`${product.product_id}번 상품을 2번 가상 포트폴리오에 넣었습니다.`)
      }
      else if (portfolioNo === "3") {
        state.customPortfolio3.push(product)
        console.log(`${product.product_id}번 상품을 3번 가상 포트폴리오에 넣었습니다.`)
      }
    }
  },
  actions: {
    fetchComparisonPortfolio({ getters }) {
      axios({
        url: spring.portfolio.virtual(),
        method: 'post',
        data: {
          member_id: getters.userInfo.member_id,
        }
      })
      .then(res => {
        console.log(res)
      })
      .catch(err => {
        console.log('haha')
        console.log(err)
      })
    },
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
    deletePortfolio({ commit, getters }) {
      axios({
        url: spring.portfolio.portfolio(),
        method: 'delete',
        data: {
          member_id: getters.userInfo.member_id,
          portfolio_no: 0
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
