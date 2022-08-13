<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">상품 저장소</h1>
  </header>
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
        <tbody v-for="productInCart in cart" :key="productInCart.product.product_id">
          <td><input type="checkbox" name="basketchks" id="basketchks" checked="checked" class="MS_input_checkbox"></td>
          <td><router-link :to="{ name: 'bankInfoDetail' , params: { bankId: productInCart.product.bank_id }}"><img :src="productInCart.product.picture" alt=""></router-link></td>
          <td><router-link :to="{ name: 'productDetail' , params: { productId: productInCart.product.product_id }}">{{productInCart.product.name}}</router-link></td>
          <td>{{productInCart.product.base_rate}}</td>
          <td>{{productInCart.product.term_min}}</td>
          <button class="btn btn-outline-second btn-sm" @click="addProductInCart(productInCart)">선택 상품 내 포트폴리오에 넣기</button>
          <div v-for="cportfolio in comparisonportfolios" :key="cportfolio.cportfolio_cnt">
            <button class="btn btn-outline-second btn-sm" @click="pushProductToCportfolio([cportfolio.cportfolio_cnt, productInCart])">{{cportfolio.cportfolio_cnt}}번 포트폴리오에 상품 추가</button>
          </div>
          <button class="btn btn-outline-second btn-sm" @click="popProductInCart(productInCart)">선택 상품 삭제</button>
        </tbody>
      </table>
      <br><br><br><br>
    </div>
  </section>
  <section class="product_section">
    <div v-if="comparisonportfolios.length === 0" class="cart-none">
      <img src="../../assets/image/물음표개미_none.png" alt="없음" style="width: 30%; height: 30%;">
      
      <br><br>
      <h5>가상 포트폴리오가 없습니다.</h5>
      <br><br>
      
      <button class="btn btn-outline-success" @click="addcomparisonportfolio(userInfo)">가상 포트폴리오 추가하기</button> 
    </div>
    <div v-else>
      <div class="container row">
        <div class="col-3" v-for="cportfolio in comparisonportfolios" :key="cportfolio">
          <div v-if="cportfolio.products.length === 0">
            <div>아직 {{cportfolio.cportfolio_cnt}}번 포트폴리오에 상품이 없습니다.</div>
          </div>
          <div v-else>
            <h5>{{cportfolio.cportfolio_cnt}}번 포트폴리오 상품</h5>
            <div v-for="cproduct in cportfolio.products" :key="cproduct">
            <p>{{cproduct.product.name}}</p>
            </div>
          </div>
        </div>
      </div>
      
    <button class="btn btn-outline-success" @click="addcomparisonportfolio(userInfo)">포트폴리오 추가하기</button> 
    <button class="btn btn-outline-success" @click="clearcomparisonportfolio()">포트폴리오 전체 삭제</button>
    <button class="btn btn-outline-success" @click="saveToDb()">최종 저장</button>
    </div>
  </section>
</template>

<script>
import Navbar from '@/components/Navbar.vue'
import Modal from '@/components/Modal.vue'
import { mapActions, mapGetters, mapMutations } from 'vuex'
import draggable from 'vuedraggable'

export default {
  name: 'ProductCartView',
  components : { Navbar, Modal, draggable},
  computed: {
    ...mapGetters(['userInfo', 'cart', 'portfolios', 'comparisonportfolios'])
  },
  methods: {
    ...mapActions(['fetchProduct', 'saveToDb']),
    ...mapMutations(['CLEAR_CART', 'POP_PRODUCT_FROM_CART', 'PUSH_PRODUCT_TO_PORTFOLIO', 'PUSH_CPORTFOLIO_TO_COMPARISONPORTFOLIOS', 'CLEAR_CPORTFOLIOS', 'PUSH_PRODUCT_TO_CPORTFOLIO']),
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
    },
    addcomparisonportfolio(value) {
      this.PUSH_CPORTFOLIO_TO_COMPARISONPORTFOLIOS(value)
    },
    clearcomparisonportfolio() {
      this.CLEAR_CPORTFOLIOS()
    },
    pushProductToCportfolio(value) {
      this.PUSH_PRODUCT_TO_CPORTFOLIO(value)
    }, 
  },
  data() {
    return {
      showModal: false,
      productIdx: '',
      modalData: null,
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