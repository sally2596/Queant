<template>
    <Navbar/>
    <header id="title-div">
      <h1 class="title" id="title">특판 상품 제보</h1>
    </header>
    <section class="product_section">
      <div>
        <form @submit="checkForm()">
					<div class="int-area">
						<input type="text" v-model="product.bankName" id="bank">
						<label class="form-label" for="bank" autocomplete="off">은행명</label>
					</div>
					<div class="int-area">
						<input type="text" v-model="product.productName" id="prd">
						<label class="form-label" for="prd" autocomplete="off">상품명</label>
					</div>
					<div class="int-area">
						<input type="text" v-model="product.referenceData" id="ref">
						<label class="form-label" for="ref" autocomplete="off">관련 링크</label>
					</div>
          <input type="submit" class="btn btn-sm btn-outline-secondary" value="제보하기">
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
		checkForm() {
      if (this.product.bankName && this.product.productName && this.product.referenceData) {
				this.product.memberEmail = this.userInfo.email;
				this.registReport(this.product);	
				alert("제보가 성공적으로 완료되었습니다!")
				this.$router.go();	
      } else {
				if (!this.product.bankName) {
        alert('은행 이름을 입력해주세요.');
				}
				else if (!this.product.productName) {
					alert('상품 이름을 입력해주세요.');
				}
				else if (!this.product.referenceData) {
					alert('관련 링크를 입력해주세요.');
      	}
			}
    },
  }
}
</script>

<style>
@import '../../assets/css/home.css';
@import '../../assets/css/product.css';
</style>