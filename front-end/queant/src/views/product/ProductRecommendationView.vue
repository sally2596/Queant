<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">맞춤상품검색</h1>
  </header>

  <section class="product_section">
    <div class="search_filter">
      <div class="filter_button_wrap">
        <button @click="deposit_OnOff" :class="{'filter_active' : filter_deposit==true}" class="filter_button" id="filter_button_a">예금</button>
        <button @click="saving_OnOff" :class="{'filter_active' : filter_savings==true}" class="filter_button" id="filter_button_b">적금</button>
      </div>

      <!-- 예금 -->
      <div v-if="filter_deposit" class="container row" id="filter_deposit">

        <div class="col-lg-3 p-4 m-2 border-bottom">
          <h6 class="mb-3"><strong>기관</strong></h6>
          <div id="banks_container">
            <div
              v-for="bankType in keywords.bank_type"
              :key="bankType.scode_id">
              <input
                v-model="filtersForDeposit.bankType"
                type="checkbox" 
                class="btn-check" 
                :value="bankType.scode_id"
                :id="`deposit-${bankType.scode_id}`" 
                autocomplete="off">
              <label 
                class="btn btn-outline-success"
                :for="`deposit-${bankType.scode_id}`">
                {{ bankType.scode_value }}
              </label>
            </div>
          </div>
        </div>

        <!-- 필수 -->
        <div class="col-lg-5 p-4 m-2 border-bottom">
          <h6 class="mb-3"><strong>*예치 기간</strong></h6>
          <input v-model="filtersForDeposit.period" :value="6" type="radio" class="btn-check" id="deposit-months-6" autocomplete="off">
          <label class="btn btn-outline-success" for="deposit-months-6">6개월</label>
          <input v-model="filtersForDeposit.period" :value="12" type="radio" class="btn-check" id="deposit-months-12" autocomplete="off">
          <label class="btn btn-outline-success" for="deposit-months-12">12개월</label>
          <input v-model="filtersForDeposit.period" :value="18" type="radio" class="btn-check" id="deposit-months-18" autocomplete="off">
          <label class="btn btn-outline-success" for="deposit-months-18">18개월</label>
          <input v-model="filtersForDeposit.period" :value="24" type="radio" class="btn-check" id="deposit-months-24" autocomplete="off">
          <label class="btn btn-outline-success" for="deposit-months-24">24개월</label>
          <input v-model="filtersForDeposit.period" :value="36" type="radio" class="btn-check" id="deposit-months-36" autocomplete="off">
          <label class="btn btn-outline-success" for="deposit-months-36">36개월</label>
        </div>

        <!-- 필수 -->
        <div class="col-lg-3 p-4 m-2 border-bottom">
          <h6 class="mb-3"><strong>*예치금(원)</strong></h6>
          <input
          style=
            "  
              width: 100%;
              padding: 20px 10px 10px;
              background-color: transparent;
              border: none;
              border-bottom: 1px solid #000;
              font-size: 18px; color: #000;
              outline: none;
            "
            required
            v-model="filtersForDeposit.amount"
            type="number"
            class="p-1"
            id="deposit-money"
            name="deposit-money">
          <div class="d-flex mt-1">
            <button style="width:5rem; height:2rem;" class="btn btn-outline-success btn-sm mx-1" @click="changeDepositAmount(10000)">+1만</button>
            <button style="width:5rem; height:2rem;" class="btn btn-outline-success btn-sm mx-1" @click="changeDepositAmount(50000)">+5만</button>
            <button style="width:5rem; height:2rem;" class="btn btn-outline-success btn-sm mx-1" @click="changeDepositAmount(100000)">+10만</button>
          </div>
          <div class="d-flex">
            <button style="width:5rem; height:2rem;" class="btn btn-outline-danger btn-sm mx-1" @click="changeDepositAmount(-10000)">-1만</button>
            <button style="width:5rem; height:2rem;" class="btn btn-outline-danger btn-sm mx-1" @click="changeDepositAmount(-50000)">-5만</button>
            <button style="width:5rem; height:2rem;" class="btn btn-outline-danger btn-sm mx-1" @click="changeDepositAmount(-100000)">-10만</button>
          </div>
        </div>

        <div class="col-lg-5 p-4 m-2 border-bottom">
          <h6 class="mb-3"><strong>가입 방법</strong></h6>
          <div id="banks_container">
            <div
              v-for="joinway in keywords.joinway"
              :key="joinway.scode_id">
              <input
                v-model="filtersForDeposit.joinway"
                type="checkbox" 
                class="btn-check" 
                :value="joinway.scode_id"
                :id="`deposit-${joinway.scode_id}`" 
                autocomplete="off">
              <label 
                class="btn btn-outline-success"
                :for="`deposit-${joinway.scode_id}`">
                {{ joinway.scode_value }}
              </label>
            </div>
          </div>
        </div>

        <div class="col-lg-3 p-4 m-2 border-bottom">
          <h6 class="mb-3"><strong>이자 유형</strong></h6>
          <input 
            v-model="filtersForDeposit.isSimpleInterest"
            type="radio"
            :value="false"
            class="btn-check"
            id="deposit-simple-interest-true" 
            autocomplete="off">
          <label 
            class="btn btn-outline-success" 
            for="deposit-simple-interest-true">
            단리
          </label>
          <input
            v-model="filtersForDeposit.isSimpleInterest"
            type="radio"
            :value="true"
            class="btn-check" 
            id="deposit-simple-interest-false"
            autocomplete="off">
          <label 
            class="btn btn-outline-success" 
            for="deposit-simple-interest-false">
            복리
          </label>
        </div>

        <div class="col-lg-3 p-4 m-2 border-bottom">
          <h6 class="mb-3"><strong>특수 조건</strong></h6>
          <div id="banks_container">
            <div
            v-for="traitSet in keywords.trait_set"
            :key="traitSet.scode_id">
              <input 
                v-model="filtersForDeposit.traitSet"
                type="checkbox" 
                class="btn-check" 
                :value="traitSet.scode_id"
                :id="`deposit-${traitSet.scode_id}`" 
                autocomplete="off">
              <label 
                class="btn btn-outline-success"
                :for="`deposit-${traitSet.scode_id}`">
                {{ traitSet.scode_value }}
              </label>
            </div>
          </div>
        </div>

        <div class="col-lg-12 p-4 m-2 border-bottom">
          <h6 class="mb-3"><strong>희망 기관
              <i class="fa-solid fa-circle-info">
                <span class="tooltiptext">선택하지 않으면 모두 검색됩니다.</span>
              </i>
            </strong>
          </h6>
          
          <div id="banks_container">
            <div 
              v-for="bank in keywords.bank"
              :key="bank.bank_id" v-show="bank.scode_id === filtersForDeposit.bankType[0] || bank.scode_id === filtersForDeposit.bankType[1]">
              <div>
                <input
                  v-model="filtersForDeposit.bank"
                  type="checkbox" 
                  class="btn-check"
                  :id="`deposit-${bank.bank_id}`" 
                  :value="bank.bank_id"
                  autocomplete="on">
                <label 
                  class="btn btn-outline-success" 
                  :for="`deposit-${bank.bank_id}`">
                  {{ bank.short_name }}
                </label>
              </div>
            </div>
          </div>
        </div>
        
        <div class="col-lg-12 p-4 m-2 border-bottom">
          <h6 class="mb-3"><strong>우대 조건</strong></h6>
          <div id="banks_container">
          <div
            v-for="condition in keywords.conditions"
            :key="condition.scode_id">
            <input
              v-model="filtersForDeposit.conditions"
              type="checkbox" 
              class="btn-check" 
              :id="`deposit-${condition.scode_id}`"
              :value="condition.scode_id"
              autocomplete="off">
            <label 
              class="btn btn-outline-success"
              :for="`deposit-${condition.scode_id}`">
              {{ condition.scode_value }}
            </label>
          </div>
          </div>
        </div>

        <div id="deposit-result-view">
          <button @click="fetchProductsByDepositFilters(filtersForDeposit)" class="btn btn-outline-primary btn-sm">결과 보기</button>
        </div>
      </div>

      <!-- 적금 -->
      <div v-if="filter_savings" class="container row" id="filter_savings">

        <div class="col-lg-3 p-4 m-2 border-bottom">
          <h6 class="mb-3"><strong>기관</strong></h6>
          <div id="banks_container">
            <div
              v-for="bankType in keywords.bank_type"
              :key="bankType.scode_id">
              <input 
                v-model="filtersForSaving.bankType"
                :value="bankType.scode_id"
                type="checkbox" 
                class="btn-check" 
                :id="`saving-${bankType.scode_id}`" 
                autocomplete="off">
              <label 
                class="btn btn-outline-success" 
                :for="`saving-${bankType.scode_id}`">
                {{ bankType.scode_value }}
              </label>
            </div>
          </div>
        </div>
        
        <!-- 필수 입력 -->
        <div class="col-lg-5 p-4 m-2 border-bottom">
          <h6 class="mb-3"><strong>*적립 기간</strong></h6>
          <input v-model="filtersForSaving.period" value="6" type="radio" class="btn-check" name="saving-time" id="saving-months-6" autocomplete="off">
          <label class="btn btn-outline-success" for="saving-months-6">6개월</label>
          <input v-model="filtersForSaving.period" value="12" type="radio" class="btn-check" name="saving-time" id="saving-months-12" autocomplete="off">
          <label class="btn btn-outline-success" for="saving-months-12">12개월</label>
          <input v-model="filtersForSaving.period" value="18" type="radio" class="btn-check" name="saving-time" id="saving-months-18" autocomplete="off">
          <label class="btn btn-outline-success" for="saving-months-18">18개월</label>
          <input v-model="filtersForSaving.period" value="24" type="radio" class="btn-check" name="saving-time" id="saving-months-24" autocomplete="off">
          <label class="btn btn-outline-success" for="saving-months-24">24개월</label>
          <input v-model="filtersForSaving.period" value="36" type="radio" class="btn-check" name="saving-time" id="saving-months-36" autocomplete="off">
          <label class="btn btn-outline-success" for="saving-months-36">36개월</label>
        </div>

        <!-- 필수 입력 -->
        <div class="col-lg-3 p-4 m-2 border-bottom">
          <h6 class="mb-3"><strong>*월 적립금(원)</strong></h6>
          <input
            style="  width: 100%;
    padding: 20px 10px 10px;
    background-color: transparent;
    border: none;
    border-bottom: 1px solid #000;
    font-size: 18px; color: #000;
    outline: none;"
            v-model="filtersForSaving.amount"
            required
            type="number"
            class="p-1"
            id="saving-money"
            name="saving-money">
          <div class="d-flex mt-1">
            <button style="width:5rem; height:2rem;" class="btn btn-outline-success btn-sm mx-1" @click="changeSavingAmount(10000)">+1만</button>
            <button style="width:5rem; height:2rem;" class="btn btn-outline-success btn-sm mx-1" @click="changeSavingAmount(50000)">+5만</button>
            <button style="width:5rem; height:2rem;" class="btn btn-outline-success btn-sm mx-1" @click="changeSavingAmount(100000)">+10만</button>
          </div>
          <div class="d-flex">
            <button style="width:5rem; height:2rem;" class="btn btn-outline-danger btn-sm mx-1" @click="changeSavingAmount(-10000)">-1만</button>
            <button style="width:5rem; height:2rem;" class="btn btn-outline-danger btn-sm mx-1" @click="changeSavingAmount(-50000)">-5만</button>
            <button style="width:5rem; height:2rem;" class="btn btn-outline-danger btn-sm mx-1" @click="changeSavingAmount(-100000)">-10만</button>
          </div>
        </div>
        
        <div class="col-lg-5 p-4 m-2 border-bottom">
          <h6 class="mb-3"><strong>가입 방법</strong></h6>
          <div id="banks_container">
            <div 
              v-for="joinway in keywords.joinway"
              :key="joinway.scode_id">
              <input 
                v-model="filtersForSaving.joinway"
                type="checkbox" 
                class="btn-check" 
                :id="`saving-${joinway.scode_id}`"
                :value="joinway.scode_id"
                autocomplete="off">
              <label 
                class="btn btn-outline-success" 
                :for="`saving-${joinway.scode_id}`">
                {{ joinway.scode_value }}
              </label>
            </div>
          </div>
        </div>

        <div class="col-lg-3 p-4 m-2 border-bottom">
          <h6 class="mb-3"><strong>이자 유형</strong></h6>
          <input 
            v-model="filtersForSaving.isSimpleInterest"
            type="radio"
            :value="true"
            class="btn-check"
            id="saving-simple-interest-true" 
            autocomplete="off">
          <label 
            class="btn btn-outline-success" 
            for="saving-simple-interest-true">
            단리
          </label>
          <input
            v-model="filtersForSaving.isSimpleInterest"
            type="radio"
            :value="false"
            class="btn-check" 
            id="saving-simple-interest-false"
            autocomplete="off">
          <label 
            class="btn btn-outline-success" 
            for="saving-simple-interest-false">
            복리
          </label>
        </div>

        <div class="col-lg-3 p-4 m-2 border-bottom">
          <h6 class="mb-3"><strong>특수 조건</strong></h6>
          <div id="banks_container">
            <div 
              v-for="traitSet in keywords.trait_set"
              :key="traitSet.scode_id">
              <input
                v-model="filtersForSaving.traitSet"
                type="checkbox" 
                class="btn-check" 
                :id="`saving-${traitSet.scode_id}`"
                :value="traitSet.scode_id"
                autocomplete="off">
              <label 
                class="btn btn-outline-success" 
                :for="`saving-${traitSet.scode_id}`">
                {{ traitSet.scode_value }}
              </label>
            </div>
          </div>
        </div>

        <div class="col-lg-8 p-4 m-2 border-bottom">
          <h6 class="mb-3"><strong>희망 기관</strong> <i class="fa-solid fa-circle-info">
                <span class="tooltiptext">선택하지 않으면 모두 검색됩니다.</span>
              </i>
          </h6>
          <div id="banks_container">
            <div 
              v-for="bank in keywords.bank"
              :key="bank.bank_id" v-show="bank.scode_id === filtersForSaving.bankType[0] || bank.scode_id === filtersForSaving.bankType[1]">
              <div>
                <input 
                  v-model="filtersForSaving.bank"
                  type="checkbox" 
                  class="btn-check"
                  :id="`saving-${bank.bank_id}`" 
                  :value="bank.bank_id"
                  autocomplete="off">
                <label 
                  class="btn btn-outline-success" 
                  :for="`saving-${bank.bank_id}`">
                  {{ bank.short_name }}
                </label>
              </div>
            </div>
          </div>
        </div>



        <div class="col-lg-3 p-4 m-2 border-bottom">
          <h6 class="mb-3"><strong>적립 유형</strong></h6>
          <input 
            v-model="filtersForSaving.isFixed"
            value="true"
            type="radio" 
            class="btn-check"
            id="saving-flat"
            autocomplete="off">
          <label 
            class="btn btn-outline-success" 
            for="saving-flat">
            정액적립
          </label>
          <input
            v-model="filtersForSaving.isFixed"
            value="false"
            type="radio" 
            class="btn-check" 
            id="saving-free" 
            autocomplete="off">
          <label 
            class="btn btn-outline-success" 
            for="saving-free">
            자율적립
          </label>
        </div>

        <div class="col-lg-12 p-4 m-2 border-bottom">
          <h6 class="mb-3"><strong>우대 조건</strong></h6>
          <div id="banks_container">
              <div
                v-for="condition in keywords.conditions"
                :key="condition.scode_id">
                <input
                  v-model="filtersForSaving.conditions"
                  type="checkbox" 
                  class="btn-check" 
                  :id="`saving-${condition.scode_id}`"
                  :value="condition.scode_id"
                  autocomplete="off">
                <label 
                  class="btn btn-outline-success" 
                  :for="`saving-${condition.scode_id}`">
                  {{ condition.scode_value }}
                </label>
              </div>
            </div>
          </div>

        <div id="saving-result-view">
          <button @click="fetchProductsBySavingFilters(filtersForSaving)" class="btn btn-outline-primary btn-sm">결과 보기</button>
        </div>

      </div>
    </div>
  </section>
