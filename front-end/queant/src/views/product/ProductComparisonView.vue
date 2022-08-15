<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">상품 비교하기</h1>
  </header>
  <section class="product-detail-box">
    <div v-if="comparisonPortfolio.length === 0" class="cart-none">
      <img src="../../assets/image/물음표개미_none.png" alt="없음" style="width:30%;">
      
      <br><br>
      <h5>가상 포트폴리오가 없습니다.</h5>
      <br><br>
      
      <h6><strong><router-link :to="{name : 'productCart'}">상품 저장소</router-link>에서 포트폴리오를 추가해보세요.</strong></h6>
    </div>

    <div v-else>

        <div class="product-detail d-flex flex-wrap justify-content-center">

        <div class="m-2 p-3 border border-1 d-grid gap-2" v-for="cportfolio in comparisonPortfolio" id="cportfolio" style="height:50vh">
          
          <div class="border p-2" style="height: 50px; background-color: #92ce95; font-family: 'jua';">
              <h5 class="text-center m-0"> 예상 포트폴리오 {{cportfolio.cportfolio_cnt}}</h5>
          </div>

          <br>

          <div v-if="cportfolio.products.length === 0">

            <div>아직 상품이 없습니다.</div>
          </div>

          <div v-else>
            <h2>상품 목록</h2>
            <div v-for="cproduct in cportfolio.products" class="d-flex" style="font-size: 15px;">
              {{cproduct.name}}
                <button style="height:1.2rem; font-size: 5px;" class="d-flex p-0 btn btn-outline btn-sm" @click="popProductFromCPortfolio([cportfolio.cportfolio_cnt, cproduct])">상품삭제</button>
            </div>
            <div >
              예상 이익금 : 
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import Navbar from '@/components/Navbar.vue'
import { mapActions, mapGetters, mapMutations } from 'vuex'

export default {
  name: 'ProductComparisonView',
  components : { Navbar },
  computed: {
    ...mapGetters(['isLoggedIn', 'userInfo', 'comparisonPortfolio']),
    total(number) {
      let sum_value = 0
      for (let product of this.comparisonPortfolio[number].products) {
        sum_value += (product.amount)
      }
    }
  },
  methods: {
    ...mapMutations(['POP_PRODUCT_FROM_CART', 'ADD_COMPARISON_PORTFOLIO', 'POP_CPORTFOLIO', 'POP_PRODUCT_FROM_CPORTFOLIO']),
    ...mapActions(['saveDb', 'getFromDb']),
    popCPortfolioFromCPortfolios(portfolioIdx) {
      this.POP_CPORTFOLIO(portfolioIdx)
    },
    popProductFromCPortfolio(value) {
      this.POP_PRODUCT_FROM_CPORTFOLIO(value)
    },
    addcomparisonportfolio() {
      this.ADD_COMPARISON_PORTFOLIO()
    },
  },
  beforeCreate: function() {
    document.body.className = 'home_body'
  },
  mounted() {
    this.getFromDb()
  },
}
</script>

<style>
@import '../../assets/css/home.css';
</style>