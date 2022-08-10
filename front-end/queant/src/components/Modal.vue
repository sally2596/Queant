<template>
  <transition on name="modal">
   <div class="modal-mask">
    <div class="modal-wrapper">
     <div class="modal-container">

      <div class="modal-header">
       <slot name="header">
        <label for="">가상 포트폴리오</label>
        <input
          v-model="payload.portfolio_no" 
          type="number">
       </slot>
      </div>
      {{ product }}
      <hr>
      {{ filters }}
      <div class="modal-body">
       <slot name="body">
          <label for="">예상 시작날짜</label>
          <input 
            v-model="payload.start_date"
            type="date">

          <label for="">예상 종료날짜</label>
          <input 
            v-model="payload.end_date"
            type="date">

          <label for="">상품 이름</label>
          <input 
            v-model="product.name" type="text"
            disabled>
       </slot>
      </div>

      <div class="modal-footer">
       <slot name="footer">
        <button @click="addProductToPortfolio(payload)">적용</button>
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
  name: 'Modal',
  props: {
    product: Object
  },
  computed: {
    ...mapGetters(['filters'])
  },
  methods: {
    ...mapActions(['addProductToPortfolio'])
  },
  data() {
    return {
      payload: {
        start_date: null,
        end_date: null,
        portfolio_no: null,
        product_id: this.product.product_id
      //   amount: 100000,
      //   amount_fixed: 10,
      //   condition_ids: this.filters.conditions,
      //   option_id: 2992,
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