<template>
  <Navbar/>
  <header id="title-div" class="section-floater">
    <h1 type="button" class="title" id="title" style="display:inline-block; font-size: 2rem;" @click="changeBanks(1)">은행</h1>
    <h1 class="title" id="title" style="display:inline-block; font-size: 2rem;">I</h1>
    <h1 type="button" class="title" id="title" style="display:inline-block; font-size: 2rem;" @click="changeBanks(2)">저축은행</h1>
    <h1 class="title" id="title" style="display:inline-block; font-size: 2rem;">I</h1>
    <h1 type="button" class="title" id="title" style="display:inline-block; font-size: 2rem;" @click="changeBanks(3)">기타 금융권</h1>
  </header>
  
  <!-- 제 1금융 목록 -->
  <section v-if="showBanks===1" class="section-floater" id="content-header-section">
    <div id="featured_banks">
      <div class="row">
        <div
          class="col-lg-4 col-6 my-2" 
          v-for="bank in theFirstBanks"
          :key="bank.bank_id" 
          data-aos="fade-up">
          <div class="card">
            <div 
              class="card-body" 
              id="banks_card" 
              v-bind:style="{ background:'url('+bank.picture+')'}"
              style="background-size: 30%; 
              background-repeat :no-repeat; 
              background-position:100% 100%;">
              <h5 class="card-title">{{bank.short_name}}</h5>
              <a :href="bank.homepage" style="text-decoration:none;">홈페이지</a>
              <h6>전화번호 : {{bank.tel}}</h6>
              <router-link :to="{ name: 'bankInfoDetail', params: { bankId: bank.bank_id } }">
              <button 
                class="banks btn btn-outline-success btn-sm"
                style="font-size: 14px;
                height: 35px;">상품 보러가기</button>
              </router-link>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>

  <!-- 제 2금융 목록 -->
  <section v-if="showBanks===2" class="section-floater" id="content-header-section">
    <div id="featured_banks">
      <div class="row">
        <div 
          class="col-lg-4 col-6 my-2"
          v-for="bank in theSecondBanks" 
          :key="bank.bank_id"
          data-aos="fade-up">
          <div class="card">
            <div class="card-body" id="banks_card"  v-bind:style="{ background:'url('+bank.picture+')'}"
             style="background-size: 30%; background-repeat :no-repeat; background-position:100% 100%;">
                <h5 class="card-title">{{bank.short_name}}</h5>
                <a :href="bank.homepage" style="text-decoration:none;">홈페이지</a>
                <h6>전화번호 : {{bank.tel}}</h6>
                <router-link :to="{ name: 'bankInfoDetail', params: { bankId: bank.bank_id } }">
              	<button 
									class="banks btn btn-outline-success btn-sm"
									style="font-size: 14px;
									height: 35px;">상품 보러가기</button>
								</router-link>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>

  <!-- 기타 금융 목록 -->
  <section v-if="showBanks===3" class="section-floater" id="content-header-section">
    <div id="featured_banks_3">
      <div class="row">
        <div 
          class="col-lg-4 col-6 my-2" 
          v-for="bank in theThirdBanks" 
          :key="bank.bank_id"
          data-aos="fade-up">
          <div class="card">
            <div class="card-body" id="banks_card"  v-bind:style="{ background:'url('+bank.picture+')'}"
            	style="background-size: 30%; background-repeat :no-repeat; background-position:100% 100%;">
              <h5 class="card-title">{{bank.short_name}}</h5>
              <a :href="bank.homepage" style="text-decoration:none;">홈페이지</a>
              <h6>전화번호 : {{bank.tel}}</h6>
              <router-link :to="{ name: 'bankInfoDetail', params: { bankId: bank.bank_id }}">
              <button class="banks btn btn-outline-success btn-sm" style="font-size: 14px; height: 35px;">상품 보러가기</button>
              </router-link>
            </div>
          </div>
        </div>
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
    ...mapGetters(['theFirstBanks', 'theSecondBanks', 'theThirdBanks']),
  },
  methods: {
    ...mapActions(['fetchBanks']),
    changeBanks(num) {
      this.showBanks = num
    }
  },
  data() {
    return {
      showBanks: 1
    }
  },
  beforeCreate: function() {
      document.body.className = 'bank_body'
  },
  created() {
    this.fetchBanks()
  }
}
</script>

<style scoped>
@import '../../assets/css/home.css';
@import '../../assets/css/bank.css';
@import '../../assets/css/common.css';

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