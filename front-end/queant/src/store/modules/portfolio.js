import spring from '@/api/spring'
import router from '@/router'
import axios from 'axios'

export default {
  state: {},
  getters: {},
  mutations: {},
  actions: {
    fetchPortfolio({ commit }) {
      axios({
        url: spring.portfolio.posession(),
        method: 'post',
        data: {
          email: getters.userInfo.email
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
