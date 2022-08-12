<template>
    <navbar/>
    <header id="title-div">
      <h1 class="title" id="title">특판상품 관리</h1>
    </header>
    <br><br>
    <div id="section-float" v-if="isOpened">
      <div>
        <form>
          Product 정보 입력
          <br>
          은행이름 <input type="text" v-model="product.bank_name"><br>
          상품명 <input type="text" v-model="product.name"><br>
          S코드(D-상품등록경로) <input type="text" v-model="product.scode_id"><br>
					<!-- deposit <input type="text" v-model="product.deposit"><br> -->
          min age <input type="text" v-model="product.age_min"><br>
          max age <input type="text" v-model="product.age_max"><br>
          min budget <input type="text" v-model="product.budget_min"><br>
          max budget <input type="text" v-model="product.budget_max"><br>
          min term <input type="text" v-model="product.term_min"><br>
          max term <input type="text" v-model="product.term_max"><br>
          기타 <input type="text" v-model="product.etc"><br>
				</form>
				<br>
				Trait set
        <button type="button" class="btn btn-sm btn-outline-secondary" @click="addTraitForm()">특징 추가</button>
        <form v-for="(trait, index) in traits" v-bind:key="trait">
          Trait set 정보 입력
          <br>
          S코드명(E-특징) <input type="text" v-model="trait.scode_id"><br>
          <button type="button" class="btn btn-sm btn-outline-secondary" @click="removeTraitForm(index)">특징 삭제</button>
          <br>
				</form>
        <br>
        <br>
        Option
        <button type="button" class="btn btn-sm btn-outline-secondary" @click="addOptionForm()">옵션 추가</button>
        <form v-for="(option, index) in options" v-bind:key="option">
          Option 정보 입력
          <br>
          기본금리 <input type="text" v-model="option.base_rate"><br>
          최고금리 <input type="text" v-model="option.high_base_rate"><br>
          저축기간 <input type="text" v-model="option.save_term"><br>
          <!-- 금리유형명 <input type="text" v-model="option.rate_type"><br>
          저축방식 <input type="text" v-model="option.rsrv_type"><br> -->
          <button type="button" class="btn btn-sm btn-outline-secondary" @click="removeOptionForm(index)">옵션 삭제</button>
          <br>
				</form>
        <br>
        Conditon
        <button type="button" class="btn btn-sm btn-outline-secondary" @click="addConditionForm()">우대조건 추가</button>
        <form v-for="(condition, index) in conditions" v-bind:key="condition">
          Condition 정보 입력
          <br>
          S코드명(B-우대사항) <input type="text" v-model="condition.scode_id"><br>
          우대금리 <input type="text" v-model="condition.special_rate"><br>
          세부내용 <input type="text" v-model="condition.condition_info"><br>
          <button type="button" class="btn btn-sm btn-outline-secondary" @click="removeConditionForm(index)">우대조건 삭제</button>
          <br>
				</form>
        <br>
				Joinway
        <button type="button" class="btn btn-sm btn-outline-secondary" @click="addJoinForm()">가입방법 추가</button>
        <form v-for="(joinway, index) in joinways" v-bind:key="joinway">
          Joinway 정보 입력
          <br>
          S코드명(A-가입방법) <input type="text" v-model="joinway.scode_id"><br>
          <button type="button" class="btn btn-sm btn-outline-secondary" @click="removeJoinForm(index)">가입방법 삭제</button>
          <br>
				</form>
        <form>
          <button type="button" class="btn btn-sm btn-outline-secondary" @click="closeInputForm()">창 닫기</button>
          <button type="button" class="btn btn-sm btn-outline-secondary" @click="registProduct(report.report_product_id)">정보 전송</button>
        </form>
      </div>
    </div>
    <br><br>
    <div v-for="report in reports" v-bind:key="report">
      {{ report }}
      <div>
        <button type="button" class="btn btn-sm btn-outline-secondary" @click="openInputForm(report.report_product_id)">입력</button>
        <button type="button" class="btn btn-sm btn-outline-secondary" @click="removeItem(report.report_product_id)">삭제</button>
      </div>
    </div> 
    <br><br>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import _ from 'lodash'
import Navbar from '@/components/Navbar.vue'

export default {
  name: 'ProductSpecialPlusView',
  components: { Navbar },
  computed: {
    ...mapGetters(['reports', 'report']),
  },
  data() {
    return {
      isOpened : false,
			trait : {
				scode_id : ''
			},
      option : {
        base_rate : '',
        high_base_rate : '',
        save_term : '',
        rate_type : false,
        rsrv_type : false
      },
      condition : {
        scode_id : '',
        special_rate : '',
        condition_info : ''
      },
			joinway : {
				scode_id : ''
			},
      product : {
				report_id : '',
        bank_name : '',
        name : '',
        scode_id : '',
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
			this.product = {},
      await this.getReport(id);
      this.product.bank_name = this.report.bank_name;
      this.product.name = this.report.product_name;
			console.log(this.product);
			this.options.push(this.option);
      this.option = {};
      this.isOpened = true;
    },
    closeInputForm() {
      this.isOpened = false;
    },
    removeItem(id) {
      this.deleteReport(id);
      alert("제보가 삭제되었습니다.");
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
    },
    removeTraitForm(idx) {
      this.traits.splice(idx,1);
    },
    addOptionForm() {
      this.options.push(this.option);
      this.option = {};
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
    },
    removeConditionForm(idx) {
      this.conditions.splice(idx,1);
    },
		addJoinForm() {
      this.joinways.push(this.joinway);
      this.joinway = {};
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