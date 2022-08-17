<template>
	<transition on name="modal">
   <div class="modal-mask">
    <div class="modal-wrapper">
     <div class="modal-container">

      <div class="modal-header">
       <slot name="header">
        <h4 style="font-family: 'jua'; margin-top: 1rem;">제보 상품 등록</h4>
       </slot>
      </div>
      <div class="h1 m-0"><b-icon-x-circle type="button" class="modal-close-button"
      @click="$emit('close-modal')"/>
			</div>
      <hr>

      <div class="modal-body">
       <slot name="body">
					<label>* 은행명</label>
					<input class="box" v-model="product.bank_name" type="text" list="bankList"/>
						<datalist id="bankList">
							<option v-for="opt in bank" v-bind:value="opt.value" v-bind:key="opt">
								{{ opt.value }}
							</option>
						</datalist>
					<br><br>
					<label>* 상품명</label>
          <input
            v-model="product.name"
            type="text"
						class="box"
						required>
					<br><br>
					<label>* 상품등록경로</label>
						<select class="box" v-model="product.scode_id">
							<option v-for="opt in D_code" v-bind:value="opt.value" v-bind:key="opt">
								{{ opt.text }}
							</option>
						</select>
					<br><br>
					<label>* 예적금</label>
						<select class="box" v-model="product.deposit">
							<option v-for="opt in dep" v-bind:value="opt.value" v-bind:key="opt">
								{{ opt.text }}
							</option>
						</select>
					<br><br>
          <label>최소 연령</label>
          <input
            v-model="product.age_min"
            type="number"
						class="box">
					<br><br>
					<label>최대 연령</label>
          <input
            v-model="product.age_max"
            type="number"
						class="box">
					<br><br>
					<label>최소 예산</label>
          <input
            v-model="product.budget_min"
            type="number"
						class="box">
					<br><br>
					<label>최대 예산</label>
          <input
            v-model="product.budget_max"
            type="number"
						class="box">
					<br><br>
					<label>최소 기간</label>
          <input
            v-model="product.term_min"
            type="number"
						class="box">
					<br><br>
					<label>최대 기간</label>
          <input
            v-model="product.term_max"
            type="number"
						class="box">
					<br><br>
					<label>기타</label>
          <input
            v-model="product.etc"
            type="text"
						class="box">
					<br><br>
          <hr>
					<h3 style="font-family: 'jua'; margin-top: 1rem;">특징  
						<button type="button" class="btn btn-sm btn-outline-secondary" @click="addTraitForm()">특징 추가</button>
					</h3>
					<br>
					<div v-for="(trait, index) in traits" v-bind:key="trait">
						<div>
							<label>* 특징</label>
							<select class="box" v-model="trait.scode_id">
								<option v-for="opt in E_code" v-bind:value="opt.value" v-bind:key="opt">
									{{ opt.text }}
								</option>
							</select>
							<br><br>
							<button type="button" class="btn btn-sm btn-outline-secondary" @click="removeTraitForm(index)">특징 삭제</button>
							<br><br>
						</div>
					</div>
					<hr>
					<h3 style="font-family: 'jua'; margin-top: 1rem;">옵션
						<button type="button" class="btn btn-sm btn-outline-secondary" @click="addOptionForm()">옵션 추가</button>
					</h3>
					<br>
					<div v-for="(option, index) in options" v-bind:key="option">
					<label>* 기본금리</label>
          <input
            v-model="option.base_rate"
            type="number"
						class="box"
						required>
					<br><br>
					<label>최고금리</label>
          <input
            v-model="option.high_base_rate"
            type="number"
						class="box">
					<br><br>
					<label>* 저축기간</label>
          <input
            v-model="option.save_term"
            type="number"
						class="box"
						required>
					<br><br>
					<label>* 금리유형</label>
						<select class="box" v-model="option.rate_type">
							<option v-for="opt in rate" v-bind:value="opt.value" v-bind:key="opt">{{ opt.text }}</option>
						</select>
					<br><br>
					<label>적립방식</label>
						<select class="box" v-model="option.rsrv_type">
							<option v-for="opt in rsrv" v-bind:value="opt.value" v-bind:key="opt">{{ opt.text }}</option>
						</select>
					<br><br>
					<button type="button" class="btn btn-sm btn-outline-secondary" @click="removeOptionForm(index)">옵션 삭제</button>
					<br><br>
					</div>
					<hr>
					<h3 style="font-family: 'jua'; margin-top: 1rem;">우대조건  
						<button type="button" class="btn btn-sm btn-outline-secondary" @click="addConditionForm()">우대조건 추가</button>
					</h3>
					<br>
					<div v-for="(condition, index) in conditions" v-bind:key="condition">
						<div>
							<label>* 우대사항</label>
							<select class="box" v-model="condition.scode_id">
								<option v-for="opt in B_code" v-bind:value="opt.value" v-bind:key="opt">
									{{ opt.text }}
								</option>
							</select>
							<br><br>
							<label>* 우대금리</label>
							<input
								v-model="condition.special_rate"
								type="number"
								class="box">
							<br><br>
							<label>세부내용</label>
							<input
								v-model="condition.condition_info"
								type="text"
								class="box">
							<br><br>
							<button type="button" class="btn btn-sm btn-outline-secondary" @click="removeConditionForm(index)">우대조건 삭제</button>
							<br><br>
						</div>
					</div>
					<hr>
					<h3 style="font-family: 'jua'; margin-top: 1rem;">가입방법  
						<button type="button" class="btn btn-sm btn-outline-secondary" @click="addJoinForm()">가입방법 추가</button>
					</h3>
					<br>
					<div v-for="(joinway, index) in joinways" v-bind:key="joinway">
						<div>
							<label>* 가입방법</label>
							<select class="box" v-model="joinway.scode_id">
								<option v-for="opt in A_code" v-bind:value="opt.value" v-bind:key="opt">
									{{ opt.text }}
								</option>
							</select>
							<br><br>
							<button type="button" class="btn btn-sm btn-outline-secondary" @click="removeJoinForm(index)">가입방법 삭제</button>
							<br><br>
						</div>
					</div>
			</slot>
      </div>

			<div class="modal-footer">
			<slot name="footer">
        <div>
          <button class="btn btn-outline-success btn-sm mx-3" @click="[registProduct(report.report_product_id), $emit('close')]">등록</button>
        </div>
			</slot>
      </div>
		</div>
    </div>
	</div>
  </transition>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import { BIconXCircle } from 'bootstrap-icons-vue';
