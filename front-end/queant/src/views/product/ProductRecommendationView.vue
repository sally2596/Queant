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
						<div class="col-lg-3 select">
              <h6>기관</h6>
              <input type="checkbox" class="btn-check" name="deposit_bank" id="deposit_bank_first" autocomplete="off">
                <label class="btn btn-outline-success" for="deposit_bank_first">은행</label>
              <input type="checkbox" class="btn-check" name="deposit_bank" id="deposit_bank_saving" autocomplete="off">
                <label class="btn btn-outline-success" for="deposit_bank_saving">저축은행</label>
              <input type="checkbox" class="btn-check" name="deposit_bank" id="deposit_bank_trust" autocomplete="off">
                <label class="btn btn-outline-success" for="deposit_bank_trust">신협</label>
            </div>
						<div class="col-lg-3 select">
              <h6>예치 기간</h6>
              <input type="radio" class="btn-check" name="deposit_time" id="deposit_time_12" autocomplete="off">
                <label class="btn btn-outline-success" for="deposit_time_12">12개월</label>
              <input type="radio" class="btn-check" name="deposit_time" id="deposit_time_18" autocomplete="off">
                <label class="btn btn-outline-success" for="deposit_time_18">18개월</label>
              <input type="radio" class="btn-check" name="deposit_time" id="deposit_time_24" autocomplete="off">
                <label class="btn btn-outline-success" for="deposit_time_24">24개월</label>
            </div>
						<div class="col-lg-3 select">
              <h6>금리 기준</h6>
              <input type="radio" class="btn-check" name="deposit_" id="deposit_sort_highest" autocomplete="off">
                <label class="btn btn-outline-success" for="deposit_sort_highest">최고 금리</label>
              <input type="radio" class="btn-check" name="deposit_" id="deposit_sort_general" autocomplete="off">
                <label class="btn btn-outline-success" for="deposit_sort_general">기본 금리</label>
            </div>						
						<div class="col-lg-3 select">
              <h6>희망 예치금</h6>
              <input type="number" name="deposit_money" id="deposit_money">
            </div>
						<div class="col-lg-6 select">
              <h6>희망 은행</h6>
              <input type="checkbox" class="btn-check" name="deposit_banks" id="deposit_banks_KB" autocomplete="off">
                <label class="btn btn-outline-success" for="deposit_banks_KB">국민</label>
              <input type="checkbox" class="btn-check" name="deposit_banks" id="deposit_banks_SH" autocomplete="off">
                <label class="btn btn-outline-success" for="deposit_banks_SH">신한</label>
              <input type="checkbox" class="btn-check" name="deposit_banks" id="deposit_banks_WR" autocomplete="off">
                <label class="btn btn-outline-success" for="deposit_banks_WR">우리</label>
              <input type="checkbox" class="btn-check" name="deposit_banks" id="deposit_banks_IBK" autocomplete="off">
                <label class="btn btn-outline-success" for="deposit_banks_IBK">기업</label>
              <input type="checkbox" class="btn-check" name="deposit_banks" id="deposit_banks_KEB" autocomplete="off">
                <label class="btn btn-outline-success" for="deposit_banks_KEB">하나</label>
              <input type="checkbox" class="btn-check" name="deposit_banks" id="deposit_banks_NH" autocomplete="off">
                <label class="btn btn-outline-success" for="deposit_banks_NH">농협</label>
              <input type="checkbox" class="btn-check" name="deposit_banks" id="deposit_banks_KDB" autocomplete="off">
                <label class="btn btn-outline-success" for="deposit_banks_KDB">산업</label>
              <input type="checkbox" class="btn-check" name="deposit_banks" id="deposit_banks_SC" autocomplete="off">
                <label class="btn btn-outline-success" for="deposit_banks_SC">SC제일</label>
              <input type="checkbox" class="btn-check" name="deposit_banks" id="deposit_banks_BNK_PS" autocomplete="off">
                <label class="btn btn-outline-success" for="deposit_banks_BNK_PS">부산</label>
              <input type="checkbox" class="btn-check" name="deposit_banks" id="deposit_banks_CITI" autocomplete="off">
                <label class="btn btn-outline-success" for="deposit_banks_CITI">시티</label>
              <input type="checkbox" class="btn-check" name="deposit_banks" id="deposit_banks_DGB" autocomplete="off">
                <label class="btn btn-outline-success" for="deposit_banks_DGB">대구</label>
              <input type="checkbox" class="btn-check" name="deposit_banks" id="deposit_banks_BNK_KN" autocomplete="off">
                <label class="btn btn-outline-success" for="deposit_banks_BNK_KN">경남</label>
              
            </div>
            <div class="col-lg-6 select">
              <h6>우대 조건</h6>
              <input type="checkbox" class="btn-check" name="deposit_" id="deposit_interest_untact" autocomplete="off">
                <label class="btn btn-outline-success" for="deposit_interest_untact">비대면 가입</label>
              <input type="checkbox" class="btn-check" name="deposit_" id="deposit_interest_app" autocomplete="off">
                <label class="btn btn-outline-success" for="deposit_interest_app">은행 앱 사용</label>
              <input type="checkbox" class="btn-check" name="deposit_" id="deposit_interest_salary" autocomplete="off">
                <label class="btn btn-outline-success" for="deposit_interest_salary">급여 통장</label>
              <input type="checkbox" class="btn-check" name="deposit_" id="deposit_interest_pension" autocomplete="off">
                <label class="btn btn-outline-success" for="deposit_interest_pension">연금</label>
              <input type="checkbox" class="btn-check" name="deposit_" id="deposit_interest_tax" autocomplete="off">
                <label class="btn btn-outline-success" for="deposit_interest_tax">공과금</label>
              <input type="checkbox" class="btn-check" name="deposit_" id="deposit_interest_card" autocomplete="off">
                <label class="btn btn-outline-success" for="deposit_interest_card">카드 발급</label>
              <input type="checkbox" class="btn-check" name="deposit_" id="deposit_interest_first" autocomplete="off">
                <label class="btn btn-outline-success" for="deposit_interest_first">첫 거래</label>
              <input type="checkbox" class="btn-check" name="deposit_" id="deposit_interest_withdrawl" autocomplete="off">
                <label class="btn btn-outline-success" for="deposit_interest_withdrawl">입출금</label>
              <input type="checkbox" class="btn-check" name="deposit_" id="deposit_interest_again" autocomplete="off">
                <label class="btn btn-outline-success" for="deposit_interest_again">재예치</label>
            </div>
            
            <div id="deposit-result-view">
              <button class="btn btn-primary">결과 보기</button>
            </div>
					</div>
					<div v-if="filter_savings" class="container row" id="filter_savings">
						<div class="col-lg-3 select">
              <h6>기관</h6>
              <input type="checkbox" class="btn-check" name="saving_bank" id="saving_bank_first" autocomplete="off">
                <label class="btn btn-outline-success" for="saving_bank_first">은행</label>
              <input type="checkbox" class="btn-check" name="saving_bank" id="saving_bank_saving" autocomplete="off">
                <label class="btn btn-outline-success" for="saving_bank_saving">저축은행</label>
              <input type="checkbox" class="btn-check" name="saving_bank" id="saving_bank_trust" autocomplete="off">
                <label class="btn btn-outline-success" for="saving_bank_trust">신협</label>
            </div>
						<div class="col-lg-3 select">
              <h6>적립 기간</h6>
              <input type="radio" class="btn-check" name="saving_time" id="saving_time_12" autocomplete="off">
                <label class="btn btn-outline-success" for="saving_time_12">12개월</label>
              <input type="radio" class="btn-check" name="saving_time" id="saving_time_18" autocomplete="off">
                <label class="btn btn-outline-success" for="saving_time_18">18개월</label>
              <input type="radio" class="btn-check" name="saving_time" id="saving_time_24" autocomplete="off">
                <label class="btn btn-outline-success" for="saving_time_24">24개월</label>
            </div>
						<div class="col-lg-3 select">
              <h6>금리 기준</h6>
              <input type="radio" class="btn-check" name="saving_" id="saving_sort_highest" autocomplete="off">
                <label class="btn btn-outline-success" for="saving_sort_highest">최고 금리</label>
              <input type="radio" class="btn-check" name="saving_" id="saving_sort_general" autocomplete="off">
                <label class="btn btn-outline-success" for="saving_sort_general">기본 금리</label>
            </div>						
						<div class="col-lg-3 select">
              <h6>희망 월 적립금</h6>
              <input type="number" name="saving_monthly" id="saving_monthly">
            </div>
						<div class="col-lg-6 select">
              <h6>희망 은행</h6>
              <input type="checkbox" class="btn-check" name="saving_banks" id="saving_banks_KB" autocomplete="off">
                <label class="btn btn-outline-success" for="saving_banks_KB">국민</label>
              <input type="checkbox" class="btn-check" name="saving_banks" id="saving_banks_SH" autocomplete="off">
                <label class="btn btn-outline-success" for="saving_banks_SH">신한</label>
              <input type="checkbox" class="btn-check" name="saving_banks" id="saving_banks_WR" autocomplete="off">
                <label class="btn btn-outline-success" for="saving_banks_WR">우리</label>
              <input type="checkbox" class="btn-check" name="saving_banks" id="saving_banks_IBK" autocomplete="off">
                <label class="btn btn-outline-success" for="saving_banks_IBK">기업</label>
              <input type="checkbox" class="btn-check" name="saving_banks" id="saving_banks_KEB" autocomplete="off">
                <label class="btn btn-outline-success" for="saving_banks_KEB">하나</label>
              <input type="checkbox" class="btn-check" name="saving_banks" id="saving_banks_NH" autocomplete="off">
                <label class="btn btn-outline-success" for="saving_banks_NH">농협</label>
              <input type="checkbox" class="btn-check" name="saving_banks" id="saving_banks_KDB" autocomplete="off">
                <label class="btn btn-outline-success" for="saving_banks_KDB">산업</label>
              <input type="checkbox" class="btn-check" name="saving_banks" id="saving_banks_SC" autocomplete="off">
                <label class="btn btn-outline-success" for="saving_banks_SC">SC제일</label>
              <input type="checkbox" class="btn-check" name="saving_banks" id="saving_banks_BNK_PS" autocomplete="off">
                <label class="btn btn-outline-success" for="saving_banks_BNK_PS">부산</label>
              <input type="checkbox" class="btn-check" name="saving_banks" id="saving_banks_CITI" autocomplete="off">
                <label class="btn btn-outline-success" for="saving_banks_CITI">시티</label>
              <input type="checkbox" class="btn-check" name="saving_banks" id="saving_banks_DGB" autocomplete="off">
                <label class="btn btn-outline-success" for="saving_banks_DGB">대구</label>
              <input type="checkbox" class="btn-check" name="saving_banks" id="saving_banks_BNK_KN" autocomplete="off">
                <label class="btn btn-outline-success" for="saving_banks_BNK_KN">경남</label>
              
            </div>
            <div class="col-lg-6 select">
              <h6>우대 조건</h6>
              <input type="checkbox" class="btn-check" name="saving_" id="saving_interest_untact" autocomplete="off">
                <label class="btn btn-outline-success" for="saving_interest_untact">비대면 가입</label>
              <input type="checkbox" class="btn-check" name="saving_" id="saving_interest_app" autocomplete="off">
                <label class="btn btn-outline-success" for="saving_interest_app">은행 앱 사용</label>
              <input type="checkbox" class="btn-check" name="saving_" id="saving_interest_salary" autocomplete="off">
                <label class="btn btn-outline-success" for="saving_interest_salary">급여 통장</label>
              <input type="checkbox" class="btn-check" name="saving_" id="saving_interest_pension" autocomplete="off">
                <label class="btn btn-outline-success" for="saving_interest_pension">연금</label>
              <input type="checkbox" class="btn-check" name="saving_" id="saving_interest_tax" autocomplete="off">
                <label class="btn btn-outline-success" for="saving_interest_tax">공과금</label>
              <input type="checkbox" class="btn-check" name="saving_" id="saving_interest_card" autocomplete="off">
                <label class="btn btn-outline-success" for="saving_interest_card">카드 발급</label>
              <input type="checkbox" class="btn-check" name="saving_" id="saving_interest_first" autocomplete="off">
                <label class="btn btn-outline-success" for="saving_interest_first">첫 거래</label>
              <input type="checkbox" class="btn-check" name="saving_" id="saving_interest_withdrawl" autocomplete="off">
                <label class="btn btn-outline-success" for="saving_interest_withdrawl">입출금</label>
              <input type="checkbox" class="btn-check" name="saving_" id="saving_interest_again" autocomplete="off">
                <label class="btn btn-outline-success" for="saving_interest_again">재적립</label>
            </div>
            <div class="col-lg-6 select">
              <h6>상품 보기</h6>
              <input type="radio" class="btn-check" name="saving_" id="saving_show_one" autocomplete="off">
                <label class="btn btn-outline-success" for="saving_show_one">상품 하나씩 보기</label>
              <input type="radio" class="btn-check" name="saving_" id="saving_show_group" autocomplete="off" checked>
                <label class="btn btn-outline-success" for="saving_show_group">상품 묶어서 보기</label>
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
export default {
  name: 'ProductRecommendationView',
  
  components : {
    Navbar : Navbar
    
  },
  
  beforeCreate: function() {
        document.body.className = 'menu_body';
  },
  methods: {
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
      filter_savings:false,
      filter_deposit:true
    }
  }
}
</script>

<style>
@import '../../assets/css/product.css';
@import '../../assets/css/common.css';
</style>