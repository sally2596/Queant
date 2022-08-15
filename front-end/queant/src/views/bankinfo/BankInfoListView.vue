<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">은행 정보</h1>
  </header>
  
  <!-- 은행 목록 -->
  <div
    class="d-flex justify-content-center">
    <button class="btn btn-outline-success" @click="changeBanks(1)">은행</button>
    <button class="btn btn-outline-success" @click="changeBanks(2)">저축은행</button>
    <button class="btn btn-outline-success" @click="changeBanks(3)">기타 금융권</button>
  </div>
  
  <!-- 제 1금융 목록 -->
  <section v-if="showBanks===1" class="section-floater">
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
              <a :href="bank.homepage">홈페이지</a>
              <p>전화번호 : {{bank.tel}}</p>
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
  <section v-if="showBanks===2" class="section-floater">
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
                <a :href="bank.homepage">홈페이지</a>
                <p>전화번호 : {{bank.tel}}</p>
                <router-link class="banks btn btn-outline-success btn-sm"
                :to="{ name: 'bankInfoDetail' , params: { bankId: bank.bank_id }}"
                style="height: 35px; text-align: center;">
                  <p style="font-size: 14px;">상품 보러가기</p>
                </router-link>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>

  <!-- 기타 금융 목록 -->
  <section v-if="showBanks===3" class="section-floater">
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
              <a :href="bank.homepage">홈페이지</a>
              <p>전화번호 : {{bank.tel}}</p>
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

<style>
@import '../../assets/css/home.css';
@import '../../assets/css/bank.css';
@import '../../assets/css/common.css';
</style>