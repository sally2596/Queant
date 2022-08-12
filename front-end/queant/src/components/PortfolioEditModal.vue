<template>
  <transition on name="modal">
   <div class="modal-mask">
    <div class="modal-wrapper">
     <div class="modal-container">
      
      <div class="modal-header">
       <slot name="header">
        <label>상품명</label>
        <input 
          v-model="modalData.product.name"
          type="text"
          disabled>
       </slot>
      </div>
      <hr>

      <div class="modal-body">
       <slot name="body">
          <label for="">납임금액</label>
          <input
            v-model="payload.amount"
            type="number">
          <br> 
          이자유형 & 개월수
          <select v-model="payload.option_id">
            <option selected disabled>선택</option>
            <option 
              v-for="option in product.options"
              :key="option"
              :value="option.option_id">
              <p v-if="option.rate_type">복리</p>
              <p v-else>단리</p>
              {{ option.save_term }}개월
              {{ option.base_rate }}%
            </option>
          </select>
          <hr>
          
          우대사항
          <div
            v-for="condition in product.conditions"
            :key="condition">
            <label :for="condition.condition_id">
              [설명] {{ condition.value }}<br>
              [추가금리] {{ condition.special_rate }}%
            </label>
            <input
              v-model="payload.condition_ids"
              type="checkbox"
              :value="condition.condition_id"
              :id="condition.condition_id">
          </div>
          <hr>

          <label for="">예상 가입날짜</label>
          <input 
            v-model="payload.start_date"
            type="date">

          <label for="">예상 만기날짜</label>
          <input 
            v-model="payload.end_date"
            type="date">
       </slot>
      </div>

      <div class="modal-footer">
       <slot name="footer">
        <button @click="[editPortfolio(payload), $emit('close')]">수정</button>
        <button class="modal-default-button" @click="$emit('close')">
         닫기
        </button>
       </slot>
      </div>
     </div>
    </div>
   </div>
  </transition>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'

export default {
  name: 'PortfolioEditModal',
  props: {
    modalData: Object
  },
  computed: {
    ...mapGetters(['product'])
  },
  methods: {
    ...mapActions(['editPortfolio'])
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
        product_id: this.modalData.product_id
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
  width: 300px;
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
</style>