</template>

<script>
import Navbar from '@/components/Navbar.vue'
import { mapActions, mapGetters } from 'vuex'

export default {
  name: 'ProductRecommendationView',
  components : { Navbar },
  beforeCreate: function() {
    document.body.className = 'menu_body'
  },
  computed: {
    ...mapGetters(['keywords', 'banks']),
  },
  methods: {
    ...mapActions(['fetchKeywords', 'fetchProductsByDepositFilters', 'fetchProductsBySavingFilters']),
    deposit_OnOff: function() {
      if (this.filter_deposit==false) {
      this.filter_deposit = !this.filter_deposit}
      if (this.filter_savings==true) {
      this.filter_savings = !this.filter_savings}
    },
    saving_OnOff: function() {
      if (this.filter_deposit==true) {
      this.filter_deposit = !this.filter_deposit}
      if (this.filter_savings==false) {
      this.filter_savings = !this.filter_savings}
    },
    comma(str) {
        str = String(str);
        return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
    },

    uncomma(str) {
        str = String(str);
        return str.replace(/[^\d]+/g, '');
    } ,
    
    inputNumberFormat(obj) {
        obj.value = comma(uncomma(obj.value));
    },
    
    inputOnlyNumberFormat(obj) {
        obj.value = onlynumber(uncomma(obj.value));
    },
    changeDepositAmount(money) {
      if (this.filtersForDeposit.amount + money >= 0)
        this.filtersForDeposit.amount += money
      else
        alert('금액을 확인해주세요.')
    },
    changeSavingAmount(money) {
      if (this.filtersForSaving.amount + money >= 0)
        this.filtersForSaving.amount += money
      else
        alert('금액을 확인해주세요.')
    },
    
    onlynumber(str) {
	    str = String(str);
	    return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g,'$1');
	},
  },
  data() {
    return {
      filtersForDeposit: {
        amount: null,
        bank: [],
        bankType: [],
        conditions: [],
        isSimpleInterest: null,
        joinway: [],
        period: null,
        traitSet: []
      },
      filtersForSaving: {
        amount: null,
        bank: [],
        bankType: [],
        conditions: [],
        isSimpleInterest: null,
        joinway: [],
        period: null,
        traitSet: [],
        isFixed: null
      },
      filter_savings: false,
      filter_deposit: true,
      isSavingSingle: null,
    }
  },
  created() {
    this.fetchKeywords()
  }
}
</script>

<style scoped>
@import '../../assets/css/product.css';
@import '../../assets/css/common.css';
</style>