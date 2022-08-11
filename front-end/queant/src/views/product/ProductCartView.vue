<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">상품 저장소</h1>
  </header>
  {{cart}}
  <section class="product_section">
    <!-- 장바구니에 상품이 비어 있을 때 -->
    <div v-if="cart.length === 0" class="cart-none">
      <img src="../../assets/image/물음표개미_none.png" alt="없음" style="width: 30%; height: 30%;">
      
      <br><br>
      <h5>담긴 상품이 없습니다.</h5>
      <br><br>
      
      <router-link :to="{name : 'productRecommend'}" class="btn btn-outline-success">상품 추천받기</router-link> 
    </div>
    <!-- 장밥구니에 상품이 담겨 있을 때 -->
    <div v-else>
      <button @click="clearCart()">장바구니 전체 비우기</button>
      <br><br><br>
      <table>
        <thead>
          <tr>
            <th><input type="checkbox" name="__allcheck" class="MS_input_checkbox" checked=""></th>
            <th>은행</th>
            <th>상품명</th>
            <th>기본 금리</th>
            <th>최소 가입 기간(개월)</th>
          </tr>
        </thead>
        <br>
        <tbody v-for="productInCart in cart" :key="productInCart.product_id">
          <td><input type="checkbox" name="basketchks" id="basketchks" checked="checked" class="MS_input_checkbox"></td>
          <td><router-link :to="{ name: 'bankInfoDetail' , params: { bankId: productInCart.bank_id }}"><img :src="productInCart.picture" alt=""></router-link></td>
          <td><router-link :to="{ name: 'productDetail' , params: { productId: productInCart.product_id }}">{{productInCart.name}}</router-link></td>
          <td>{{productInCart.base_rate}}</td>
          <td>{{productInCart.term_min}}</td>
          <button class="btn btn-outline-second btn-sm" @click="addProductInCart(productInCart)">선택 상품 포트폴리오에 넣기</button>
          <button class="btn btn-outline-second btn-sm" @click="popProductInCart(productInCart)">선택 상품 삭제</button>
        </tbody>
      </table>
      <br><br><br><br>
    </div>
  </section>
</template>

<script>
import Navbar from '@/components/Navbar.vue'
import Modal from '@/components/Modal.vue'
import { mapActions, mapGetters, mapMutations } from 'vuex'

export default {
  name: 'ProductCartView',
  components : { Navbar, Modal },
  computed: {
    ...mapGetters(['cart', 'portfolios'])
  },
  methods: {
    ...mapActions(['fetchProduct']),
    ...mapMutations(['CLEAR_CART', 'POP_PRODUCT_FROM_CART', 'PUSH_PRODUCT_TO_PORTFOLIO']),
    clearCart() {
      this.CLEAR_CART()
    },
    popProductFromCart(product) {
      this.POP_PRODUCT_FROM_CART(product)
    },
    pushProductToPortfolio(value) {
      this.PUSH_PRODUCT_TO_PORTFOLIO(value)
    },
    openModal(value) {
      this.modalData = value,
      this.showModal = true,
      this.fetchProduct(value[1].product_id)
    }
  },
  data() {
    return {
      showModal: false,
      productIdx: '',
      modalData: null
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