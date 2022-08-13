<template>
  <div class="modal33">
    <div class="overlay33">
      <div class="modal-card33">
        <h2 v-if="this.customDto">사용자정의 상품 수정</h2>
        <h2 v-else>사용자정의 상품 추가</h2>
        <div class="int-area">
          <input type="text" v-model="custom_product_dto.product_name" id="name">
          <label class="form-label" for="name" autocomplete="off">* 상품 이름</label>
        </div>
        <div class="int-area">
          <input type="text" v-model="custom_product_dto.institution_name" id="institute">
          <label class="form-label" for="institute" autocomplete="off">* 기관명</label>
        </div>
        <div class="choose-area">
          <select v-model="custom_product_dto.deposit">
            <option :value="0">선택</option>
            <option :value='true'>예금</option>
            <option :value='false'>적금</option>
          </select>
          <label>* 예적금 분류</label>
        </div>
        <div class="choose-area" v-show="!custom_product_dto.deposit">
          <select v-model="custom_product_dto.fixed_rsrv">
            <option :value="0">선택</option>
            <option :value='true'>정액적립</option>
            <option :value='false'>자유적립</option>
          </select>
          <label>* 적립방식</label>
        </div>
        <div class="int-area">
          <input type="number" v-model="custom_product_dto.amount" id="amount">
          <label class="form-label" for="amount" autocomplete="off">* 금액</label>
        </div>
        <div class="int-area">
          <input type="number" v-model="custom_product_dto.base_rate" id="baserate">
          <label class="form-label" for="baserate" autocomplete="off">* 기본금리</label>
        </div>
        <div class="int-area">
          <input type="number" v-model="custom_product_dto.special_rate" id="special">
          <label class="form-label" for="special" autocomplete="off">* 우대금리</label>
        </div>
        <div class="date-area">
          <input
          v-model="custom_product_dto.start_date"
          type="date"
          id="startdate">
          <label for="startdate">시작일</label>
        </div>
        <div class="date-area">
          <input
          v-model="custom_product_dto.end_date"
          type="date"
          id="enddate">
          <label for="enddate">종료일</label>
        </div>
        <div>
          <button v-if="!this.customDto" @click="[addCustomProduct(custom_product_dto), $emit('close-modal')]">등록</button>
          <button v-else @click="[updateCustomProduct(custom_product_dto), $emit('close-modal')]">수정</button>
        </div>
        <div>
          <button @click="$emit('close-modal')">취소</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'

export default {
  methods: {
    ...mapActions(['addCustomProduct', 'updateCustomProduct'])
  },
  props: {
    customDto: Object
  },
  created() {
    if(this.customDto){
      this.custom_product_dto.product_id = this.customDto?.product_id;
      this.custom_product_dto.product_name= this.customDto?.product_name;
      this.custom_product_dto.institution_name= this.customDto?.institution_name;
      this.custom_product_dto.deposit= this.customDto?.deposit;
      this.custom_product_dto.amount=this.customDto?.amount;
      this.custom_product_dto.base_rate= this.customDto?.base_rate;
      this.custom_product_dto.special_rate= this.customDto?.special_rate;
      this.custom_product_dto.start_date= this.customDto?.start_date;
      this.custom_product_dto.end_date= this.customDto?.end_date;
      this.custom_product_dto.etc= this.customDto?.etc;
        
      if(!this.customDto.deposit) {
        this.custom_product_dto.fixed_rsrv= this.customDto?.fixed_rsrv;
      }
    }
  },
  computed: {
    ...mapGetters(['userInfo']),
    deposit() {
      return !this.custom_product_dto.deposit;
    }
  },
  data() {
    return {
      custom_product_dto:{
        amount:0,
        base_rate: 0,
        deposit: true,
        end_date: "",
        etc: "",
        fixed_rsrv: false,
        institution_name: "",
        product_name: "",
        special_rate: 0,
        start_date: "",
      }
    }
  }
}
</script>

<style>
.modal33, .overlay33 {
  width: 100%;
  height: 100%;
  position: fixed;
  left:0;
  top:0;
}

.overlay33{
  /* opacity: 0.8; */
  background-color: black;
}

.modal-card33{
  position: relative;
  max-width:40%;
  margin: auto;
  margin-top: 20%;
  padding:20px;
  background-color: white;
  min-height: 500px;
  z-index:10;
}
</style>