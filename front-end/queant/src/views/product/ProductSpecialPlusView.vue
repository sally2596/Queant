<template>
    <navbar/>
    <header id="title-div">
      <h1 class="title" id="title">특판상품 관리</h1>
    </header>
    <section class="product_section">
    <div id="section-float" v-if="isOpened">
      <div>
        <form @submit="checkForm()" @reset="onReset()">
					<div style="font-weight:bold">*표시는 필수 입력사항입니다.</div>
					<br><br>
					<div>
						<h5>상품 상세 정보 입력</h5>
						<br>
						<!-- 은행이름 <input type="text" v-model="product.bank_name"><br>
						상품명 <input type="text" v-model="product.name"><br> -->
						*상품등록경로 <select v-model="product.scode_id"><option v-for="opt in D_code" v-bind:value="opt.value" v-bind:key="opt">{{ opt.text }}</option></select><br>
						*예적금
						<input v-model="product.deposit" :value="false" type="radio" class="btn-check" id="deposit-false" autocomplete="off">
						<label class="btn btn-outline-success" for="deposit-false">예금</label>
						<input v-model="product.deposit" :value="true" type="radio" class="btn-check" id="deposit-true" autocomplete="off">
						<label class="btn btn-outline-success" for="deposit-true">적금</label><br>
						최소 연령 <input type="text" v-model="product.age_min"><br>
						최대 연령 <input type="text" v-model="product.age_max"><br>
						최소 예산 <input type="text" v-model="product.budget_min"><br>
						최대 예산 <input type="text" v-model="product.budget_max"><br>
						최소 기간 <input type="text" v-model="product.term_min"><br>
						최대 기간 <input type="text" v-model="product.term_max"><br>
						기타 <input type="text" v-model="product.etc"><br>
					</div>
					<br>
          <div>
						<h5>특징  <button type="button" class="btn btn-sm btn-outline-secondary" @click="addTraitForm()">특징 추가</button></h5>
						<br>
						<div v-for="(trait, index) in traits" v-bind:key="trait">
							특징 정보 입력
							<br>
							*특징 <select v-model="trait.scode_id"><option v-for="opt in E_code" v-bind:value="opt.value" v-bind:key="opt">{{ opt.text }}</option></select><br>
							<button type="button" class="btn btn-sm btn-outline-secondary" @click="removeTraitForm(index)">특징 삭제</button>
							<br><br>
						</div>
					</div>
					<br>
					<div>
						<h5>옵션  <button type="button" class="btn btn-sm btn-outline-secondary" @click="addOptionForm()">옵션 추가</button></h5>
						<br>
						<div v-for="(option, index) in options" v-bind:key="option">
							옵션 정보 입력
							<br>
							*기본금리 <input type="text" v-model="option.base_rate"><br>
							최고금리 <input type="text" v-model="option.high_base_rate"><br>
							*저축기간 <input type="text" v-model="option.save_term"><br>
							*금리유형
							<input v-model="option.rate_type" :value="false" type="radio" class="btn-check" id="rate-false" autocomplete="off">
							<label class="btn btn-outline-success" for="rate-false">단리</label>
							<input v-model="option.rate_type" :value="true" type="radio" class="btn-check" id="rate-true" autocomplete="off">
							<label class="btn btn-outline-success" for="rate-true">복리</label><br>
							적립방식
							<input v-model="option.rsrv_type" :value="false" type="radio" class="btn-check" id="rsrv-false" autocomplete="off">
							<label class="btn btn-outline-success" for="rsrv-false">자유적립</label>
							<input v-model="option.rsrv_type" :value="true" type="radio" class="btn-check" id="rsrv-true" autocomplete="off">
							<label class="btn btn-outline-success" for="rsrv-true">정액적립</label><br>
							<button type="button" class="btn btn-sm btn-outline-secondary" @click="removeOptionForm(index)">옵션 삭제</button>
							<br><br>
						</div>
					</div>
					<br>
					<div>
						<h5>우대조건  <button type="button" class="btn btn-sm btn-outline-secondary" @click="addConditionForm()">우대조건 추가</button></h5>
						<br>
						<div v-for="(condition, index) in conditions" v-bind:key="condition">
							우대조건 정보 입력
							<br>
							*우대사항 <select v-model="condition.scode_id"><option v-for="opt in B_code" v-bind:value="opt.value" v-bind:key="opt">{{ opt.text }}</option></select><br>
							*우대금리 <input type="text" v-model="condition.special_rate"><br>
							세부내용 <input type="text" v-model="condition.condition_info"><br>
							<button type="button" class="btn btn-sm btn-outline-secondary" @click="removeConditionForm(index)">우대조건 삭제</button>
							<br><br>
						</div>
					</div>
					<br>
					<div>
						<h5>가입방법  <button type="button" class="btn btn-sm btn-outline-secondary" @click="addJoinForm()">가입방법 추가</button></h5>
						<br>
						<div v-for="(joinway, index) in joinways" v-bind:key="joinway">
							가입방법 정보 입력
							<br>
							*가입방법 <select v-model="joinway.scode_id"><option v-for="opt in A_code" v-bind:value="opt.value" v-bind:key="opt">{{ opt.text }}</option></select><br>
							<button type="button" class="btn btn-sm btn-outline-secondary" @click="removeJoinForm(index)">가입방법 삭제</button>
							<br><br>
						</div>
					</div>
				</form>
        <form>
          <input type="reset" class="btn btn-sm btn-outline-secondary" @click="closeInputForm()" value="창 닫기">
          <input type="submit" class="btn btn-sm btn-outline-secondary" @click="registProduct(report.report_product_id)" value="정보 전송">
        </form>
      </div>
    </div>
    <br><br>
		</section>
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
							<button type="button" class="btn btn-sm btn-outline-secondary" @click="openInputForm(report.report_product_id)">입력</button>
							<button type="button" class="btn btn-sm btn-outline-secondary" @click="removeItem(report.report_product_id)">삭제</button>
						</td>
					</tr>
				</tbody>
			</table>
		</section>
    <br><br>
			<button id="show-modal" @click="showModal = true">Show Modal</button>
			<!-- use the modal component, pass in the prop -->
			<Modal v-if="showModal" @close="showModal = false">
				<!--
				you can use custom content here to overwrite
				default content
				-->
			</Modal>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import _ from 'lodash'
