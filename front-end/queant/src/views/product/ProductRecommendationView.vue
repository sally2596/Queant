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

      <div v-if="filter_deposit" class="container row" id="filter_deposit">

        <div class="col-lg-4 m-3">
          <h6>기관</h6>
          <div id="banks_container">
            <div
              v-for="bankType in keywords.bank_type"
              :key="bankType.scode_id">
              <input
                v-model="bankTypeFromDeposit"
                type="checkbox" 
                class="btn-check" 
                name="deposit_bank"
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

        <div class="col-lg-3 m-3">
          <h6>예치 기간(개월)</h6>
          <input type="number">
        </div>

        <div class="col-lg-3 m-3">
          <h6>희망 예치금(원)</h6>
          <input type="number" name="deposit_money" id="deposit_money">
        </div>

        <div class="col-lg-5 m-3">
          <h6>가입 방법</h6>
          <div id="banks_container">
            <div
              v-for="joinway in keywords.joinway"
              :key="joinway.scode_id">
              <input 
                type="checkbox" 
                class="btn-check" 
                name="deposit_banks" 
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
          <h6>특수 조건</h6>
          <div id="banks_container">
            <div
            v-for="traitSet in keywords.trait_set"
            :key="traitSet.scode_id">
              <input 
                type="checkbox" 
                class="btn-check" 
                name="deposit_banks" 
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

        <div class="m-3">
          <h6>희망 은행</h6>
          <div id="banks_container">
            <div
              v-for="bank in keywords.bank"
              :key="bank.bank_id" v-show="bank.scode_id === bankTypeFromDeposit[0] || bank.scode_id === bankTypeFromDeposit[1]">
              <div>
                <input 
                  type="checkbox" 
                  class="btn-check" 
                  name="deposit_banks" 
                  :id="`deposit-${bank.bank_id}`" 
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
              type="checkbox" 
              class="btn-check" 
              name="deposit_" 
              :id="`deposit-${condition.scode_id}`" 
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
          <button class="btn btn-primary">결과 보기</button>
        </div>
      </div>

      <div v-if="filter_savings" class="container row" id="filter_savings">

        <div class="col-lg-4 m-3">
          <h6>기관</h6>
          <div id="banks_container">
            <div
              v-for="bankType in keywords.bank_type"
              :key="bankType.scode_id">
              <input 
                v-model="bankTypeFromSaving"
                :value="bankType.scode_id"
                type="checkbox" 
                class="btn-check" 
                name="saving_bank" 
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
        
        <div class="col-lg-3 m-3">
          <h6>적립 기간(개월)</h6>
          <input type="number">
        </div>

        <div class="col-lg-3 m-3">
          <h6>희망 월 적립금(원)</h6>
          <input 
            type="number" 
            name="saving_monthly" 
            id="saving_monthly">
        </div>

        <div class="col-lg-3 m-3">
          <h6>상품 보기</h6>
          <input 
            type="radio" 
            class="btn-check" 
            name="saving_" 
            id="saving_show_one" 
            autocomplete="off">
          <label 
            class="btn btn-outline-success" 
            for="saving_show_one">
            하나씩 보기
          </label>
          <input 
            type="radio" 
            class="btn-check" 
            name="saving_" 
            id="saving_show_group" 
            autocomplete="off" 
            checked>
          <label 
            class="btn btn-outline-success" 
            for="saving_show_group">
            묶어서 보기
          </label>
        </div>

        <div class="col-lg-5 m-3">
          <h6>가입 방법</h6>
          <div id="banks_container">
            <div 
              v-for="joinway in keywords.joinway"
              :key="joinway.scode_id">
              <input 
                type="checkbox" 
                class="btn-check" 
                name="saving_banks" 
                :id="`saving-${joinway.scode_id}`" 
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
          <h6>특수 조건</h6>
          <div id="banks_container">
            <div 
              v-for="traitSet in keywords.trait_set"
              :key="traitSet.scode_id">
              <input 
                type="checkbox" 
                class="btn-check" 
                name="saving_banks" 
                :id="`saving-${traitSet.scode_id}`" 
                autocomplete="off">
              <label 
                class="btn btn-outline-success" 
                :for="`saving-${traitSet.scode_id}`">
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
              :key="bank.bank_id" v-show="bank.scode_id === bankTypeFromSaving[0] || bank.scode_id === bankTypeFromSaving[1]">
              <div>
                <input 
                  type="checkbox" 
                  class="btn-check"
                  :value="bank.scode_id"
                  name="saving_banks" 
                  :id="`saving-${bank.bank_id}`" 
                  autocomplete="off">
                <label 
                  class="btn btn-outline-success" 
                  :for="`saving-${bank.bank_id}`">
                  {{ bank.bank_name }}
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
                  type="checkbox" 
                  class="btn-check" 
                  name="saving_" 
                  :id="`saving-${condition.scode_id}`" 
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
          <button class="btn btn-primary">결과 보기</button>
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
    ...mapActions(['fetchKeywords']),
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
      filter_savings: false,
      filter_deposit: true,
      bankTypeFromDeposit: [],
      bankTypeFromSaving: []
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