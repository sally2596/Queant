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
    customPortfolio1: state => state.customPortfolio1
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
              // amount_fixed: 0,  // 적금일때 돈
              condition_ids: [479], // 우대사항 array
              start_date: '2022-08-01',
              end_date: '2022-08-10',
              option_id: 2992,  // 개월수에 따라 금리의 차이를 알려주는 속성
              // portfolio_id: 0,  // 수정할때만 입력, 실존하는 포트폴리오 id, 수정 삭제가 반복될 때마다 오토인크
              portfolio_no: 0,  // 0은 마이포트폴리오, 1~5 가상포트폴리오, 생성할때 id 입력X, no 입력O (cnt +1)
              product_id: 1
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
    addPortfolio({ commit, getters }) {
      console.log(getters.userInfo.member_id)
      axios({
        url: spring.portfolio.portfolio(),
        method: 'post',
        data: {
          // custom_product_dto: [
          //   {
          //     amount: 0,
          //     amount_regular:	0,
          //     base_rate: 1.0,
          //     deposit: true,
          //     start_date: '2022-06-01',
          //     end_date:	'2022-07-01',
          //     etc: '',
          //     fixed_rsrv: true,
          //     institution_name: '',
          //     product_id: 1,
          //     product_name:	'',
          //     special_rate:	2.0
          //   }
          // ],
          member_id: getters.userInfo.member_id,
          // portfolio_cnt: getters.userInfo.portfolio_cnt,
          portfolio_dto_list: [
            {
              amount: 100000,  // 예금일때 돈
              // amount_fixed: 0,  // 적금일때 돈
              condition_ids: [479], // 우대사항 array
              start_date: '2022-08-01',
              end_date: '2022-08-10',
              option_id: 2992,  // 개월수에 따라 금리의 차이를 알려주는 속성
              // portfolio_id: 0,  // 수정할때만 입력, 실존하는 포트폴리오 id, 수정 삭제가 반복될 때마다 오토인크
              portfolio_no: 0,  // 0은 마이포트폴리오, 1~5 가상포트폴리오, 생성할때 id 입력X, no 입력O (cnt +1)
              product_id: 1
            }
          ],
          portfolio_no: 0
        }
      })
      .then(res => {
        console.log(res)
      })
      .catch(err => {
        console.log(err)
      })
    }
  }
};
