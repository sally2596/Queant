<template>
    <Navbar/>
    <header id="title-div">
      <h1 class="title" id="title">특판 상품 제보</h1>
    </header>
    <section>
      <div>
        <form>
          <br>
          은행이름 <input type="text" v-model="product.bankName"><br>
          상품명 <input type="text" v-model="product.productName"><br>
          관련링크 <input type="text" v-model="product.referenceData"><br>
          <!-- 예금/적금 <input type="text" v-model="product.is_deposit"><br> -->
          <button type="button" class="btn btn-sm btn-outline-secondary" @click="regist()">제보하기</button>
	      </form>
      </div>
    </section>
</template>

<script>
import Navbar from '@/components/Navbar.vue'
import { mapActions, mapGetters } from 'vuex'

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
  computed: {
    ...mapGetters(['userInfo']),
  },
  data() {
    return {
      filter_savings:false,
      filter_deposit:true,
      product : {
        memberEmail : '',
        bankName : '',
        productName : '',
        referenceData : '',
        //isDeposit : '',
      },
    }
  },
  methods: {
    ...mapActions(['registReport']),
    regist() {
      this.product.memberEmail = this.userInfo.email;
      this.registReport(this.product);	
			this.$router.go();		
    }
  }
}
</script>

<style>
@import '../../assets/css/home.css';
@import '../../assets/css/product.css';
</style>