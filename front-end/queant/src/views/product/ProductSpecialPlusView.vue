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
    beforeCreate: function() {
        document.body.className = 'home_body';
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
    },
    general_radio_Onoff: function() {
      if (조건) {
        변수명.classList.add('클래스 명');
      } else {
        변수명.classList.remove('클래스 명');
      }
    },
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