export default {
	name: 'ReportProductView',
	components: {
		BIconXCircle
  	},
	data() {
    return {
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
			rate : [
				{ text: '단리', value: false },
				{ text: '복리', value: true },
			],
			rsrv: [
				{ text: '자유적립', value: false },
				{ text: '정액적립', value: true },
			],
			dep: [
				{ text: '예금', value: false },
				{ text: '적금', value: true },
			],
			bank : [
				{ value: '우리은행' }, { value: 'SC제일' }, { value: '대구은행' }, { value: '부산은행' }, { value: '광주은행' }, { value: '제주은행' }, { value: '전북은행' }, { value: '경남은행' },
				{ value: 'IBK' },{ value: 'KDB' }, { value: '국민은행' }, { value: '신한은행' }, { value: '농협은행' }, { value: '하나은행' }, { value: '케이뱅크' }, { value: '수협은행' },
				{ value: '카카오뱅크' }, { value: '토스뱅크' }, { value: '우체국' }, { value: '애큐온저축' }, { value: 'OSB저축' }, { value: '디비저축' }, { value: '스카이저축' }, { value: '민국저축' },
				{ value: '푸른저축' }, { value: 'HB저축' }, { value: '키움' }, { value: '더케이저축' }, { value: '조은저축' }, { value: 'SBI저축' }, { value: '바로저축' }, { value: '다올저축' },
				{ value: '유안타저축' }, { value: '고려저축' }, { value: '국제저축' }, { value: 'DH저축' }, { value: '흥국저축' }, { value: '우리저축' }, { value: '인성저축' }, { value: '금화저축' },
				{ value: '인천저축' }, { value: '모아저축' }, { value: '대백저축' }, { value: '유니온저축' }, { value: 'MS저축' }, { value: '안국저축' }, { value: '남양저축' }, { value: '부림저축' },
				{ value: '키움저축' }, { value: '삼정저축' }, { value: '평택상호저축' }, { value: '안양저축' }, { value: '영진저축' }, { value: '융창저축' }, { value: '세람저축' }, { value: '페퍼저축' },
				{ value: '상상인' }, { value: '한화저축' }, { value: 'CK저축' }, { value: '대명상호저축' }, { value: '우리금융저축' }, { value: '청주저축' }, { value: '한성저축' }, { value: '상상인플러스' },
				{ value: '아산저축' }, { value: '오투저축' }, { value: '스타저축' }, { value: '대한저축' }, { value: '동양저축' }, { value: '더블저축' }, { value: '센트럴저축' }, { value: '스마트저축' },
				{ value: '한투' }, { value: '라온저축' }, { value: '드림저축' }, { value: '대아' }, { value: '머스트' }, { value: '참저축' }, { value: '오성저축' }, { value: '대원저축' }, { value: 'SNT' },
				{ value: '솔브' }, { value: '동원제일저축' }, { value: '조흥저축' }, { value: '진주저축' }, { value: '예가람저축' }, { value: 'JT저축' }, { value: '삼호저축' }, { value: 'NH' },
				{ value: '대신저축' }, { value: 'IBK저축' }, { value: 'BNK저축' }, { value: 'KB저축' }, { value: '하나저축' }, { value: '친애' }, { value: '신한저축' }, { value: '웰컴저축' }, { value: 'OK저축' },
			],
			trait : {
				scode_id : 'E001'
			},
      option : {
        base_rate : '0',
        high_base_rate : '',
        save_term : '0',
        rate_type : false,
        rsrv_type : false
      },
      condition : {
        scode_id : 'B001',
        special_rate : '0',
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
			},
			isCheckedForm: false
    }
	},
  methods: {
    ...mapActions(['getReport', 'updateReport', 'fetchBanks']),
		registProduct(idx) {
			this.productDetail.product = this.product;
			this.productDetail.options = this.options;
			this.productDetail.conditions = this.conditions;
			this.productDetail.joinway = this.joinways;
			this.productDetail.trait_set = this.traits;
			this.product.report_id = idx;
			this.updateReport(this.productDetail);
			alert("상품이 등록되었습니다.");
			this.$router.go();
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
      this.option = {
        base_rate : '0',
        high_base_rate : '',
        save_term : '0',
        rate_type : false,
        rsrv_type : false
			};
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
      this.condition = {
        scode_id : 'B001',
        special_rate : '0',
        condition_info : ''
      };
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
		setRateFalse(idx) {
			this.options[idx].rate_type = false;
		},
		setRateTrue(idx) {
			this.options[idx].rate_type = true;
		}
  },
  props: {
    reportID : String
  },
  created() {
		this.getReport(this.reportID);
		this.options.push(this.option);
		this.option = {
        base_rate : '0',
        high_base_rate : '',
        save_term : '0',
        rate_type : false,
        rsrv_type : false
		};
  },
  computed: {
    ...mapGetters(['report']),
  },
}
</script>

<style>
@import '@/assets/css/modal.css';
</style>
