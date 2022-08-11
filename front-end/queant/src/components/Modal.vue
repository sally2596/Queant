<template>
  <transition on name="modal">
   <div class="modal-mask">
    <div class="modal-wrapper">
     <div class="modal-container">

      <div class="modal-header">
       <slot name="header">

         <select 
          v-for="number in customPortfolio.length+1"
          :key="number">
          <option selected disabled>선택</option>
          <option :value="number">{{ number }}</option>
        </select>
       </slot>
      </div>
      <hr>

      <div class="modal-body">
       <slot name="body">
         <label for="">상품 이름</label>
         <input 
           v-model="product.name" type="text"
           disabled>

          <label for="">납임금액</label>
          <input
            v-model="filters.amount"
            type="text"
            disabled>
          
          <label for="">기간(개월)</label>
          <input
            v-model="filters.period"
            type="text"
            disabled>

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
        <button @click="[addProductToCustomPortfolio(payload), $emit('close')]">적용</button>
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
import { mapActions, mapGetters, mapMutations } from 'vuex'

export default {
  name: 'Modal',
  props: {
    product: Object
  },
  computed: {
    ...mapGetters(['filters', 'customPortfolio'])
  },
  methods: {
    ...mapActions(['addProductToCustomPortfolio']),
    ...mapMutations(['POP_PRODUCT_FROM_CART'])
  },
  data() {
    return {
      payload: {
        start_date: null,
        end_date: null,
        portfolio_no: null,
        product_id: this.product.product_id,
        option_id: this.product.selected_option_id
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