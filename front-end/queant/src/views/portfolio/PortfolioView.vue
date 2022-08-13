<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">내 포트폴리오</h1>
  </header>

  <div>
    <!-- 포트폴리오 없을 때 -->
    <div v-if="portfolio?.length === 0" class="portfolio-none">
      <img src="../../assets/image/물음표개미_none.png" alt="" style="width: 300px; height:280px;">
      <br><br>
      <h5>포트폴리오에 상품이 없습니다.</h5>
      <br><br>
      <router-link :to="{ name : 'portfolioEdit' }"><button class="btn btn-outline-success">포트폴리오에 상품 추가하기</button></router-link>
      
    </div>

    <!-- 포트폴리오 있을 때 -->
    <div v-else class="portfolio">
      <router-link :to="{ name: 'portfolioEdit' }"><button class="btn btn-outline-success">포트폴리오 관리하기</button></router-link><br>
      
      <!-- 포트폴리오 뭉텅이로 볼 때 -->
      <!-- {{ portfolio }} -->

      <!-- 포트폴리오의 상품을 개별로 볼 때 -->
      <div
        v-for="product in portfolio"
        :key="product">
        {{ product }}
        <hr>
      </div>

      <div
        v-for="customProduct in customProducts"
        :key="customProduct">
        {{ customProduct }}
      </div>
    </div>

  </div>
</template>

<script>
import Navbar from '@/components/Navbar.vue'
import { mapActions, mapGetters } from 'vuex'

export default {
  name: 'PortfolioView',
  components: { Navbar },
  computed: {
    ...mapGetters(['portfolio', 'customProducts'])
  },
  methods: {
    ...mapActions(['fetchMyPortfolio'])
  },
  beforeCreate: function() {
    document.body.className = 'home_body'
  },
  created() {
    this.fetchMyPortfolio()
    // this.fetchCustomList()
  }
}
</script>

<style>
@import '../../assets/css/home.css';
</style>