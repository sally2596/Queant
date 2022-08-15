<template>
  <Navbar/>
  <header id="title-div">
    <!-- <h1 class="title" id="title">은행 정보</h1> -->
  </header>
  
  <!-- 은행 목록 -->
    <!-- 제 1금융 목록 -->
  <section>
  <h1 class="bank-title">은행</h1>
  <section class="section-floater">
    <div id="featured_banks">
      <div class="row">
        <div class="col-lg-4 col-6 my-2" v-for="bank in banks" :key="bank.bank_id" v-show="bank.scode_id=='C001'" data-aos="fade-up">
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
    <br><br><br>
    <!-- <br><br><br><br> -->
    <!-- 제 2금융 목록 -->
  
  <h1 class="bank-title">저축은행</h1>
  <section class="section-floater">
    <div id="featured_banks">
      <div class="row">
        <div class="col-lg-4 col-6 my-2" v-for="bank in banks" :key="bank.bank_id" v-show="bank.scode_id=='C002'" data-aos="fade-up">
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

  <h1 class="bank-title">기타 기관</h1>
  <section class="section-floater">
    <div id="featured_banks_3">
      <div class="row">
        <div class="col-lg-4 col-6 my-2" v-for="bank in banks" :key="bank.bank_id" v-show="bank.scode_id=='C003'" data-aos="fade-up">
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
  </section>
</template>

<script>
import Navbar from '@/components/Navbar.vue'
import { mapActions, mapGetters } from 'vuex'

export default {
  name: 'BankInfoListView',
  components : { Navbar },
  computed: {
    ...mapGetters(['banks']),
  },
  methods: {
    ...mapActions(['fetchBanks']),
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