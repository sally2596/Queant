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
            v-model="customProduct.institution_name"
            disabled>
        </td>
        <td>
          <input 
            type="text"
            v-model="customProduct.product_name"
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
          우대사항
        </td> -->
        <td>
          <input 
            type="text"
            v-model="appliedRate"
            disabled>
        </td>
      </tbody>
    </table>

    <button class="btn btn-outline-success btn-sm mx-2" @click="openModal()">수정</button>
    <button class="btn btn-outline-danger btn-sm mx-2" @click="deleteCustomProduct(payload.product_id)">삭제</button>

    <!-- 모달 -->
    <CustomProductModal 
      v-if="showModal" 
      @close="showModal=false"
      :customDto="payload">
    </CustomProductModal>
    <hr>
  </div>
</template>

<script>
import { mapActions } from 'vuex'
import CustomProductModal from '@/components/CustomProductModal.vue'

export default {
  name: 'CustomProductItem',
  components: { CustomProductModal },
  props: {
    customProduct: Object
  },
  computed: {
    appliedRate() {
      return `${(this.customProduct.base_rate + this.customProduct.special_rate).toFixed(2)}%`
    },
    productType() {
      if (this.customProduct.deposit === true)
        return '예금'
      else
        return '적금'
    },
    amount() {
      return `${String(this.customProduct.amount).replace(/\B(?=(\d{3})+(?!\d))/g, ',')} 원`
    }
   },
  methods: {
    ...mapActions(['deleteCustomProduct']),
    changeTimeStamp() {
      var date = new Date(this.customProduct.start_date)
      var year = date.getFullYear().toString(); //년도 뒤에 두자리
      var month = ("0" + (date.getMonth() + 1)).slice(-2); //월 2자리 (01, 02 ... 12)
      var day = ("0" + date.getDate()).slice(-2); //일 2자리 (01, 02 ... 31)
      this.payload.start_date = year + "-" + month + "-" + day

      var date = new Date(this.customProduct.end_date)
      var year = date.getFullYear().toString(); //년도 뒤에 두자리
      var month = ("0" + (date.getMonth() + 1)).slice(-2); //월 2자리 (01, 02 ... 12)
      var day = ("0" + date.getDate()).slice(-2); //일 2자리 (01, 02 ... 31)
      this.payload.end_date = year + "-" + month + "-" + day
    },
    openModal(){
      this.showModal = !this.showModal
    }
  },
  data() {
    return {
      payload: {
        amount: this.customProduct.amount,
        base_rate: this.customProduct.base_rate,
        deposit: this.customProduct.deposit,
        end_date: this.customProduct.end_date,
        etc: this.customProduct.etc,
        fixed_rsrv: this.customProduct.fixed_rsrv,
        institution_name: this.customProduct.institution_name,
        product_name: this.customProduct.product_name,
        special_rate: this.customProduct.special_rate,
        start_date: this.customProduct.start_date,
        product_id: this.customProduct.product_id
      },
      showModal: false,   
    }
  },
  created() {
    this.changeTimeStamp()
  }
}
</script>

<style>

</style>