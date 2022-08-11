<template>
<Navbar></Navbar>
  <header id="title-div">
    <h1 class="title" id="title">'{{product.product.name}}' 상세 페이지</h1>
  </header>
  <section class="product-detail-box">
      <div class="product-detail">
        <div class="product clearfix">
            <div class="prd-info">
                <dl>
                    <dt class="name">상품 이름 : {{product.product.name}}</dt>
                    <dd class="subject">
                        <div>
                          <dl><dt>가입기간</dt><dd><span>최소 {{product.product.term_min}}개월 ~ 최대 {{product.product.term_max}}개월</span></dd></dl>
                          <dl><dt>가입금액</dt><dd>1백만원 이상</dd></dl>
                          <dl><dt>최소 금리(12개월 기준)</dt><dd>{{product.options[0].base_rate}}</dd></dl>
                        </div>
                    </dd>
                    <dd class="foot">
                      <p>추가 금리 조건</p>
                        <ul v-for="condition in product.conditions">
                          <li>{{condition.value}} : {{condition.condition_info}}</li>
                        </ul>
                    </dd>
                </dl>
                <div class="prd-btn-area">                       
                  <span class=" btn-pack btn-type-3c ui-btn-pack-a ui-set-btn-pack ui-set-btn-pack-event"><a href="#none" @click="pushProductToCart(product)">장바구니에 넣기</a></span>	                
                </div>
            </div>   
            <div class="prd-image">
              <img alt="" :src="product.product.picture">
            </div>
          </div>
      </div>

      
      
      
  </section>
</template>

<script>
import { mapActions, mapGetters, mapMutations } from 'vuex'
import Navbar from '@/components/Navbar.vue'

export default {
    name: "ProductDetailView",
    computed: {
        ...mapGetters(["product"])
    },
    methods: {
        ...mapActions(["fetchProduct"]),
        ...mapMutations(["CLEAR_CART", "POP_PRODUCT_FROM_CART", "PUSH_PRODUCT_TO_PORTFOLIO", "PUSH_PRODUCT_TO_CART"]),
    },
    created() {
        this.fetchProduct(this.$route.params.productId);
    },
    components: { Navbar }
}
</script>

<style>
@import '../../assets/css/product.css';
</style>