<template>
    <navbar/>
    <header id="title-div">
      <h1 class="title" id="title">내 상품제보 관리</h1>
    </header>
		<section class="container row">
			<table class="table table-hover">
				<thead>
					<tr class="text-center">
						<th scope="col">번호</th>
						<th scope="col">은행명</th>
						<th scope="col">상품명</th>
						<th scope="col">등록상황</th>
					</tr>
				</thead>
				<tbody v-for="report in reports" v-bind:key="report">
					<tr scope="row">
						<td class="col-1 text-center">{{ report.report_product_id }}</td>
						<td class="col-1 text-center">{{ report.bank_name }}</td>
						<td class="col-1 text-center">{{ report.product_name }}</td>
						<td class="col-1 text-center" v-if="report.updated"> 처리완료 </td>
						<td class="col-1 text-center" v-else> 처리중 </td>
					</tr>
				</tbody>
			</table>
		</section>
    <br><br>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import _ from 'lodash'
import Navbar from '@/components/Navbar.vue'

export default {
  name: 'ProductSpecialMyView',
  components: { Navbar },
  computed: {
    ...mapGetters(['userInfo', 'reports']),
  },
  data() {
    return {
    }
  },
  methods: {
    ...mapActions(['getUserReports']),
  },
  created() {
    this.getUserReports(this.userInfo.member_id);
  },
}
</script>

<style>
@import '../../assets/css/admin.css';
</style>