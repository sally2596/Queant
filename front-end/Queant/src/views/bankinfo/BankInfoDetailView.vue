<template>
  <Navbar></Navbar>
  <header id="title-div">
    <h1 class="title" id="title">{{bank.bank_name}} 상품 정보</h1>
  </header>
  <section class="section-floater">
    <div id="featured_banks" style="width:100vw;">
      <div class="row">
        <div class="col-lg-4 col-6 my-2" v-for="product in products" :key="product.product_id">
          <div class="card">
            <div class="card-body">
                <h5 class="card-title" style="font-size: 18px;">{{product.name}}</h5>
                <br>
                <p>최소 가입 연령 : {{product.age_min}}세</p>
                <p>최소 가입 기간 : {{product.term_min}}개월</p>
                <p>최대 가입 기간 : {{product.term_max}}개월</p>
                <router-link class="banks btn btn-outline-success btn-sm" 
                :to="{ name: 'productDetail', params: { productId: product.product_id }}"
                style="height: 35px;">
                  <p style="font-size: 14px;">상세 정보</p>
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

<style>
@import '../../assets/css/home.css';
</style>