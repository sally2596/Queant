<template>
	<transition on name="modal">
	<div class="modal-mask">
		<div class="modal-wrapper">
		<div class="modal-container">
			<div class="modal-header" >
				<h1 style="font-family: 'jua'; margin: 1rem;">제보 상품 등록</h1>
			</div>
				<p class="h1 m-0"><b-icon-x-circle type="button" style="position:fixed; height: 5em; margin-left:400px; margin-top: -150px;" @click="$emit('close-modal')"/>
				</p>

			
			<div class="modal-body">
				<form>
					<p style="font-weight:bold; padding-left:10px;">* 표시는 필수 입력사항입니다.</p>
					<div>
						<h3 style="font-family: 'jua'; margin-top: 1rem;">상품 상세 정보</h3>
						<br>
						<div class="choose-area" style="width:350px">
							<label>* 상품등록경로 </label>
							<select v-model="product.scode_id">
								<option v-for="opt in D_code" v-bind:value="opt.value" v-bind:key="opt">
									{{ opt.text }}
								</option>
							</select><br>
						</div>

						<div class="btn-area" style="width:350px; border-bottom: 1px solid #000;">
							<label style="font-size: 18px; color: #999; margin:0 130px 25px 10px;">* 예적금</label>
							<input v-model="product.deposit" :value="false" type="radio" class="btn-check" id="deposit-false" autocomplete="off">
							<label class="btn btn-outline-success" for="deposit-false">예금</label>
							<input v-model="product.deposit" :value="true" type="radio" class="btn-check" id="deposit-true" autocomplete="off">
							<label class="btn btn-outline-success" for="deposit-true">적금</label><br>
						</div>
						
						<div class="int-area" style="width:350px">
							<input type="number" v-model="product.age_min" id="ageMin">
							<label class="form-label" for="ageMin" autocomplete="off">최소 연령</label>
						</div>
						<div class="int-area" style="width:350px">
							<input type="number" v-model="product.age_max" id="ageMax">
							<label class="form-label" for="ageMax" autocomplete="off">최대 연령</label>
						</div>
						<div class="int-area" style="width:350px">
							<input type="number" v-model="product.budget_min" id="budMin">
							<label class="form-label" for="budMin" autocomplete="off">최소 예산</label>
						</div>
						<div class="int-area" style="width:350px">
							<input type="number" v-model="product.budget_max" id="budMax">
							<label class="form-label" for="budMax" autocomplete="off">최대 예산</label>
						</div>
						<div class="int-area" style="width:350px">
							<input type="number" v-model="product.term_min" id="termMin">
							<label class="form-label" for="termMin" autocomplete="off">최소 기간</label>
						</div>
						<div class="int-area" style="width:350px">
							<input type="number" v-model="product.term_max" id="termMax">
							<label class="form-label" for="termMax" autocomplete="off">최대 기간</label>
						</div>
						<div class="int-area" style="width:350px">
							<input type="text" v-model="product.etc" id="etc">
							<label class="form-label" for="etc" autocomplete="off">기타</label>
						</div>
					</div>
					<div>
						<h3 style="font-family: 'jua'; margin-top: 1rem;">특징  
							<button type="button" class="btn btn-sm btn-outline-secondary" @click="addTraitForm()">특징 추가</button>
						</h3>
						<br>
						<div v-for="(trait, index) in traits" v-bind:key="trait">
							<div class="choose-area" style="width:350px">
								<label>* 특징 </label>
								<select v-model="trait.scode_id"><option v-for="opt in E_code" v-bind:value="opt.value" v-bind:key="opt">{{ opt.text }}</option></select><br>
							</div>
							<button type="button" class="btn btn-sm btn-outline-secondary" @click="removeTraitForm(index)">특징 삭제</button>
							<br><br>
						</div>
					</div>

					<div>
						<h3 style="font-family: 'jua'; margin-top: 1rem;">옵션  <button type="button" class="btn btn-sm btn-outline-secondary" @click="addOptionForm()">옵션 추가</button></h3>
						<br>
						<div v-for="(option, index) in options" v-bind:key="option">
							<div class="int-area" style="width:350px">
								<input type="text" v-model="option.base_rate" id="base">
								<label class="form-label" for="base" autocomplete="off">* 기본금리</label>
							</div>
							<div class="int-area" style="width:350px">
								<input type="text" v-model="option.high_base_rate" id="hbase">
								<label class="form-label" for="hbase" autocomplete="off">최고금리</label>
							</div>
							<div class="int-area" style="width:350px">
								<input type="text" v-model="option.save_term" id="term">
								<label class="form-label" for="term" autocomplete="off">* 저축기간</label>
							</div>
							<div class="choose-area" style="width:350px">
								<label>* 금리유형</label>
								<select v-model="option.rate_type">
									<option v-for="opt in rate" v-bind:value="opt.value" v-bind:key="opt">{{ opt.text }}</option>
								</select>
							</div>

							<div class="choose-area" style="width:350px">
								<label>적립방식</label>
								<select v-model="option.rsrv_type"><option v-for="opt in rsrv" v-bind:value="opt.value" v-bind:key="opt">{{ opt.text }}</option></select><br>
							</div>
							<button type="button" class="btn btn-sm btn-outline-secondary" @click="removeOptionForm(index)">옵션 삭제</button>
							<br><br>
						</div>
					</div>
					<br>
					<div>
						<h3 style="font-family: 'jua'; margin-top: 1rem;">우대조건  <button type="button" class="btn btn-sm btn-outline-secondary" @click="addConditionForm()">우대조건 추가</button></h3>
						<br>
						<div v-for="(condition, index) in conditions" v-bind:key="condition">
							<div class="choose-area" style="width:350px">
								<label>* 우대사항</label> 
								<select v-model="condition.scode_id">
									<option v-for="opt in B_code" v-bind:value="opt.value" v-bind:key="opt">{{ opt.text }}</option>
								</select><br>
							</div>
							<div class="int-area" style="width:350px">
								<input type="text" v-model="condition.special_rate" id="special">
								<label class="form-label" for="special" autocomplete="off">* 우대금리</label>
							</div>
							<div class="int-area" style="width:350px">
								<input type="text" v-model="condition.condition_info" id="info">
								<label class="form-label" for="info" autocomplete="off">세부내용</label>
							</div>
							<button type="button" class="btn btn-sm btn-outline-secondary" @click="removeConditionForm(index)">우대조건 삭제</button>
							<br><br>
						</div>
					</div>
					<br>
					<div>
						<h3 style="font-family: 'jua'; margin-top: 1rem;">가입방법  <button type="button" class="btn btn-sm btn-outline-secondary" @click="addJoinForm()">가입방법 추가</button></h3>
						<br>
						<div v-for="(joinway, index) in joinways" v-bind:key="joinway">
							<div class="choose-area" style="width:350px">
								<label class="form-label">*가입방법</label> 
								<select v-model="joinway.scode_id">
									<option v-for="opt in A_code" v-bind:value="opt.value" v-bind:key="opt">{{ opt.text }}</option>
								</select>
							</div>
							<br>
							<button type="button" class="btn btn-sm btn-outline-secondary" @click="removeJoinForm(index)">가입방법 삭제</button>
							<br><br>
						</div>
					</div>
				</form>
				<form>
					<div class="btn-area">
						<button type="submit" @click="registProduct(report.report_product_id)"> 등록</button>
					</div>
				</form>
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
			}
    }
	},
  methods: {
    ...mapActions(['getReport', 'updateReport']),
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
		this.product.bank_name = this.report.bank_name;
		this.product.name = this.report.product_name;
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

<style scoped>
.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, .5);
  display: table;
  transition: opacity .3s ease;
}


.modal-wrapper {
  display: table-cell;
  vertical-align: middle;
  color:#555555;
}


.modal-container {
  width: 500px;
  height: 50%;
  margin: 0px auto;
  padding: 20px 30px;
  background-color: #fff;
  border-radius: 2px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, .33);
  transition: all .3s ease;
  font-family: Helvetica, Arial, sans-serif;
}


.modal-header h3 {
  margin-top: 0;
  color: #42b983;
}


.modal-body {
  margin: 20px 0;
}


.modal-default-button {
  float: right;
}

.modal-enter {
  opacity: 0;
}


.modal-leave-active {
  opacity: 0;
}


.modal-enter .modal-container,
.modal-leave-active .modal-container {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}

.box {
	float: right;
	width: 160px;
	text-align:right;
}

</style>
