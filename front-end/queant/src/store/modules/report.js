import spring from '@/api/spring'
import router from '@/router'
import axios from 'axios'

export default {
  state: {
		reports: [],
		report: {},
		userReports: []
  },
  getters: {
		reports: state => state.reports,
		report: state => state.report,
		userReports: state => state.userReports
  },
	mutations: {
		SET_REPORTS: (state, reports) => state.reports = reports,
		SET_REPORT: (state, report) => state.report = report,
		SET_USER_REPORTS: (state, userReports) => state.userReports = userReports
	},
	actions: {
		getReports({ commit }) {
      axios({
        url: spring.product.report.list(),
        method: 'get',
      })
      .then(res => {
        console.log(res)
        commit('SET_REPORTS', res.data)
      })
      .catch(err => {
        console.log(err)
      })
		},
		registReports({}, reportProductDto) {
      axios({
        url: spring.product.report.list(),
				method: 'post',
				data: {
					member_email : reportProductDto.memberEmail,
					bank_name : reportProductDto.bankName,
					product_name : reportProductDto.productName,
					is_deposit : reportProductDto.isDeposit,
					is_updated : false,
				}
      })
      .then(res => {
        console.log(res)
      })
      .catch(err => {
        console.log(err)
      })
		},
		getReport({ commit }, reportId) {
      axios({
        url: spring.product.report.detail(reportId),
        method: 'get',
      })
      .then(res => {
        console.log(res)
        commit('SET_REPORT', res.data)
      })
      .catch(err => {
        console.log(err)
      })
		},
		updateReports({}, reportId, productDetail) {
      axios({
        url: spring.product.report.detail(reportId),
				method: 'post',
				data: {
					product : productDetail.product,
					options : productDetail.options,
					conditions : productDetail.conditions,
					joinway : productDetail.joinway
				}
      })
      .then(res => {
        console.log(res)
      })
      .catch(err => {
        console.log(err)
      })
		},
		deleteReports({}, reportId) {
      console.log(content);
      axios({
        url: spring.contents.delete(),
				method: 'put',
				data: {
					report_id : reportId
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
		getUserReports({ commit }, memberEmail) {
      axios({
        url: spring.product.report.user(),
				method: 'get',
				data: {
					member_email : memberEmail
        },
      })
      .then(res => {
        console.log(res)
        commit('SET_USER_REPORTS', res.data)
      })
      .catch(err => {
        console.log(err)
      })
		},
  }
}