import spring from '@/api/spring'
import router from '@/router'
import axios from 'axios'

export default {
  state: {
    product_name: '',
    portfolio: [],
    customProducts: [],
    portfolios: [],
    comparisonPortfolio: [],
    newlyAddedPortfolio: [],
    deletedPortfolio:[],
    },
  getters: {
    product_name: state => state.product_name,
    portfolio: state => state.portfolio,
    customProducts: state => state.customProducts,
    portfolios: state => state.portfolios,
    comparisonPortfolio: state => state.comparisonPortfolio,
    newlyAddedPortfolio: state => state.newlyAddedPortfolio,
    deletedPortfolio: state => state.deletedPortfolio,
  },
  mutations: {
    SET_PORTFOLIO: (state, portfolio) => state.portfolio = portfolio,
    SET_CUSTOM_PRODUCTS: (state, customProducts) => state.customProducts = customProducts,
    UPDATE_CPORTFOLIO_FROM_DB(state, value) {
      state.comparisonPortfolio = value;
    },
    PUSH_PRODUCT_TO_PORTFOLIO(state, value) {
      let portfolioNo = value.portfolioNo

      if (portfolioNo > state.portfolios.length) {
        state.portfolios.push([value])
        console.log('내 포트폴리오에 상품이 담겼습니다.')
      } else {
        state.portfolios[portfolioNo-1].push(value)
        console.log('내 포트폴리오에 상품을 추가했습니다.')
      }
    },
    CLEAR_CPORTFOLIO_DB(state) {
      state.newlyAddedPortfolio = [],
      state.deletedPortfolio = []
    },

    POP_CPORTFOLIO(state, cportfolio_cnt) {
      state.deletedPortfolio.push(cportfolio_cnt)
      let portfolioNo = cportfolio_cnt
      // 삭제된 포트폴리오의 이후 포트폴리오의 cnt를 1씩 차감
      for (let i=portfolioNo; i <= state.comparisonPortfolio.length-1; i++) {
        state.comparisonPortfolio[i].cportfolio_cnt -= 1
        console.log(state.comparisonPortfolio[i])
      }
      state.comparisonPortfolio.splice(portfolioNo-1, 1)
    },
    ADD_COMPARISON_PORTFOLIO(state) {
      if (state.comparisonPortfolio.length === 0) {
        state.newlyAddedPortfolio.push(1);
        state.comparisonPortfolio.push({
          cportfolio_cnt: 1,
          products: []
        }),
        console.log('새로운 가상 포트폴리오가 추가되었습니다.')
      } else if (state.comparisonPortfolio.length <= 4) {
        state.newlyAddedPortfolio.push(state.comparisonPortfolio.length+1)
        state.comparisonPortfolio.push({
          cportfolio_cnt: (state.comparisonPortfolio.length) +1,
          products: []
        }),
        console.log('새로운 가상 포트폴리오가 추가되었습니다.')
      } else if (state.comparisonPortfolio.length > 4) {
        alert('더 이상 가상 포트폴리오를 만들 수 없습니다.')
      }
    },

    PUSH_PRODUCT_TO_CPORTFOLIO(state, value) {
      let portfolioNo = value[0]
      let product = value[1]
      let cportfolios = state.comparisonPortfolio
      let pushproduct = {
        portfolio_no: portfolioNo,
        product_id: product.product.product_id,
        amount: product.amount,
        condition_ids: product.condition_ids,
        option_id: product.option_id,
        start_date: product.start_date,
        end_date: product.end_date,
        name: product.product.name
      }
      if (cportfolios[portfolioNo-1].products.find(cportfolioItem => cportfolioItem.product_id === product.product.product_id)) {
        alert('이미 포트폴리오에 있는 상품입니다.')
      } else {
        state.comparisonPortfolio[portfolioNo-1].products.push(pushproduct)
      console.log(`${portfolioNo}번 포트폴리오에 상품이 담겼습니다.`)
      }
    },

    POP_PRODUCT_FROM_CPORTFOLIO(state, value) {
      let portfolioNo = value[0]
      let FindProduct = value[1]
      let cportfolios = state.comparisonPortfolio
      let item = cportfolios[portfolioNo-1].products.indexOf('FindProduct')
      cportfolios[portfolioNo-1].products.splice(item, 1)
      alert(`${portfolioNo}번 포트폴리오의 ${FindProduct.product.name} 상품을 삭제했습니다.`)
    },

    POP_CPORTFOLIO_FROM_CPORTFOLIOS(state, cportfolioIdx) {
      state.comparisonPortfolio.splice(cportfolioIdx-1, 1)
      console.log(`${cportfolioIdx}번 포트폴리오를 삭제했습니다.`)
    },

    CLEAR_CPORTFOLIOS(state) {
      state.comparisonPortfolio = []
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
    },
    CHANGE_NAMES(state, name) {
      state.product_name = name
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
        console.log('내 포트폴리오에 상품이 등록됐습니다.')
        dispatch('fetchMyPortfolio')
      })
      .catch(err => {
        console.log(err)
      })
    },

    // 가상 포트폴리오 DB 저장 및 가져오기
    saveToDb({ dispatch, state, getters }) {
      //삭제된 포트폴리오 DB에서 제거하기
      if (state.deletedPortfolio.length != 0) {
        for (const port_no of state.deletedPortfolio) {
          axios({
            url: spring.portfolio.portfolio(),
            method: 'delete',
            data: {
              member_id: getters.userInfo.member_id,
              portfolio_no:port_no
            }
          }).then(res => {
            console.log(res)
            console.log(port_no+'번 포트폴리오가 DB에서 제거되었습니다.')
          })
          .catch(err => {
            console.log(err)
          })
        }
      }
      //현재 남아있는 포트폴리오 수정 및 삽입
      for (let i = 1; i <= state.comparisonPortfolio.length; i++){
        if (state.newlyAddedPortfolio.includes(i)) {
          console.log(state.comparisonPortfolio[i-1])
          let tempPortfolioList = [];
          for (const port of state.comparisonPortfolio[i - 1].products) {
            console.log(port.option_id)
            let temp = {
              portfolio_no:i,
              product_id:port.product_id,
              amount:port.amount,
              condition_ids: port.condition_ids,
              option_id:port.option_id,
              start_date: port.start_date,
              end_date: port.end_date
            }
            tempPortfolioList.push(temp)
          }
          console.log(tempPortfolioList)
          console.log(getters.userInfo)
          axios({
            url: spring.portfolio.portfolio(),
            method: 'post',
            data: {
              member_id: getters.userInfo.member_id,
              portfolio_dto_list:tempPortfolioList
            }
          }).then(res => {
            console.log(res)
            console.log(i+'번 포트폴리오가 DB에 저장되었습니다.')
          })
          .catch(err => {
            console.log(err)
          })
        } else {
          let tempPortfolioList = [];
          console.log(state.comparisonPortfolio[i-1])
          for (const port of state.comparisonPortfolio[i - 1].products) {
            console.log(port.option_id)
            let temp = {
              portfolio_no:i,
              product_id:port.product_id,
              amount:port.amount,
              condition_ids: port.condition_ids,
              option_id: port.option_id,
              start_date: port.start_date,
              end_date: port.end_date
            }
            tempPortfolioList.push(temp)
          }
          console.log(tempPortfolioList)
          axios({
            url: spring.portfolio.portfolio(),
            method: 'put',
            data: {
              member_id: getters.userInfo.member_id,
              portfolio_no: i,
              portfolio_dto_list: tempPortfolioList
            }
          }).then(res => {
            console.log(res)
            console.log(i+'번 포트폴리오가 DB에 업데이트 되었습니다.')
          })
          .catch(err => {
            console.log(err)
          })
        }
      }
      
    },
    getFromDb({ commit, dispatch, state, getters, actions }) {
      // Clear DB를 실행(추가, 삭제 초기화)
      commit('CLEAR_CPORTFOLIO_DB');
      // 포트폴리오 초기화
      commit('CLEAR_CPORTFOLIOS');
      // 정보 받아오기
      axios({
        url: spring.portfolio.virtual(),
        method: 'post',
        data: {
          member_id: getters.userInfo.member_id
        }
      })
      .then(res => {
        console.log(res.data)
        const firstdata = res.data
        let bringdata = []
        // 넣을 데이터를 생성
        for (let i = 0; i<firstdata.length; i++) {
          bringdata.push({
            cportfolio_cnt: i+1,
            products: []
          })
        }
        
        // 받아온 데이터를 다시 형식에 맞게 변경
        for (let i = 0; i<firstdata.length; i++) {
          for (let j = 0; j<firstdata[i].length; j++) {
            bringdata[i].products.push({
              portfolio_no: firstdata[i][j].portfolio_no,
              product_id: firstdata[i][j].product_id,
              amount: firstdata[i][j].amount,
              condition_ids: firstdata[i][j].condition_ids,
              option_id: firstdata[i][j].option_id,
              start_date: firstdata[i][j].start_date,
              end_date: firstdata[i][j].end_date,
              name: firstdata[i][j].product.name
            })
          }
        }
        // ComparisonPortfolio를 업데이트
        commit('UPDATE_CPORTFOLIO_FROM_DB', bringdata);
      })
      .catch(err => {
        console.log(err)
      })
    }
  }
};
