<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">은행 정보</h1>
  </header>
  
  <!-- 은행 목록 -->
  <div
    v-for="bank in banks"
    :key="bank.bank_id">
    <router-link :to="{ name: 'bankInfoDetail' , params: { bankId: bank.bank_id }}">
      {{ bank }}
      <hr>
    </router-link>
  </div>
  
</template>

<script>
import Navbar from '@/components/Navbar.vue'
import { mapActions, mapGetters } from 'vuex'

export default {
  name: 'BankInfoListView',
  components : {
    Navbar : Navbar
  },
  computed: {
    ...mapGetters(['banks'])
  },
  methods: {
    ...mapActions(['fetchBanks'])
  },
  beforeCreate: function() {
      document.body.className = 'home_body';
  },
  mounted() {
    this.fetchBanks()
  }
}
</script>

<style>
@import '../../assets/css/home.css';
</style>