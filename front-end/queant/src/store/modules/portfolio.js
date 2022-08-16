import spring from '@/api/spring'
import router from '@/router'
import axios from 'axios'

export default {
  state: {
    comparisonProducts: [],
    portfolio: [],
    customProducts: [],
    portfolios: [],
    comparisonPortfolio: [],
    newlyAddedPortfolio: [],
    deletedPortfolio:[],
    },
  getters: {
    comparisonProducts: state=> state.comparisonProducts,
    portfolio: state => state.portfolio,
    customProducts: state => state.customProducts,
    portfolios: state => state.portfolios,
    comparisonPortfolio: state => state.comparisonPortfolio,
    newlyAddedPortfolio: state => state.newlyAddedPortfolio,
    deletedPortfolio: state => state.deletedPortfolio,
  },
  mutations: {
    SET_CUSTOM_PRODUCTS: (state, customProducts) => state.customProducts = customProducts,

    // Comparison Portfolio

    UPDATE_CPORTFOLIO_FROM_DB(state, value) {state.comparisonPortfolio = value;},
    
      // DB정리
    CLEAR_CPORTFOLIO_DB(state) {
      state.newlyAddedPortfolio = [],
      state.deletedPortfolio = []
    },
    ADD_COMPARISON_PORTFOLIO_PRODUCTS(state, product) {
      state.comparisonProducts.push(product);
    },
      // 포트폴리오 삭제하기
    POP_CPORTFOLIO(state, cportfolio_cnt) {
      state.deletedPortfolio.push(cportfolio_cnt)
      let portfolioNo = cportfolio_cnt
      // 삭제된 포트폴리오의 이후 포트폴리오의 cnt를 1씩 차감
      for (let i=portfolioNo; i < state.comparisonPortfolio.length; i++) {
        state.comparisonPortfolio[i].cportfolio_cnt -= 1
        console.log(state.comparisonPortfolio[i])
      }
      state.comparisonPortfolio.splice(portfolioNo-1, 1)
    },

      // 포트폴리오 추가하기
    ADD_COMPARISON_PORTFOLIO(state) {
      // 0개일 때
      if (state.comparisonPortfolio.length === 0) {
        state.newlyAddedPortfolio.push(1);
        state.comparisonPortfolio.push({
          cportfolio_cnt: 1,
          products: []
        }),
        console.log('새로운 가상 포트폴리오가 추가되었습니다.')
      // 1~3개일 때
      } else if (state.comparisonPortfolio.length < 4) {
        state.newlyAddedPortfolio.push(state.comparisonPortfolio.length+1)
        state.comparisonPortfolio.push({
          cportfolio_cnt: (state.comparisonPortfolio.length)+1,
          products: []
        }),
        console.log('새로운 가상 포트폴리오가 추가되었습니다.')
      // 4개일 때
      } else if (state.comparisonPortfolio.length > 3) {
        alert('더 이상 가상 포트폴리오를 만들 수 없습니다.')
      }
    },

      // 가상 포트폴리오에 상품 추가하기
    PUSH_PRODUCT_TO_CPORTFOLIO(state, value) {
      // 추가할 정보 정리
      let portfolioNo = value[0]
      let product = value[1]
      let cportfolios = state.comparisonPortfolio
      let pushproduct = {
        amount: product.amount,
        condition_ids: product.condition_ids,
        end_date: product.end_date,
        portfolio_no: portfolioNo,
        product: product.product,
        product_id: product.product.product_id,
        option_id: product.option_id,
        start_date: product.start_date,
        total_rate: product.product.total_rate,
        isDeposit: product.product.deposit,
        term: product.aplied_period,
        rate_type: product.rate_type
      }
      console.log(pushproduct)
      if (cportfolios[portfolioNo-1].products.find(cportfolioItem => cportfolioItem.product_id === product.product.product_id)) {
        alert(`${product.product.name}은 이미 포트폴리오에 있는 상품입니다.`)
      } else {
        state.comparisonPortfolio[portfolioNo-1].products.push(pushproduct)
        alert(`${portfolioNo}번 포트폴리오에 ${pushproduct.name}상품이 담겼습니다.`)
      }
    },
    // 상품 삭제
    POP_PRODUCT_FROM_CPORTFOLIO(state, value) {
    let portfolioNo = value[0]
    let FindProduct = value[1]
    let cportfolios = state.comparisonPortfolio
    let item = cportfolios[portfolioNo-1].products.indexOf('FindProduct')
    
    alert(`${portfolioNo}번 포트폴리오의 ${FindProduct.name} 상품을 삭제했습니다.`)
    cportfolios[portfolioNo-1].products.splice(item, 1)
    },

    POP_CPORTFOLIO_FROM_CPORTFOLIOS(state, cportfolioIdx) {
      state.comparisonPortfolio.splice(cportfolioIdx-1, 1)
      console.log(`${cportfolioIdx}번 포트폴리오를 삭제했습니다.`)
    },

    CLEAR_CPORTFOLIOS(state) {
      state.comparisonPortfolio = []
    },

    // My Portfolio
    
    SET_PORTFOLIO: (state, portfolio) => state.portfolio = portfolio,
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
  },

  actions: {
    // Custom Product
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

    // My Portfolio

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
      if (!getters.portfolio) {
        dispatch('fetchMyPortfolio')
      }

      for (const port of getters.portfolio) {
        if (port.product.product_id == payload.product.product_id) {
          alert('이미 내 포트폴리오에 포함되어있는 상품입니다.')
          return;
        }
      }

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
				if (confirm('내 포트폴리오에 상품이 등록됐습니다. 내 포트폴리오로 이동하시겠습니까?') === true) {
          router.push({ name: 'portfolio' })
        } else {
          return
        }

      })
      .catch(err => {
        console.log(err)
      })
    },

    // Comparison Portfolio

    // 가상 포트폴리오 DB 저장 및 가져오기
    saveToDb({ dispatch, commit, state, getters }) {
      //삭제된 포트폴리오 DB에서 제거하기
      // 삭제된 포트폴리오가 있다면 요청 보내기
      if (state.deletedPortfolio.length != 0) {
        for (let i=0; i<state.deletedPortfolio.length; i++) {
          let port_no = state.deletedPortfolio[i]
          // port_no번에 해당하는 포트폴리오를 모두 삭제해 주세요.
          axios({
            url: spring.portfolio.portfolio(),
            method: 'delete',
            data: {
              member_id: getters.userInfo.member_id,
              portfolio_no:port_no
            }
          }).then(res => {
            console.log(res)
            console.log(`${i+1}번 포트폴리오가 DB에서 삭제되었습니다.`)
          })
          .catch(err => {
            console.log(err)
          })
        }
      }
    //현재 남아있는 포트폴리오 수정 및 삽입
    for (let i = 0; i < state.comparisonPortfolio.length; i++){
      // 새로 추가된 포트폴리오가 있다면
      if (state.newlyAddedPortfolio.includes(i+1)) {
          // 만약 비어있는 포트폴리오가 있다면 새로 저장할 수 없음.
          if (state.comparisonPortfolio[i].cportfolio_cnt === (i+1) && state.comparisonPortfolio[i].products.length === 0) {
            commit('POP_CPORTFOLIO', i+1)
            alert(`비어 있는 ${i+1}번 포트폴리오를 삭제했습니다.`)
          } else if (state.comparisonPortfolio[i].cportfolio_cnt === (i+1) && state.comparisonPortfolio[i].length != 0) {
          let tempPortfolioList = [];
          for (let port of state.comparisonPortfolio[i].products) {
            let temp = {
              amount: port.amount,
              condition_ids: port.condition_ids,
              end_date: port.end_date,
              portfolio_no: port.portfolio_no,
              product_id: port.product_id,
              option_id: port.option_id,
              start_date: port.start_date,
            }
            tempPortfolioList.push(temp)
          }
          console.log(tempPortfolioList)
          console.log('올릴 자료')
          axios({
            url: spring.portfolio.portfolio(),
            method: 'post',
            data: {
              member_id: getters.userInfo.member_id,
              portfolio_no: i+1,
              portfolio_dto_list: tempPortfolioList
            }
          }).then(res => {
            console.log(res)
            alert(`${i+1}번 포트폴리오가 DB에 새롭게 저장되었습니다.`)
          })
          .catch(err => {
            console.log(err)
          })}
        // 새로 추가된 것이 없는 경우
      } else {
        // 이 경우에도 비어있는 경우 저장할 수 없고, 오히려 삭제할 것.
        if (state.comparisonPortfolio[i].cportfolio_cnt === (i+1) && state.comparisonPortfolio[i].products.length === 0) {
          alert(`비어 있는 포트폴리오를 저장할 수 없습니다. ${i+1}번 포트폴리오를 DB에서 삭제합니다.`)
          let port_no = state.comparisonPortfolio[i].cportfolio_cnt
          // port_no번에 해당하는 포트폴리오를 모두 삭제해 주세요.
          axios({
            url: spring.portfolio.portfolio(),
            method: 'delete',
            data: {
              member_id: getters.userInfo.member_id,
              portfolio_no:port_no
            }
          }).then(res => {
            console.log(res)
            alert(`${i+1}번 포트폴리오가 DB에서 삭제되었습니다.`)
          })
          .catch(err => {
            console.log(err)
          })
          // 비어있지 않은 경우
        } else if (state.comparisonPortfolio[i].cportfolio_cnt === (i+1) && state.comparisonPortfolio[i].length != 0) {
        let tempPortfolioList = [];
        console.log(state.comparisonPortfolio[i])
        for (let port of state.comparisonPortfolio[i].products) {
          console.log(port.option_id)
          let temp = {
            amount: port.amount,
            condition_ids: port.condition_ids,
            end_date: port.end_date,
            portfolio_no: port.portfolio_no,
            product_id: port.product_id,
            option_id: port.option_id,
            start_date: port.start_date,
          }
          tempPortfolioList.push(temp)
        }
        console.log(tempPortfolioList)
        axios({
          url: spring.portfolio.portfolio(),
          method: 'put',
          data: {
            member_id: getters.userInfo.member_id,
            portfolio_no: i+1,
            portfolio_dto_list: tempPortfolioList
          }
        }).then(res => {
          console.log(res)
          alert(`${i+1}번 포트폴리오를 DB에서 수정했습니다.`)
        })
        .catch(err => {
          console.log(err)
        })}
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
            var conditions_rate = 0
            if (firstdata[i][j].conditions.length > 0) {
              for (var condition of firstdata[i][j].conditions) {
                conditions_rate += condition.special_rate
              }
            }
            bringdata[i].products.push({
              amount: firstdata[i][j].amount,
              condition_ids: firstdata[i][j].condition_ids,
              end_date: firstdata[i][j].end_date,
              portfolio_no: firstdata[i][j].portfolio_no,
              product: firstdata[i][j].product,
              product_id: firstdata[i][j].product_id,
              option_id: firstdata[i][j].option_id,
              start_date: firstdata[i][j].start_date,
              total_rate: firstdata[i][j].option.base_rate+conditions_rate,
              isDeposit: firstdata[i][j].product.deposit,
              term: firstdata[i][j].option.save_term,
              rate_type: firstdata[i][j].option.rate_type
            })
            dispatch('getProductDetails', firstdata[i][j].product_id)
          }
        }
        // ComparisonPortfolio를 업데이트
        commit('UPDATE_CPORTFOLIO_FROM_DB', bringdata);
      })
      .catch(err => {
        console.log(err)
      })
    },
    getProductDetails({ commit, dispatch }, productId) {
      axios({
        url: spring.product.detail(productId),
        method: 'get',
        params: {
            productId: productId
        }
      })
      .then(res=> {
        console.log(res.data)
        commit('ADD_COMPARISON_PORTFOLIO_PRODUCTS', res.data)
      })
      .catch(err => {
        console.log(err)
      })
    }
  }
};
