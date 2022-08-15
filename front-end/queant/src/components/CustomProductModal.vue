<template>
  <transition on name="modal">
    <div class="modal-mask">
      <div class="modal-wrapper">
      <div class="modal-container">

      <div class="modal-header">
      <slot name="header">
        <div class="int-area">
          <label>* 상품명</label>
          <input
            style="width: 170px;"
            v-model="payload.product_name"
            type="text">

        </div>
      </slot>
      </div>
      <p class="h1 m-0"><b-icon-x-circle type="button" style="position:fixed; height: 5em; margin-left:400px; margin-top: -140px;" @click="$emit('close')"/>
			</p>
      <hr>

      <div class="modal-body">
        <slot name="body">
        <label>* 납임금액(원)</label>
        <input
          style="width: 130px;"
          v-model="payload.amount"
          type="number"
          required>
        <button class="btn btn-outline-success btn-sm mx-1" @click="changeAmount(10000)">+1만원</button>
        <button class="btn btn-outline-success btn-sm mx-1" @click="changeAmount(50000)">+5만원</button>
        <button class="btn btn-outline-success btn-sm mx-1" @click="changeAmount(100000)">+10만원</button>
        <button class="btn btn-outline-danger btn-sm mx-1" @click="changeAmount(-10000)">-1만원</button>
        <button class="btn btn-outline-danger btn-sm mx-1" @click="changeAmount(-50000)">-5만원</button>
        <button class="btn btn-outline-danger btn-sm mx-1" @click="changeAmount(-100000)">-10만원</button>
        <p>{{ error.amount }}</p>

        <label>기관명</label>
        <input
          v-model="payload.institution_name"
          type="text"
          required>
        
        <label>* 예적금 분류</label>
        <select v-model="payload.deposit">
          <option :value="0">선택</option>
          <option :value='true'>예금</option>
          <option :value='false'>적금</option>
        </select><br>

        <div v-if="!payload.deposit">
          <label>* 적립방식</label>
          <select v-model="payload.fixed_rsrv">
            <option :value="0">선택</option>
            <option :value='true'>정액적립</option>
            <option :value='false'>자유적립</option>
          </select>
        </div>

        <label>* 기본금리(%)</label>
        <input
          style="width: 80px;"
          type="number" 
          v-model="payload.base_rate">
        
        <label>우대금리(%)</label>
        <input
          style="width: 80px;"
          type="number"
          v-model="payload.special_rate">
        <br>
        <label>* 가입일</label>
        <input
          v-model="payload.start_date"
          type="date">        
        <br>
        <label for="enddate">* 만기일</label>
        <input
          v-model="payload.end_date"
          type="date">
        <p>{{ error.date }}</p>
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
        end_date: '',
        etc: '',
        fixed_rsrv: false,
        institution_name: '',
        product_name: '',
        special_rate: 0,
        start_date: '',
      },
      error: {
        require: '',
        amount: '',
        date: '',
      },
      isCheckedForm: false
    }
  },
  methods: {
    ...mapActions(['addCustomProduct', 'updateCustomProduct']),
    checkForm() {
      if (!this.payload.amount || !this.payload.base_rate || !this.payload.deposit===null && !this.payload.end_date || !this.payload.product_name || !this.payload.start_date)
        this.error.require = '필수 입력사항(*)을 확인해주세요.'
      else this.error.require = ''

      if (this.payload.amount < 1)
        this.error.amount = '납입금액을 확인해주세요.'
      else this.error.amount = ''
      
      if (this.payload.start_date && this.payload.end_date && this.payload.start_date >= this.payload.end_date)
        this.error.date = '날짜를 확인해주세요.'
      else this.error.date = ''
    
      if (!this.error.amount && !this.error.date && !this.error.require && this.payload.amount && this.payload.start_date && this.payload.end_date)
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
      this.payload.end_date= this.customDto?.end_date;
      this.payload.etc= this.customDto?.etc;
        
      if(!this.customDto.deposit) {
        this.payload.fixed_rsrv= this.customDto?.fixed_rsrv;
      }
    }
  }
}
</script>

<style>

</style>