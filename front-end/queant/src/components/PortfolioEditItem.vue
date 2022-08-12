<template>
  <div>
    <table>
      <thead>
        <tr>
          <th>이름</th>
          <th>납입금액</th>
          <th>가입일</th>
          <th>만기일</th>
          <th>예금/적금</th>
          <th>우대사항</th>
          <th>적용금리</th>
        </tr>
      </thead><br>
      
      <tbody>
        <td>
          <input 
            type="text"
            v-model="myProduct.product.name"
            disabled>
        </td>
        <td>
          <input 
            type="number"
            v-model="myProduct.amount"
            disabled>
        </td>
        <td>
          <input 
            type="date"
            v-model="startDate"
            disabled>
        </td>
        <td>
          <input 
            type="date"
            v-model="endDate"
            disabled>
        </td>
        <td>
          <input 
            type="text"
            v-model="productType"
            disabled>
        </td>
        <td>
          {{ myProduct.conditions.length }}개 적용
        </td>
        <td>
          <input 
            type="number"
            v-model="appliedRate"
            disabled>
        </td>
      </tbody>
    </table>

    <!-- <label>은행사진</label>
    <input 
      type="text"
      v-model="myProduct.myProduct.picture"
      disabled> -->
    
    <!-- 적용 우대사항
    <div
      v-for="condition in product.conditions"
      :key="condition">
      <label :for="condition.condition_id">{{ condition.condition_info }}</label>
      <input
        v-model="payload.condition_ids"
        type="checkbox"
        :value="condition.condition_id"
        :id="condition.condition_id">
    </div> -->

    <!-- <button @click="editPortfolio(payload)">수정</button> -->
    <button @click="openModal(payload)">수정</button>
    <button @click="deletePortfolio(myProduct.portfolio_id)">삭제</button>
    
     <!-- 모달 -->
    <portfolio-edit-modal
      v-if="showModal" @close="showModal=false"
      :modalData="modalData">
      <h3>모달 창 제목</h3>
    </portfolio-edit-modal>
    <hr>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
import PortfolioEditModal from '@/components/PortfolioEditModal.vue'

export default {
  name: 'PortfolioEditItem',
  components: { PortfolioEditModal },
  props: {
    myProduct: Object
  },
  computed: {
    ...mapGetters(['product']),
    appliedRate() {
      let rate = this.myProduct.option.base_rate
      for (const condition1 of this.product.conditions) {
        for (const condition2 of this.myProduct.conditions) {
          if (condition1.condition_id === condition2.condition_id) {
            rate += condition1.special_rate
          }
        }
      }
      return rate.toFixed(2)
    },
    productType() {
      if (this.myProduct.product.depost === true)
        return '예금'
      else
        return '적금'
    },
    startDate() {
      var date = new Date(this.myProduct.start_date)
      var year = date.getFullYear().toString(); //년도 뒤에 두자리
      var month = ("0" + (date.getMonth() + 1)).slice(-2); //월 2자리 (01, 02 ... 12)
      var day = ("0" + date.getDate()).slice(-2); //일 2자리 (01, 02 ... 31)
      return year + "-" + month + "-" + day
    },
    endDate() {
      var date = new Date(this.myProduct.end_date)
      var year = date.getFullYear().toString(); //년도 뒤에 두자리
      var month = ("0" + (date.getMonth() + 1)).slice(-2); //월 2자리 (01, 02 ... 12)
      var day = ("0" + date.getDate()).slice(-2); //일 2자리 (01, 02 ... 31)
      return year + "-" + month + "-" + day
    }
  },
  methods: {
    ...mapActions(['editPortfolio', 'deletePortfolio', 'fetchProduct']),
    // 타임스탬프 포맷(15자리 숫자)을 정상적인 날짜로 변경
    changeTimeStamp() {
      var date = new Date(this.myProduct.start_date)
      var year = date.getFullYear().toString(); //년도 뒤에 두자리
      var month = ("0" + (date.getMonth() + 1)).slice(-2); //월 2자리 (01, 02 ... 12)
      var day = ("0" + date.getDate()).slice(-2); //일 2자리 (01, 02 ... 31)
      this.payload.start_date = year + "-" + month + "-" + day

      var date = new Date(this.myProduct.end_date)
      var year = date.getFullYear().toString(); //년도 뒤에 두자리
      var month = ("0" + (date.getMonth() + 1)).slice(-2); //월 2자리 (01, 02 ... 12)
      var day = ("0" + date.getDate()).slice(-2); //일 2자리 (01, 02 ... 31)
      this.payload.end_date = year + "-" + month + "-" + day
    },
    openModal(value) {
      this.modalData = value,
      this.showModal = true
    }
  },
  data() {
    return {
      payload: {
        amount: this.myProduct.amount,
        conditions: this.myProduct.conditions,
        condition_ids: this.myProduct.condition_ids,
        start_date: null,
        end_date: null,
        option_id: this.myProduct.option_id,
        portfolio_no: this.myProduct.portfolio_no,
        product_id: this.myProduct.product_id,
        portfolio_id: this.myProduct.portfolio_id
      },
      showModal: false,
      modalData: null
    }
  },
  created() {
    this.changeTimeStamp()
    this.fetchProduct(this.myProduct.product_id)
  }
}
</script>

<style>

</style>