<template>
  <transition on name="modal">
   <div class="modal-mask">
    <div class="modal-wrapper">
     <div class="modal-container">
			<div class="modal-header">
       <slot name="header">
        <h4 style="font-family: 'jua'; margin-top: 1rem;">{{ modalData.product.name }}</h4>
       </slot>
      </div>
			<p class="h1 m-0"><b-icon-x-circle type="button" class="modal-close-button" @click="$emit('close')"/>
			</p>
      <hr>
      <div class="modal-body">
       <slot name="body">
          <label>납임금액(원)</label>
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
          이자유형 & 개월수
          <select class="box" v-model="payload.option_id">
            <option selected disabled>선택</option>
            <option 
              v-for="option in product.options"
              :key="option"
              :value="option.option_id"
              required>
              <p v-if="option.rate_type">복리</p>
              <p v-else>단리</p>
              {{ option.save_term }}개월
              {{ option.base_rate }}%
            </option>
          </select>
          <hr>
					<div>우대사항</div>
					<div></div>
					<br>
					<table class="table table-hover">
						<thead>
							<tr class="text-center">
								<th scope="col">설명</th>
								<th scope="col">추가금리</th>
								<th scope="col">선택</th>
							</tr>
						</thead>
						<tbody v-for="condition in product.conditions" v-bind:key="condition">
							<tr scope="row">
								<td class="col-8 text-center">{{ condition.condition_info || condition.value }}</td>
								<td class="col-4 text-center">{{ condition.special_rate }}%</td>
								<td class="col-1 text-center">
									<input
										v-model="payload.condition_ids"
										type="checkbox"
										class="box"
										:value="condition.condition_id"
										:id="condition.condition_id">
								</td>
							</tr>
						</tbody>
					</table>
					<hr>
					<div>
						예상 가입일
						<input 
							v-model="payload.start_date"
							type="date"
							class="box"
							required>
					</div>
					<br>
					<div>
						예상 만기일
						<input 
							v-model="payload.end_date"
							type="date"
							class="box"
							required>
					</div>
          {{ error.date }}
       </slot>
			</div>
      <div class="modal-footer">
       <slot name="footer">
        <div v-if="isCheckedForm">
          <button class="btn btn-outline-success btn-sm mx-2" @click="[editPortfolio(payload), $emit('close')]">수정</button>
        </div>
        <div v-else>
          <button class="btn btn-outline-success btn-sm mx-2" disabled>수정</button>
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
  name: 'PortfolioEditModal',
  props: {
    modalData: Object
  },
	components: {
		BIconXCircle
  },
  computed: {
    ...mapGetters(['product'])
  },
  methods: {
    ...mapActions(['editPortfolio', 'fetchProduct']),
    checkForm() {
      if (this.payload.amount < 1)
        this.error.amount = '납입금액을 확인해주세요.'
      else this.error.amount = ''
      
      if (this.payload.start_date && this.payload.end_date && this.payload.start_date >= this.payload.end_date)
        this.error.date = '날짜를 확인해주세요.'
      else this.error.date = ''
    
      if (!this.error.amount && !this.error.date && this.payload.amount && this.payload.start_date && this.payload.end_date)
        this.isCheckedForm = true
      else this.isCheckedForm = false
    },
    changeAmount(money) {
      if (this.payload.amount + money >= 0)
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
  data() {
    return {
      payload: {
        amount: this.modalData.amount,
        condition_ids: this.modalData.condition_ids,
        start_date: this.modalData.start_date,
        end_date: this.modalData.end_date,
        option_id: this.modalData.option_id,
        portfolio_id: this.modalData.portfolio_id,
        portfolio_no: 0,
        product_id: this.modalData.product_id,
        conditions: this.modalData.conditions
      },
      error: {
        amount: '',
        date: ''
      },
      isCheckedForm: false
    }
  },
  created() {
    this.fetchProduct(this.modalData.product_id)
  }
}
</script>

<style scoped>
@import '@/assets/css/modal.css';
</style>