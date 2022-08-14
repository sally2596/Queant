<template>
  <div>
    <table>
      <thead>
        <tr>
          <th>기관이름</th>
          <th>이름</th>
          <th>납입금액</th>
          <th>가입일</th>
          <th>만기일</th>
          <th>예금/적금</th>
          <!-- <th>우대사항</th> -->
          <th>적용금리</th>
        </tr>
      </thead><br>
      
      <tbody>
        <td>
          <input 
            type="text"
            v-model="bank.bank_name"
            disabled>
        </td>
        <td>
          <input 
            type="text"
            v-model="myProduct.product.name"
            disabled>
        </td>
        <td>
          <input 
            type="text"
            v-model="amount"
            disabled>
        </td>
        <td>
          <input 
            type="date"
            v-model="payload.start_date"
            disabled>
        </td>
        <td>
          <input 
            type="date"
            v-model="payload.end_date"
            disabled>
        </td>
        <td>
          <input 
            type="text"
            v-model="productType"
            disabled>
        </td>
        <!-- <td>
          {{ myProduct.conditions.length }}개 적용
        </td> -->
        <td>
          <input 
            type="text"
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

    <button class="btn btn-outline-success btn-sm mx-3" @click="openModal(payload)">수정</button>
    <button class="btn btn-outline-danger btn-sm mx-3" @click="deletePortfolio(myProduct.portfolio_id)">삭제</button>
    
    <!-- 모달 -->
    <portfolio-edit-modal
      v-if="showModal" @close="showModal=false"
      :modalData="modalData">
      <h3>모달 창 제목</h3>
    </portfolio-edit-modal>
    <hr>
    {{ myProduct }}
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
    ...mapGetters(['product', 'bank']),
    appliedRate() {
      let rate = this.myProduct.option.base_rate
      for (const condition of this.myProduct.conditions) {
        rate += condition.special_rate
      }
      return `${rate.toFixed(2)}%`
    },
    productType() {
      if (this.myProduct.product.deposit === true)
        return '예금'
      else
        return '적금'
    },
    amount() {
      return `${String(this.myProduct.amount).replace(/\B(?=(\d{3})+(?!\d))/g, ',')} 원`
    }
  },
  methods: {
    ...mapActions(['editPortfolio', 'deletePortfolio', 'fetchProduct', 'fetchBank']),
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
    openModal(payload) {
      this.modalData = payload,
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
        portfolio_id: this.myProduct.portfolio_id,
        product: this.myProduct.product
      },
      showModal: false,
      modalData: null
    }
  },
  created() {
    this.changeTimeStamp()
    this.fetchBank(this.myProduct.product.bank_id)
  }
}
</script>

<style>

</style>