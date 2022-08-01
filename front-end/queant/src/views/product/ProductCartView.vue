<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">상품 저장소</h1>
  </header>
  <section class="product_section">
    <div class="container row cart-none">
      <router-link to="/product" class="to_product"><i class="fa-solid fa-circle-question fa-5x"></i></router-link>
      <h5>마음에 두고 계신 상품이 없어요.</h5>
    </div>
  </section>
</template>

<script>
import Navbar from '@/components/Navbar.vue'
export default {
  name: 'ProductCartView',
  components : {
    Navbar : Navbar
  },
    beforeCreate: function() {
        document.body.className = 'home_body';
    },
}
</script>

<style>
@import '../../assets/css/home.css';
@import '../../assets/css/product.css';
</style>