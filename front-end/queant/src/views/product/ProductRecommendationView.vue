<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">맞춤 상품 추천</h1>
  </header>

  <section class="product_section">
    <div class="search_filter">
      <div class="filter_button_wrap">
        <button @click="deposit_OnOff" :class="{'active' : filter_deposit==true}" class="filter_button" id="filter_button_a">예금</button>
        <button @click="saving_OnOff" :class="{'active' : filter_savings==true}" class="filter_button" id="filter_button_b">적금</button>
      </div>

      <!-- 예금 -->
      <div v-if="filter_deposit" class="container row" id="filter_deposit">

        <div class="col-lg-2 m-3">
          <h6>기관</h6>
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
        <div class="col-lg-3 m-3">
          <h6>예치 기간 (필수)</h6>
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
        <div class="col-lg-2 m-3">
          <h6>희망 예치금(원) (필수)</h6>
          <input
            v-model="filtersForDeposit.amount"
            type="number"
            name="deposit-money">
        </div>

        <div class="col-lg-5 m-3">
          <h6>가입 방법</h6>
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

        <div class="col-lg-3 m-3">
          <h6>이자 유형</h6>
          <input 
            v-model="filtersForDeposit.isSimpleInterest"
            type="radio"
            :value="true"
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
            :value="false"
            class="btn-check" 
            id="deposit-simple-interest-false"
            autocomplete="off">
          <label 
            class="btn btn-outline-success" 
            for="deposit-simple-interest-false">
            복리
          </label>
        </div>

        <div class="col-lg-3 m-3">
          <h6>특수 조건</h6>
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

        <div class="col-lg-12 m-3">
          <h6>희망 은행</h6>
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
                  autocomplete="off">
                <label 
                  class="btn btn-outline-success" 
                  :for="`deposit-${bank.bank_id}`">
                  {{ bank.short_name }}
                </label>
              </div>
            </div>
          </div>
        </div>
        
        <div class="col-lg-12 m-3">
          <h6>우대 조건</h6>
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
          <button @click="fetchProductsByDepositFilters(filtersForDeposit)" class="btn btn-primary">결과 보기</button>
        </div>
      </div>

      <!-- 적금 -->
      <div v-if="filter_savings" class="container row" id="filter_savings">

        <div class="col-lg-2 m-3">
          <h6>기관</h6>
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
        <div class="col-lg-3 m-3 select">
          <h6>적립 기간 (필수)</h6>
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
        <div class="col-lg-2 m-3">
          <h6>희망 월 적립금(원) (필수)</h6>
          <input
            v-model="filtersForSaving.amount"
            type="number">
        </div>

        <!-- 묶어서 보기 기능 삭제 예정 -->
        <!-- <div class="col-lg-3 m-3">
          <h6>상품 보기</h6>
          <input
            v-model="isSavingSingle"
            type="radio" 
            class="btn-check" 
            id="saving-single"
            value="true"
            autocomplete="off">
          <label 
            class="btn btn-outline-success" 
            for="saving-single">
            하나씩 보기
          </label>
          <input
            v-model="isSavingSingle"
            type="radio" 
            class="btn-check" 
            id="saving-set"
            value="false"
            autocomplete="off">
          <label 
            class="btn btn-outline-success" 
            for="saving-set">
            묶어서 보기
          </label>
        </div> -->

        <div class="col-lg-5 m-3">
          <h6>가입 방법</h6>
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

        <div class="col-lg-3 m-3">
          <h6>이자 유형</h6>
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

        <div class="col-lg-3 m-3">
          <h6>특수 조건</h6>
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

        <div class="col-lg-3 m-3">
          <h6>적립 유형</h6>
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

        <div class="col-lg-12 m-3">
          <h6>희망 은행</h6>
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

        <div class="col-lg-12 m-3">
          <h6>우대 조건</h6>
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
          <button @click="fetchProductsBySavingFilters(filtersForSaving)" class="btn btn-primary">결과 보기</button>
          <!-- <button v-else-if="!isSavingSingle" @click="fetchProductsBySavingSetFilters(filtersForSaving)" class="btn btn-primary">결과 보기</button> -->
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
    ...mapGetters(['keywords', 'banks'])
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
    general_radio_Onoff: function() {
      if (조건) {
        변수명.classList.add('클래스 명');
      } else {
        변수명.classList.remove('클래스 명');
      }
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
        traitSet: [],
        page: 1
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
        isFixed: null,
        page: 1
      },
      filter_savings: false,
      filter_deposit: true,
      isSavingSingle: null
    }
  },
  created() {
    this.fetchKeywords()
  }
}
</script>

<style>
@import '../../assets/css/product.css';
@import '../../assets/css/common.css';
</style>