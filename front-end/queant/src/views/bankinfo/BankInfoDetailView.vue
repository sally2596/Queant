<template>
  <Navbar></Navbar>
  <header id="title-div">
    <h1 class="title" id="title">{{bank.bank_name}} 상품 정보</h1>
  </header>
  <section class="home">
    <div>
      <!-- 은행 상품 목록 (product_list) -->
      <div 
        v-for="product in products"
        :key="product.product_id">
        {{ product.name }}
        <hr>
      </div>
    </div>
  </section>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import Navbar from '@/components/Navbar.vue'

export default {
    name: "BankInfoDetailView",
    computed: {
        ...mapGetters(["bank", "products"])
    },
    methods: {
        ...mapActions(["fetchBank"])
    },
    created() {
        this.fetchBank(this.$route.params.bankId);
    },
    components: { Navbar }
}
</script>

<style>

</style>