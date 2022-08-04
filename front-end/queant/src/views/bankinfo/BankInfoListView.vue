<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">은행 정보</h1>
  </header>
  
  <!-- 은행 목록 -->
  <section class="home">
    <div class="container row home" id="container_banks">
      <div
        v-for="bank in banks" :key="bank.bank_id"
        class="col-6 col-lg-2 p-3 home">
        <router-link :to="{ name: 'bankInfoDetail' , params: { bankId: bank.bank_id }}">
          {{ bank.bank_name }}
        </router-link>
      </div>
    </div>
  </section>
</template>

<script>
import Navbar from '@/components/Navbar.vue'
import { mapActions, mapGetters } from 'vuex'

export default {
  name: 'BankInfoListView',
  components : { Navbar },
  computed: {
    ...mapGetters(['banks'])
  },
  methods: {
    ...mapActions(['fetchBanks'])
  },
  beforeCreate: function() {
      document.body.className = 'home_body'
  },
  created() {
    this.fetchBanks()
  }
}
</script>

<style>
@import '../../assets/css/home.css';
</style>