<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">상품 비교하기</h1>
  </header>
  <section class="product-detail-box">
    <div v-if="comparisonPortfolio.length === 0" class="cart-none" style="margin-left: 10%">
      <img src="../../assets/image/물음표개미_none.png" alt="없음">
      
      <br><br>
      <h5>가상 포트폴리오가 없습니다.</h5>
      <br><br>
      
      <button class="btn btn-outline-success" @click="addcomparisonportfolio(userInfo)">가상 포트폴리오 추가하기</button>
    </div>

    <div>

        <div v-if="comparisonPortfolio.length <= 3">

          <button class="btn btn-outline-success btn-sm" @click="addcomparisonportfolio(userInfo)"><i class="fa-solid fa-circle-plus">포트폴리오 추가하기</i></button>
          <button  class="btn btn-outline-success btn-sm" v-show="isLoggedIn" @click="saveToDb()">최종 저장</button>
        </div>

        <div class="product-detail d-flex flex-wrap justify-content-center">

        <div class="m-2 p-5 border border-1 d-grid gap-2" v-for="cportfolio in comparisonPortfolio" id="cportfolio">

          <div class="border p-2" style="background-color: #92ce95; font-family: 'jua';">
              <h5 class="text-center m-0"> 예상 포트폴리오 {{cportfolio.cportfolio_cnt}}</h5>
          </div>

          <br>

          <div v-if="cportfolio.products.length === 0">

            <div>아직 상품이 없습니다.</div>

            <br><br><br><br>

          </div>

          <div v-else v-for="cproduct in cportfolio.products" class="d-flex" style="font-size: 15px;">
                {{cproduct.name}}
                <button style="height:1.2rem; font-size: 5px;" class="d-flex p-0 btn btn-outline btn-sm" @click="popProductFromCPortfolio([cportfolio.cportfolio_cnt, cproduct])">상품삭제</button>
          </div>
          
          <button class="btn btn-outline-primary" type="button"><router-link :to="{ name: 'productCart' }"><i class="fa-solid fa-circle-plus"> 상품 추가하기</i></router-link></button>
          <button class="btn btn-danger" type="button" @click="popCPortfolioFromCPortfolios(cportfolio.cportfolio_cnt)">포트폴리오 삭제</button>
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
    ...mapGetters(['isLoggedIn', 'userInfo', 'comparisonPortfolio'])
  },
  methods: {
    ...mapMutations(['POP_PRODUCT_FROM_CART', 'ADD_COMPARISON_PORTFOLIO', 'POP_CPORTFOLIO', 'POP_PRODUCT_FROM_CPORTFOLIO']),
    ...mapActions(['saveDb']),
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
  }
}
</script>

<style>
@import '../../assets/css/home.css';
</style>