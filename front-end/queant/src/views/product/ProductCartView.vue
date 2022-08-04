<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">상품 저장소</h1>
  </header>
  <section class="product_section">
    <div class="cart-none">
        <img src="../../assets/image/물음표개미_none.png" alt="없음" style="width: 300px; height:280px;">
        <h5>담긴 상품이 없습니다.</h5>
        <br><br><br>
        <button class="btn btn-outline-success">상품 추천받기</button>
    </div>
  </section>
</template>

<script>
import Navbar from '@/components/Navbar.vue'

export default {
  name: 'ProductCartView',
  components : { Navbar },
  beforeCreate: function() {
    document.body.className = 'home_body'
  },
}
</script>

<style>
@import '../../assets/css/home.css';
@import '../../assets/css/product.css';
</style>