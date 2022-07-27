<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">내 포트폴리오</h1>
  </header>
  <div class="portfolio-none">
    <br><br><br><br><br>
      <h1>포트폴리오가 없으시네요!</h1>
      <br><br><br><br><br><br>
        <button  @click="">
          <strong>직접 포트폴리오 만들기</strong>
        </button>
        <button class="col-lg-1 offset-lg-5">
          <strong>상품 추가하기</strong>
        </button>
  </div>
  <div class="portfolio">
    
  </div>
</template>

<script>
import Navbar from '@/components/Navbar.vue';
export default {
  name: "PortfolioView",
  components: { Navbar },
  beforeCreate: function() {
    document.body.className = 'home_body';
  },
  
}
</script>

<style>
@import '../../assets/css/home.css';
</style>