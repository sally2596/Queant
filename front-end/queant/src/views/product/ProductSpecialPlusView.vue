<template>
    <Navbar/>
    <header id="title-div">
      <h1 class="title" id="title">특판 상품 정보</h1>
    </header>
    <section>
      <div></div>
    </section>
</template>

<script>
import Navbar from '@/components/Navbar.vue'

export default {
  name: 'ProductSpecialPlusView',
  components : { Navbar },
  beforeCreate: function() {
    document.body.className = 'home_body'
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
@import '../../assets/css/home.css';
@import '../../assets/css/product.css';
</style>