import Navbar from '@/components/Navbar.vue'
import ModalView from "@/components/ModalView.vue"

export default {
  name: 'ProductSpecialPlusView',
  components: { Navbar, ModalView },
  computed: {
    ...mapGetters(['reports', 'report']),
  },
  data() {
    return {
			showModal: false,
      isOpened : false,
			A_code : [
				{ text: '인터넷', value: 'A001' },
				{ text: '스마트폰', value: 'A002' },
				{ text: '영업점', value: 'A003' },
				{ text: '전화', value: 'A004' },
				{ text: '기타', value: 'A005' },
			],
			B_code : [
				{ text: '비대면', value: 'B001' },
				{ text: '모바일', value: 'B002' },
				{ text: '급여', value: 'B003' },
				{ text: '연금', value: 'B004' },
				{ text: '공과금', value: 'B005' },
				{ text: '재예치', value: 'B006' },
				{ text: '입출금통장', value: 'B007' },
				{ text: '카드', value: 'B008' },
				{ text: '인터넷', value: 'B009' },
				{ text: '첫거래', value: 'B010' },
				{ text: '기타', value: 'B011' },
			],
			D_code : [
				{ text: '금융감독원API', value: 'D001' },
				{ text: '우체국API', value: 'D002' },
				{ text: 'user', value: 'D003' },
				{ text: 'superuser', value: 'D004' },
				{ text: 'admin', value: 'D005' },
			],
			E_code : [
				{ text: '특판', value: 'E001' },
				{ text: '청약', value: 'E002' },
				{ text: '군인', value: 'E003' },
			],
			trait : {
				scode_id : 'E001'
			},
      option : {
        base_rate : '',
        high_base_rate : '',
        save_term : '',
        rate_type : false,
        rsrv_type : false
      },
      condition : {
        scode_id : 'B001',
        special_rate : '',
        condition_info : ''
      },
			joinway : {
				scode_id : 'A001'
			},
      product : {
				report_id : '',
        bank_name : '',
        name : '',
        scode_id : 'D001',
        deposit : false,
        age_min : '',
        age_max : '',
        budget_min : '',
        budget_max : '',
        term_min : '',
        term_max : '',
        etc : '',
        enabled : true
      },
			traits : [
			],
      options : [
      ],
      conditions : [
      ],
			joinways : [
			],
			productDetail : {
				product : '',
				options : [],
				conditions : [],
				joinway : [],
				trait_set : []
			}
    }
  },
  methods: {
    ...mapActions(['getReports', 'deleteReport', 'getReport', 'updateReport']),
		async openInputForm(id) {
			//고쳐야됨
			this.options = [];
      await this.getReport(id);
      this.product.bank_name = this.report.bank_name;
      this.product.name = this.report.product_name;
			console.log(this.product);
			this.options.push(this.option);
      this.option = {};
      this.isOpened = true;
    },
    closeInputForm() {
			console.log(this.product.deposit);
      //this.isOpened = false;
    },
    removeItem(id) {
      this.deleteReport(id);
      alert("제보가 삭제되었습니다.");
			this.$router.go();
    },
    registProduct(idx) {
			if (this.options.length === 0) {
        alert("옵션 정보는 최소 한개 이상 필요합니다.");
      } else {
        this.productDetail.product = this.product;
				this.productDetail.options = this.options;
				this.productDetail.conditions = this.conditions;
				this.productDetail.joinway = this.joinways;
				this.productDetail.trait_set = this.traits;
				this.product.report_id = idx;
				this.updateReport(this.productDetail);
      }
    },
		addTraitForm() {
      this.traits.push(this.trait);
      this.trait = {};
			this.trait.scode_id = 'E001';
    },
    removeTraitForm(idx) {
      this.traits.splice(idx,1);
    },
    addOptionForm() {
      this.options.push(this.option);
      this.option = {};
			this.option.rate_type = false;
			this.option.rsrv_type = false;
    },
    removeOptionForm(idx) {
      if (idx===0 && this.options.length === 1) {
        alert("옵션 정보는 최소 한개 이상 필요합니다.");
      } else {
        this.options.splice(idx,1);
      }
    },
    addConditionForm() {
      this.conditions.push(this.condition);
      this.condition = {};
			this.condition.scode_id = 'B001';
    },
    removeConditionForm(idx) {
      this.conditions.splice(idx,1);
    },
		addJoinForm() {
      this.joinways.push(this.joinway);
      this.joinway = {};
			this.joinway.scode_id = 'A001';
    },
    removeJoinForm(idx) {
      this.joinways.splice(idx,1);
    },
  },
  created() {
    this.getReports();
  },
}
</script>

<style>
@import '../../assets/css/admin.css';
</style>