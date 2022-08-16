import spring from '@/api/spring'
import router from '@/router'
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
		getContentList({ commit }) {
      axios({
        url: spring.contents.contentlist(),
        method: 'get',
      })
      .then(res => {
        console.log(res)
        commit('SET_CONTENTS', res.data)
      })
      .catch(err => {
        console.log(err)
      })
		},
		getContent({ commit }, contentId) {
      axios({
        url: spring.contents.detail(contentId),
        method: 'get',
      })
      .then(res => {
        console.log(res)
        res.data.content = res.data.content.replaceAll('㉾','"');
        commit('SET_CONTENT', res.data)

      })
      .catch(err => {
        console.log(err)
      })
		},
		editContent({}, content) {
      axios({
        url: spring.contents.edit(),
				method: 'post',
				data: {
					title : content.title,
          member_id : content.memberId,
          content : content.content
        },
      })
      .then(res => {
				console.log(res)
        router.push({ name: 'contents' })
      })
      .catch(err => {
        console.log(err)
      })
		},
    modifyContent({}, content) {
      console.log(content);
      axios({
        url: spring.contents.edit(),
				method: 'put',
				data: {
					title : content.title,
          member_id : content.memberId,
          content : content.content,
          content_id : content.contentId
        },
      })
      .then(res => {
				console.log(res)
        router.push({ name: 'contents' })
      })
      .catch(err => {
        console.log(err)
      })
		},
    deleteContent({}, contentId) {
      axios({
        url: spring.contents.remove(),
				method: 'delete',
				data: {
          content_id : contentId,
        },
      })
      .then(res => {
				console.log(res)
        router.push({ name: 'contents' })
      })
      .catch(err => {
        console.log(err)
      })
		},
  }
}