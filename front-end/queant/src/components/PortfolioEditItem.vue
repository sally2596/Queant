<template>
  <div>
    <label>이름</label>
    <input 
      type="text"
      v-model="product.product.name"
      disabled>
    
    <label>납입금액</label>
    <input 
      type="number"
      v-model="payload.amount">
    
    <label>가입일</label>
    <input 
      type="date"
      v-model="payload.start_date">

    <label>만기일</label>
    <input 
      type="date"
      v-model="payload.end_date">

    <!-- <label>은행사진</label>
    <input 
      type="text"
      v-model="product.product.picture"
      disabled> -->

    <!-- <label>예금여부</label>
    <input 
      type="text"
      v-model="product.product.deposit"
      disabled> -->
    
    <!-- 적용 우대사항
    <div
      v-for="condition in payload.condition_ids"
      :key="condition">
      <label :for="condition.condition_id">{{ condition.condition_info }}</label>
      <input
        v-model="payload.condition_ids"
        type="checkbox"
        :value="condition.condition_id"
        :id="condition.condition_id">
    </div> -->
    
    <label>기본금리</label>
    <input 
      type="number"
      v-model="product.option.base_rate"
      disabled>
    
    <label>적용금리</label>
    <input 
      type="number"
      disabled>

    <label>포폴id</label>
    <input 
      v-model="product.portfolio_id"
      type="number"
      disabled>
    <button @click="editPortfolio(payload)">수정</button>
    <button @click="deletePortfolio(product.portfolio_id)">삭제</button>
  </div> 
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'PortfolioEditItem',
  props: {
    product: Object
  },
  methods: {
    ...mapActions(['editPortfolio', 'deletePortfolio']),
    // 타임스탬프 포맷(15자리 숫자)을 정상적인 날짜로 변경
    changeTimeStamp() {
      var date = new Date(this.product.start_date)
      var year = date.getFullYear().toString(); //년도 뒤에 두자리
      var month = ("0" + (date.getMonth() + 1)).slice(-2); //월 2자리 (01, 02 ... 12)
      var day = ("0" + date.getDate()).slice(-2); //일 2자리 (01, 02 ... 31)
      this.payload.start_date = year + "-" + month + "-" + day

      var date = new Date(this.product.end_date)
      var year = date.getFullYear().toString(); //년도 뒤에 두자리
      var month = ("0" + (date.getMonth() + 1)).slice(-2); //월 2자리 (01, 02 ... 12)
      var day = ("0" + date.getDate()).slice(-2); //일 2자리 (01, 02 ... 31)
      this.payload.end_date = year + "-" + month + "-" + day
    }
  },
  data() {
    return {
      payload: {
        amount: this.product.amount,
        condition_ids: this.product.conditions,
        start_date: null,
        end_date: null,
        option_id: this.product.option_id,
        portfolio_no: this.product.portfolio_no,
        product_id: this.product.product_id,
        portfolio_id: this.product.portfolio_id
      }
    }
  },
  created() {
    this.changeTimeStamp()
  }
}
</script>

<style>

</style>