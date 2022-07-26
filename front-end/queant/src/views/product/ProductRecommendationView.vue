<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">맞춤 상품 추천</h1>
  </header>
  <section>
    <div class="search_fi lter">
					<div class="filter_button_wrap">
						<button @click="deposit_OnOff" :class="{'active' : filter_deposit==true}" class="filter_button" id="filter_button_a">예금</button>
						<button @click="saving_OnOff" :class="{'active' : filter_savings==true}" class="filter_button" id="filter_button_b">적금</button>
					</div>
					<div v-if="filter_deposit" class="container row" id="filter_deposit">
						<div class="col-lg-3 select">
              <h6>기관</h6>
              <button class="btn">은행</button>
              <button class="btn">저축은행</button>
              <button class="btn">은행</button>
            </div>
						<div class="col-lg-3 select">
              <h6>예치 기간</h6>
              <button class="btn">12개월</button>
              <button class="btn">18개월</button>
              <button class="btn">24개월</button>
            </div>
						<div class="col-lg-3 select">
              <h6>금리 기준</h6>
              <button class="btn">최고 금리</button>
              <button class="btn">기본 금리</button>
            </div>						
						<div class="col-lg-3 select">
              <h6>희망 예치금</h6>
              <input type="number" name="" id="">
            </div>
						<div class="col-lg-6 select">
              <h6>희망 은행</h6>
              <button class="btn">국민</button>
              <button class="btn">신한</button>
              <button class="btn">우리</button>
              <button class="btn">기업</button>
              <button class="btn">하나</button>
              <button class="btn">농협</button>
              <button class="btn">산업</button>
              <button class="btn">SC제일</button>
              <button class="btn">부산</button>
              <button class="btn">시티</button>
              <button class="btn">대구</button>
              <button class="btn">경남</button>
              
            </div>
            <div class="col-lg-6 select">
              <h6>우대 조건</h6>
              <button class="btn">비대면 가입</button>
              <button class="btn">은행 앱 사용</button>
              <button class="btn">급여</button>
              <button class="btn">연금</button>
              <button class="btn">공과금</button>
              <button class="btn">카드</button>
              <button class="btn">첫 거래</button>
              <button class="btn">입출금통장</button>
              <button class="btn">재예치</button>
            </div>
					</div>
					<div v-if="filter_savings" class="container row" id="filter_savings">
						<div class="col-lg-3 select">
              <h6>기관</h6>
              <button class="btn">은행</button>
              <button class="btn">저축은행</button>
              <button class="btn">신협</button>
            </div>
						<div class="col-lg-3 select">
              <h6>적립 기간</h6>
              <button class="btn">12개월</button>
              <button class="btn">18개월</button>
              <button class="btn">24개월</button>
            </div>
						<div class="col-lg-3 select">
              <h6>금리 기준</h6>
              <button class="btn">최고 금리</button>
              <button class="btn">기본 금리</button>
            </div>						
						<div class="col-lg-3 select">
              <h6>희망 월 적립금</h6>
              <input type="number" name="" id="">
            </div>
						<div class="col-lg-6 select">
              <h6>희망 은행</h6>
              <button class="btn">국민</button>
              <button class="btn">신한</button>
              <button class="btn">우리</button>
              <button class="btn">기업</button>
              <button class="btn">하나</button>
              <button class="btn">농협</button>
              <button class="btn">산업</button>
              <button class="btn">SC제일</button>
              <button class="btn">부산</button>
              <button class="btn">시티</button>
              <button class="btn">대구</button>
              <button class="btn">경남</button>
              
            </div>
            <div class="col-lg-5 select">
              <h6>우대 조건</h6>
              <button class="btn btn-ouline-primary">비대면 가입</button>
              <button class="btn">은행 앱 사용</button>
              <button class="btn">급여</button>
              <button class="btn">연금</button>
              <button class="btn">공과금</button>
              <button class="btn">카드</button>
              <button class="btn">첫 거래</button>
              <button class="btn">입출금통장</button>
              <button class="btn">재예치</button>
            </div>
            <div class="col-lg-6 select">
              <h6>상품 보기</h6>
              <button class="btn">상품 하나씩 보기</button>
              <button class="btn">상품 묶어서 보기</button>
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
  }
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