import spring from '@/api/spring'
import router from '@/router'
import { faSleigh } from "@fortawesome/free-solid-svg-icons"
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
		registReport({}, reportProductDto) {
      axios({
        url: spring.product.report.list(),
				method: 'post',
				data: {
					member_email : reportProductDto.memberEmail,
					bank_name : reportProductDto.bankName,
					product_name : reportProductDto.productName,
					deposit : false,
          reference_data : reportProductDto.referenceData,
					updated : false,
				}
      })
      .then(res => {
				console.log(res)
				alert("제보에 성공했습니다!");
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
		updateReport({ }, data) {
      axios.post(
        spring.product.report.regist(),
				JSON.stringify(data), {
					headers: { 'Content-Type': 'application/json' }
				}
      )
      .then(res => {
        console.log(res)
      })
      .catch(err => {
        console.log(err)
      })
		},
		deleteReport({ }, data) {
			axios.put(
        spring.product.report.delete(),
				JSON.stringify(data), {
					headers: { 'Content-Type': 'application/json' }
				}
      )
      .then(res => {
				console.log(res)
      })
      .catch(err => {
        console.log(err)
      })
		},
		getUserReports({ commit }, data) {
			axios.get(
        spring.product.report.user(),
				JSON.stringify(data), {
					headers: { 'Content-Type': 'application/json' }
				}
      )
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