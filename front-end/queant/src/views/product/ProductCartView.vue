<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">상품 저장소</h1>
  </header>

  <section class="product_section">
    <!-- 장바구니에 상품이 비어 있을 때 -->
    <div v-if="cart.length === 0" class="cart-none">
      <img src="../../assets/image/물음표개미_none.png" alt="없음" style="width: 300px; height:280px;">
      
      <br><br>
      <h5>담긴 상품이 없습니다.</h5>
      <br><br>
      
      <router-link :to="{name : 'productRecommend'}" class="btn btn-outline-success">상품 추천받기</router-link> 
    </div>

    <!-- 장밥구니에 상품이 담겨 있을 때 -->
    <div v-else>
      <button @click="clearCart()">장바구니 전체 비우기</button>
      <div
        v-for="productInCart in cart"
        :key="productInCart.product_id">
        {{ productInCart }}
        <button @click="popProductInCart(productInCart)">장바구니에서 빼기</button>
        <hr>
      </div>
    </div>
  </section>
</template>

<script>
import Navbar from '@/components/Navbar.vue'
import { mapGetters, mapMutations } from 'vuex'

export default {
  name: 'ProductCartView',
  components : { Navbar },
  computed: {
    ...mapGetters(['cart'])
  },
  methods: {
    ...mapMutations(['CLEAR_CART', 'POP_PRODUCT_IN_CART']),
    clearCart() {
      this.CLEAR_CART()
    },
    popProductInCart(product) {
      this.POP_PRODUCT_IN_CART(product)
    }
  },
  beforeCreate: function() {
    document.body.className = 'home_body'
  },
}
</script>

<style>
@import '../../assets/css/home.css';
@import '../../assets/css/product.css';
</style>