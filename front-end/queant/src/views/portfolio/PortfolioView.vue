<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">내 포트폴리오</h1>
  </header>

  <!-- 포트폴리오 없을 때 -->
  <div v-if="portfolio.length === 0" class="portfolio-none">
    <img src="../../assets/image/물음표개미_none.png" alt="" style="width: 300px; height:280px;">
    <br><br>
    <h5>포트폴리오가 없습니다.</h5>
    <br><br>
    <router-link :to="{ name : 'portfolioAdd' }"><button class="btn btn-outline-success">포트폴리오 만들기</button></router-link>
  </div>

  <!-- 포트폴리오 있을 때 -->
  <div v-else class="portfolio">
    {{ portfolio }}
  </div>
</template>

<script>
import Navbar from '@/components/Navbar.vue'
import { mapActions, mapGetters } from 'vuex'

export default {
  name: 'PortfolioView',
  components: { Navbar },
  computed: {
    ...mapGetters(['portfolio'])
  },
  methods: {
    ...mapActions(['fetchPortfolio'])
  },
  beforeCreate: function() {
    document.body.className = 'home_body'
  },
  created() {
    this.fetchPortfolio()
  }
}
</script>

<style>
@import '../../assets/css/home.css';
</style>