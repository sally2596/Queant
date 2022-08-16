<template>
  <transition on name="modal">
   <div class="modal-mask">
    <div class="modal-wrapper">
     <div class="modal-container">

      <div class="modal-header">
       <slot name="header">
        <h4 style="font-family: 'jua'; margin-top: 1rem;">{{ modalData.name }}</h4>
       </slot>
      </div>
      <p class="h1 m-0"><b-icon-x-circle type="button" class="modal-close-button"
      @click="$emit('close')"/>
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
            <option disabled>선택</option>
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
          <button class="btn btn-outline-success btn-sm mx-3" @click="[pushProductToPortfolio(payload), $emit('close')]">내 포트폴리오</button>
          <!-- <button class="btn btn-outline-success btn-sm mx-3" @click="[changeAppliedRate(payload.condition_ids), $emit('close')]">장바구니</button> -->
          <button class="btn btn-outline-success btn-sm mx-3" @click="[pushProductToCart(payload), $emit('close')]">장바구니</button>
        </div>
        <div v-else>
          <button class="btn btn-outline-success btn-sm mx-3" disabled>내 포트폴리오</button>
          <button class="btn btn-outline-success btn-sm mx-3" disabled>장바구니</button>
        </div>
       </slot>
      </div>
     </div>
    </div>
   </div>
  </transition>
</template>

<script>
import { mapActions, mapGetters, mapMutations } from 'vuex'
import { BIconXCircle } from 'bootstrap-icons-vue';
export default {
  name: 'Modal',
  props: {
    modalData: Object
  },
  components: {
		BIconXCircle
  },
  computed: {
    ...mapGetters(['portfolios', 'product'])
  },
  methods: {
    ...mapActions(['pushProductToPortfolio']),
    ...mapMutations(['PUSH_PRODUCT_TO_CART']),
    pushProductToCart(payload) {
      for (let productCondition of this.product.conditions) {
        for (let selectedConditionId of payload.condition_ids) {
          if (productCondition.condition_id === selectedConditionId) {
            this.payload.special_rate += productCondition.special_rate
          }
        }
      }
      this.PUSH_PRODUCT_TO_CART(payload)
    },
    checkForm() {
      if (this.payload.amount < 1)
        this.error.amount = '납입금액을 확인해주세요.'
      else this.error.amount = ''
      
      if (this.payload.start_date && this.payload.end_date && this.payload.start_date >= this.payload.end_date)
        this.error.date = '날짜를 확인해주세요.'
      else this.error.date = ''
    
      if (!this.error.amount && !this.error.date && this.payload.amount && this.payload.start_date && this.payload.end_date && this.payload.option_id !== '선택')
        this.isCheckedForm = true
      else this.isCheckedForm = false
    },
    changeAmount(money) {
      if (this.payload.amount + money >= 0)
        this.payload.amount += money
      else
        alert('납입금액을 확인해주세요.')
    },
    // 선택한 이자유형 & 개월수에 따라 payload.applied_rate 변경
    changeOption(option_id) {
      for (let option of this.product.options) {
        if (option.option_id === option_id) {
          this.payload.applied_rate = option.base_rate
          this.payload.applied_period = option.save_term
          this.payload.rate_type = option.rate_type
        }
      }
    }
  },
  watch: {
    payload: {
      deep: true,
      handler(v) {
        this.checkForm()
        this.changeOption(v.option_id)
      }
			// dateCheck(data) {
			// 	let date = data.split('-');

			// 	let year = parseInt(date[0])
			// 	let month = parseInt(date[1]);
			// 	let day = parseInt(date[2]);
			// 	let term = this.product.options[this.payload.option_id].save_term;
				

			// }
    }
  },
  data() {
    return {
      payload: {
        amount: this.modalData.amount?this.modalData.amount:0,
        condition_ids: [],
        start_date: null,
        end_date: null,
        option_id: this.modalData.selected_option_id?this.modalData.selected_option_id:'선택',
        product: this.modalData,
        applied_rate: null,
        applied_period: null,
        rate_type: null
      },
      error: {
        amount: '',
        date: ''
      },
      isCheckedForm: false
    }
  },
  created() {
    for (let option of this.product.options) {
        if (option.option_id === this.modalData.selected_option_id) {
          this.payload.applied_rate = option.base_rate
          this.payload.applied_period = option.save_term
        }
      }
  }
}
</script>

<style>
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
  width: 560px;
  height: 600px;
  margin: 0px auto;
  padding: 20px 60px;
  background-color: #fff;
  border-radius: 2px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, .33);
  transition: all .3s ease;
  font-family: Helvetica, Arial, sans-serif;
  overflow: scroll;
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


/*
 * The following styles are auto-applied to elements with
 * transition="modal" when their visibility is toggled
 * by Vue.js.
 *
 * You can easily play with the modal transition by editing
 * these styles.
 */


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

.modal-close-button {
	position:fixed; 
	height: 5em;
	margin-left: 441px; 
	margin-top: -130px;
	z-index: 1000;
}

</style>