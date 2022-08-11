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

    <label>은행사진</label>
    <input 
      type="text"
      v-model="product.product.picture"
      disabled>

    <label>예금여부</label>
    <input 
      type="text"
      v-model="product.product.deposit"
      disabled>

    <button @click="editPortfolio(product)">수정</button>
    <button>삭제</button>
  </div>
</template>

<script>
export default {
  name: 'PortfolioEditItem',
  props: {
    product: Object
  },
  data() {
    return {
      payload: {
        amount: this.product.amount,
        condition_ids: this.product.condition_ids,
        start_date: this.product.start_date,
        end_date: this.product.end_date,
        option_id: this.product.option_id,
        portfolio_no: this.product.portfolio_no,
        product_id: this.product.product_id
      }
    }
  }
}
</script>

<style>

</style>