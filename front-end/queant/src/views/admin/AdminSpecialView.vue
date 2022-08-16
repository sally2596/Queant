<template>
    <NavbarAdmin/>
    <header id="title-div">
      <h1 class="title" id="title">제보 상품 관리</h1>
    </header>
		<div class="d-flex justify-content-center">
      <section class="container row">
        <table class="table table-hover">
          <thead>
            <tr class="text-center">
              <th scope="col">번호</th>
              <th scope="col">은행명</th>
              <th scope="col">상품명</th>
              <th scope="col">작업</th>
            </tr>
          </thead>
          <tbody v-for="report in reports" v-bind:key="report">
            <tr scope="row">
              <td class="col-1 text-center">{{ report.report_product_id }}</td>
              <td class="col-1 text-center">{{ report.bank_name }}</td>
              <td class="col-1 text-center">{{ report.product_name }}</td>
              <td class="col-1 text-center">
                <button type="button" class="btn btn-sm btn-outline-secondary" @click="modal(report.report_product_id)">입력</button>
                <button type="button" class="btn btn-sm btn-outline-secondary" @click="removeItem(report.report_product_id)">삭제</button>
              </td>
            </tr>
          </tbody>
        </table>
      </section>
		</div>
    <br><br>
		<!-- 모달 -->
    <CustomProductModal 
      v-if="isModalViewed" 
      @close-modal="isModalViewed=false"
      :reportID=this.reportIndex>
    </CustomProductModal>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import _ from 'lodash'
import NavbarAdmin from '@/components/NavbarAdmin.vue'
import CustomProductModal from '@/views/product/ReportProductModal.vue'

export default {
  name: 'AdminSpecialView',
  components: { NavbarAdmin, CustomProductModal },
  computed: {
    ...mapGetters(['reports']),
  },
  data() {
    return {
			isModalViewed: false,
      isOpened : false,
			reportIndex : '',
    }
  },
  methods: {
    ...mapActions(['getReports', 'deleteReport']),
		modal(id){
			this.reportIndex = id;
      this.isModalViewed = !this.isModalViewed
    },
    removeItem(id) {
      this.deleteReport(id);
      alert("제보가 삭제되었습니다.");
			this.$router.go();
    },
  },
  created() {
    this.getReports();
  },
}
</script>

<style scoped>
@import '../../assets/css/admin.css';
</style>