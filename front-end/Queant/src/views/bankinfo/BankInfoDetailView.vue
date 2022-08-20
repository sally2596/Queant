<template>
  <Navbar></Navbar>
  <header id="content-header-section">
    <h1 class="title" id="title" style="font-size:xx-large;">{{bank.bank_name}} 상품 정보</h1>
  </header>
  <section class="section-floater">
    <div id="featured_banks" style="width:100vw;">
      <div class="row">
        <div class="col-lg-4 col-6 my-2" v-for="product in products" :key="product.product_id">
          <div class="card">
            <div class="card-body" v-bind:style="{ background:'url('+product.picture+')'}"
            	style="background-size: 50px;; background-repeat :no-repeat; background-position:90% 90%;">
            	<br>
                <h4 class="card-title" style="font-size: 18px;"><strong>{{product.name}}</strong></h4>
                <br>
                <p>기본 금리 : {{product.base_rate}}%</p>
                <p>최소 가입 기간 : {{product.term_min}}개월</p>
                <p>최대 가입 기간 : {{product.term_max}}개월</p>
                
                <router-link class="banks btn btn-outline-success btn-sm" 
                :to="{ name: 'productDetail', params: { productId: product.product_id }}"
                style="height: 35px; font-size: 14px; padding-top: 7px;">
                  상세 정보
                </router-link>
                
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import Navbar from '@/components/Navbar.vue'

export default {
  name: 'BankInfoDetailView',
  components: { Navbar },
  computed: {
    ...mapGetters(['bank', 'products'])
  },
  methods: {
    ...mapActions(['fetchBank'])
  },
  created() {
    this.fetchBank(this.$route.params.bankId)
  }
}
</script>

<style scoped>
@import '../../assets/css/home.css';

/* 레페리포인트 */
/* @font-face {
    font-family: 'LeferiPoint-BlackA';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2201-2@1.0/LeferiPoint-BlackA.woff') format('woff');
    font-weight: normal;
    font-style: normal;
} */

@font-face {
    font-family: 'LeferiBaseType-RegularA';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2201-2@1.0/LeferiBaseType-RegularA.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

* {
  font-family: LeferiBaseType-RegularA !important;
  /* font-family: LeferiPoint-BlackA; */
}
</style>