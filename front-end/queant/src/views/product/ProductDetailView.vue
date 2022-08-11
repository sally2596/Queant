<template>
<Navbar></Navbar>
  <header id="title-div">
    <h1 class="title" id="title">{{product.product.name}}</h1>
  </header>
  <section class="product-detail-box">     
    <div class="product-detail" v-bind:style="{ background:'url('+product.product.picture+')'}"
             style="background-size: 300px;; background-repeat :no-repeat; background-position:90% 50%;">
      <div class="prd-info m-5">
        <dl>
          <dd class="subject">
              <div>
                <dl><dt>가입기간</dt><dd><br><span>최소 {{product.product.term_min}}개월 ~ 최대 {{product.product.term_max}}개월</span></dd></dl><br>
                <dl><dt>최소 가입 연령</dt><dd><br>{{product.product.age_min}}세</dd></dl><br>
                <dl><dt>최소 금리(12개월 기준, 세전)</dt><dd><br>{{product.options[0].base_rate}}%</dd></dl>
              </div>
          </dd>
          <br>
          <dd class="foot">
            <p>추가 금리 조건</p>
              <ul v-for="condition in product.conditions">
                <li style="list-style:none;">- {{condition.value}} : {{condition.condition_info}}</li>
              </ul>
          </dd>
        </dl>
      </div>
      
      <div class="prd-btn-area m-5 d-flex justify-content-center">
        <button class="btn btn-outline-success btn-sm mx-3">내 포트폴리오에 넣기</button>
        <button class="btn btn-outline-success btn-sm mx-3" @click="pushProductToCart(product)">장바구니에 넣기</button>	                
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
        pushProductToCart(product) {
        this.PUSH_PRODUCT_TO_CART(product)
      }
    },
    created() {
        this.fetchProduct(this.$route.params.productId);
    },
    components: { Navbar,},
}
</script>

<style>
@import '../../assets/css/product.css';
</style>