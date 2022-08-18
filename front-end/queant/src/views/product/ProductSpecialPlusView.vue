<template>
    <Navbar/>
		<section id="hero">
			<h1 class="title" id="title">상품제보</h1>
			<section class="search_filter" style="padding:0; width:600px;">
				<div class="filter_button_wrap">
					<div style="width:100%; background-color: #3e8e41; height: 50px; text-align: center; line-height:3rem; font-size: 1.5rem; color: white;"><strong>필요하신 상품 정보를 기입해 주세요!</strong></div>
				</div>
				<div class="product_section" style="padding: 60px 0;">
					<form @submit="checkForm()">
						<div class="int-area">
							<input type="text" v-model="product.bankName" id="bank">
							<label class="form-label" for="bank" autocomplete="off">은행명</label>
						</div>
						<div class="int-area">
							<input type="text" v-model="product.productName" id="prd">
							<label class="form-label" for="prd" autocomplete="off">상품명</label>
						</div>
						<br><br>
						<input type="submit" class="btn btn-sm btn-outline-secondary" value="제보하기">
					</form>
				</div>
			</section>

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
  methods: {
    ...mapActions(['registReport']),
		checkForm() {
      if (this.product.bankName && this.product.productName) {
				this.product.memberEmail = this.userInfo.email;
				this.registReport(this.product);	
				alert("제보가 성공적으로 완료되었습니다!")	
      } else {
				if (!this.product.bankName) {
        alert('은행 이름을 입력해주세요.');
				}
				else if (!this.product.productName) {
					alert('상품 이름을 입력해주세요.');
				}
			}
    }
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
        referenceData : ''
      },
    }
  }
}
</script>

<style scoped>
@import '../../assets/css/product.css';
@import '../../assets/css/common.css';
@import '../../assets/css/home.css';
</style>