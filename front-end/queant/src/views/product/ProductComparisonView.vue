<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">상품 비교하기</h1>
  </header>
  <section class="product-detail-box">
    <!-- <div v-if="comparisonportfolios.length === 0" class="cart-none">
      <img src="../../assets/image/물음표개미_none.png" alt="없음">
      
      <br><br>
      <h5>가상 포트폴리오가 없습니다.</h5>
      <br><br>
      
      <button class="btn btn-outline-success" @click="addcomparisonportfolio(userInfo)">가상 포트폴리오 추가하기</button> 
    </div> -->

    <div>

        <div v-if="comparisonportfolios.length <= 3">

          <button class="btn btn-outline-success btn-sm" @click="addcomparisonportfolio(userInfo)"><i class="fa-solid fa-circle-plus">포트폴리오 추가하기</i></button>
        </div>

        <div class="product-detail d-flex flex-wrap justify-content-center">

        <div class="m-2 p-5 border border-1 d-grid gap-2" v-for="cportfolio in comparisonportfolios" id="cportfolio">

          <div class="border p-2" style="background-color: #92ce95; font-family: 'jua';">
              <h5 class="text-center m-0"> 예상 포트폴리오 {{cportfolio.cportfolio_cnt}}</h5>
          </div>

          <br>

          <div v-if="cportfolio.products.length === 0">

            <div>아직 상품이 없습니다.</div>

            <br><br><br><br>

          </div>

          <div v-else>

              <div v-for="cproduct in cportfolio.products" class="d-flex" style="font-size: 15px;">
                {{cproduct.product.name}}
                <button style="height:1.2rem; font-size: 5px;" class="d-flex p-0 btn btn-outline btn-sm" @click="popProductFromCPortfolio([cportfolio.cportfolio_cnt, cproduct])">상품삭제</button>
              </div>

            <br><br><br>
            
            <div>예상 이익금</div>
          </div>
          
            <button class="btn btn-outline-primary" type="button"><router-link :to="{ name: 'productCart' }"><i class="fa-solid fa-circle-plus"> 상품 추가하기</i></router-link></button>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import Navbar from '@/components/Navbar.vue'
import { mapGetters, mapMutations } from 'vuex'

export default {
  name: 'ProductComparisonView',
  components : { Navbar },
  computed: {
    ...mapGetters(['userInfo', 'comparisonportfolios'])
  },
  methods: {
    ...mapMutations(['POP_CPORTFOLIO_FROM_CPORTFOLIOS', 'POP_PRODUCT_FROM_CPORTFOLIO', 'PUSH_CPORTFOLIO_TO_COMPARISONPORTFOLIOS']),
    popCPortfolioFromCPortfolios(portfolioIdx) {
      this.POP_CPORTFOLIO_FROM_CPORTFOLIOS(portfolioIdx)
    },
    popProductFromCPortfolio(value) {
      this.POP_PRODUCT_FROM_CPORTFOLIO(value)
    },
    addcomparisonportfolio(value) {
      this.PUSH_CPORTFOLIO_TO_COMPARISONPORTFOLIOS(value)
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