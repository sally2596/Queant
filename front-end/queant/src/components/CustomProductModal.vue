<template>
  <transition on name="modal">
    <div class="modal-mask">
      <div class="modal-wrapper">
      <div class="modal-container">

      <div class="modal-header">
      <slot name="header">

        <h4 style="font-family: 'jua'; margin-top: 1rem;">상품 정보</h4>

      </slot>
      </div>
      <p class="h1 m-0"><b-icon-x-circle type="button" class="modal-close-button" @click="$emit('close')"/>
			</p>
      <hr>

      <div class="modal-body">
        <slot name="body">
				<label>* 상품명</label>
				<input
					class="box"
					v-model="payload.product_name"
					type="text">
				<hr>
        <label>* 납임금액(원)</label>
				<input
					v-model="payload.amount"
					type="number"
					class="box"
					required>
				<br><br>
				<button class="btn btn-outline-success btn-sm mx-1" @click="changeAmount(10000)">+1만원</button>
				<button class="btn btn-outline-success btn-sm mx-1" @click="changeAmount(50000)">+5만원</button>
				<button class="btn btn-outline-success btn-sm mx-1" @click="changeAmount(100000)">+10만원</button>
				<button class="btn btn-outline-danger btn-sm mx-1" @click="changeAmount(-10000)">-1만원</button>
				<button class="btn btn-outline-danger btn-sm mx-1" @click="changeAmount(-50000)">-5만원</button>
				<button class="btn btn-outline-danger btn-sm mx-1" @click="changeAmount(-100000)">-10만원</button>
				<br>
        <p>{{ error.amount }}</p>
				<hr>
        <label class="label">기관명</label>
        <input
					class="box"
          v-model="payload.institution_name"
          type="text"
          required>
        <br>
        <label class="label">* 예적금 분류</label>
        <select class="box" v-model="payload.deposit">
          <option :value="0">선택</option>
          <option :value='true'>예금</option>
          <option :value='false'>적금</option>
        </select><br>

        <div v-if="!payload.deposit">
          <label class="label">* 적립방식</label>
          <select class="box" v-model="payload.fixed_rsrv">
            <option :value="0">선택</option>
            <option :value='true'>정액적립</option>
            <option :value='false'>자유적립</option>
          </select>
        </div>

        <label class="label">* 기본금리(%)</label>
        <input
          class="box"
          type="number" 
          v-model="payload.base_rate">
				<br>
        <label class="label">우대금리(%)</label>
        <input
          class="box"
          type="number"
          v-model="payload.special_rate">
        <br>
        <label class="label">* 가입일</label>
        <input
					class="box"
          v-model="payload.start_date"
          type="date">        
        <br>
        <div>
          <label class="label">* 투자 기간</label>
          <br>
          <input v-model="payload.save_term" value="6" type="radio" class="btn-check" name="saving-time" id="saving-months-6" autocomplete="off">
          <label class="btn btn-outline-success" for="saving-months-6">6개월</label>
          <input v-model="payload.save_term" value="12" type="radio" class="btn-check" name="saving-time" id="saving-months-12" autocomplete="off">
          <label class="btn btn-outline-success" for="saving-months-12">12개월</label>
          <input v-model="payload.save_term" value="18" type="radio" class="btn-check" name="saving-time" id="saving-months-18" autocomplete="off">
          <label class="btn btn-outline-success" for="saving-months-18">18개월</label>
          <input v-model="payload.save_term" value="24" type="radio" class="btn-check" name="saving-time" id="saving-months-24" autocomplete="off">
          <label class="btn btn-outline-success" for="saving-months-24">24개월</label>
          <input v-model="payload.save_term" value="36" type="radio" class="btn-check" name="saving-time" id="saving-months-36" autocomplete="off">
          <label class="btn btn-outline-success" for="saving-months-36">36개월</label>
        </div>
        </slot>
      </div>

      <div class="modal-footer">
       <slot name="footer">
        <p>{{ error.require }}</p>
        <div v-if="isCheckedForm">
          <button v-if="!this.customDto" class="btn btn-outline-success btn-sm mx-3" @click="[addCustomProduct(payload), $emit('close')]">등록</button>
          <button v-else class="btn btn-outline-success btn-sm mx-3" @click="[updateCustomProduct(payload), $emit('close')]">수정</button>
        </div>
        <div v-else>
          <button v-if="!this.customDto" class="btn btn-outline-success btn-sm mx-2" disabled>등록</button>
          <button v-else class="btn btn-outline-success btn-sm mx-2" disabled>수정</button>
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
  props: {
    customDto: Object
  },
  components: {
		BIconXCircle
  },
  data() {
    return {
      payload: {
        amount: 0,
        base_rate: 0,
        deposit: null,
        etc: '',
        fixed_rsrv: false,
        institution_name: '',
        product_name: '',
        special_rate: 0,
        start_date: '',
        save_term: 0
      },
      error: {
        require: '',
        amount: '',
      },
      isCheckedForm: false
    }
  },
  methods: {
    ...mapActions(['addCustomProduct', 'updateCustomProduct']),
    checkForm() {
      if (!this.payload.amount || !this.payload.base_rate || !this.payload.deposit===null && !this.payload.save_term || !this.payload.product_name || !this.payload.start_date)
        this.error.require = '필수 입력사항(*)을 확인해주세요.'
      else this.error.require = ''

      if (this.payload.amount < 1)
        this.error.amount = '납입금액을 확인해주세요.'
      else this.error.amount = ''
      
      if (!this.error.amount  && !this.error.require && this.payload.amount && this.payload.start_date && this.payload.save_term)
        this.isCheckedForm = true
      else this.isCheckedForm = false
    },
    changeAmount(money) {
      if (this.payload.amount + money > 0)
        this.payload.amount += money
      else
        alert('납입금액을 확인해주세요.')
    }
  },
  watch: {
    payload: {
      deep: true,
      handler() {
        this.checkForm()
      }
    }
  },
  computed: {
    ...mapGetters(['userInfo']),
    deposit() {
      return !this.payload.deposit;
    }
  },
  created() {
    if(this.customDto){
      this.payload.product_id = this.customDto?.product_id;
      this.payload.product_name= this.customDto?.product_name;
      this.payload.institution_name= this.customDto?.institution_name;
      this.payload.deposit= this.customDto?.deposit;
      this.payload.amount=this.customDto?.amount;
      this.payload.base_rate= this.customDto?.base_rate;
      this.payload.special_rate= this.customDto?.special_rate;
      this.payload.start_date= this.customDto?.start_date;
      this.payload.save_term= this.customDto?.save_term;
      this.payload.etc= this.customDto?.etc;
        
      if(!this.customDto.deposit) {
        this.payload.fixed_rsrv= this.customDto?.fixed_rsrv;
      }
    }
  }
}
</script>

<style scoped>
.box {
	float: right;
	width: 160px;
	text-align:right;
}

.label {
	line-height:30px;
}
</style>