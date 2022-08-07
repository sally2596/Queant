import spring from '@/api/spring'
import axios from 'axios'

export default {
  state: {
		newslist: [],
		contents: [],
		content: {},
  },
  getters: {
		newslist: state => state.newslist,
		contents: state => state.contents,
		content: state => state.content
  },
  mutations: {
		SET_NEWSLIST: (state, newslist) => state.newslist = newslist,
		SET_CONTENTS: (state, contents) => state.contents = contents,
		SET_CONTENT: (state, content) => state.content = content,
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
		// getContentList({ commit }) {
    //   axios({
    //     url: spring.contents.newslist(),
    //     method: 'get',
    //   })
    //   .then(res => {
    //     console.log(res)
    //     commit('SET_NEWSLIST', res.data)
    //   })
    //   .catch(err => {
    //     console.log(err)
    //   })
		// },
		// getContent({ commit }) {
    //   axios({
    //     url: spring.contents.newslist(),
    //     method: 'get',
    //   })
    //   .then(res => {
    //     console.log(res)
    //     commit('SET_NEWSLIST', res.data)
    //   })
    //   .catch(err => {
    //     console.log(err)
    //   })
		// },
		editContent({}, content) {
      axios({
        url: spring.contents.edit(),
				method: 'post',
				params: {
					content: JSON.stringify(content)
        }
      })
      .then(res => {
				console.log(res)
      })
      .catch(err => {
        console.log(err)
      })
		},
  }
}