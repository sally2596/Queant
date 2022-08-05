import spring from '@/api/spring'
import axios from 'axios'

export default {
  state: {
    newslist: [],
  },
  getters: {
    newslist: state => state.newslist,
  },
  mutations: {
    SET_NEWSLIST: (state, newslist) => state.newslist = newslist,
  },
  actions: {
    getNews({ commit }) {
      axios({
        url: spring.contents.newslist(),
        method: 'get',
      })
      .then(res => {
        console.log(res)
        commit('SET_NEWSLIST', res.data)
      })
      .catch(err => {
        console.log(err)
      })
    },
  }
}