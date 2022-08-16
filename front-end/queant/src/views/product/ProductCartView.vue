<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">상품 저장소</h1>
  </header>
  <!-- 장바구니 섹션 -->
  <section class="product_section">
    <!-- 장바구니에 상품이 비어 있을 때 -->
    <div v-if="cart.length === 0" class="cart-none">
      <img src="../../assets/image/물음표개미_none.png" alt="없음" style="width: 240px; height:240px;">
      <br><br>
      <h3><strong>담긴 상품이 없습니다.</strong></h3>
      <br>
      <h6>상품을 <router-link :to="{ name: 'productRecommend'}" style="text-decoration-line : none;">추천</router-link> 받거나, 상단의 검색창을 이용해 추가해보세요.</h6>
    </div>

    <!-- 장바구니에 상품이 담겨 있을 때 -->
    <div v-else id="cart-item">
      
    <h1 class="title" id="title">장바구니</h1>
      <button class="btn btn-outline-danger btn-sm" @click="clearCart()">장바구니 전체 비우기 <i class="fa-solid fa-circle-minus fa-lg"></i></button>
      <button class="btn btn-outline-primary btn-sm" @click="addComparisonPortfolio()">가상 포트폴리오 추가 <i class="fa-solid fa-circle-plus fa-lg"></i></button>
      <button  class="btn btn-outline-success btn-sm" v-show="isLoggedIn" @click="saveToDb()">가상 포트폴리오 최종 저장</button>
      <br><br>
      <table class="table table--block" cellspacing="0" cellpadding="0">
        <thead>
          <tr class="text-center">
            <th>은행</th>
            <th>상품명</th>
            <th>금리</th>
            <th>기간</th>
            <th>비교 포트폴리오</th>
            <th>내 포트폴리오</th>
            <th>삭제</th>
          </tr>
        </thead>
        <tbody class="border" v-for="productInCart in cart" :key="productInCart.product.product_id">
          <td><router-link :to="{ name: 'bankInfoDetail' , params: { bankId: productInCart.product.bank_id }}"><img :src="productInCart.product.picture" alt="" style="width: 2rem;"></router-link></td>
          <td><router-link style="text-decoration-line: none;" :to="{ name: 'productDetail' , params: { productId: productInCart.product.product_id }}">{{productInCart.product.name}}</router-link></td>
          <td>{{productInCart.applied_rate}}%</td>
          <td>{{productInCart.applied_period}}</td>
          <td class="flex-wrap">
            <button v-for="cportfolio in comparisonPortfolio"
             :key="cportfolio.cportfolio_cnt"
             class="btn btn-outline-primary btn-sm p-1"
             style="height: 2rem; font-size: 0.8rem;"
             @click="pushProductToCportfolio([cportfolio.cportfolio_cnt, productInCart])">
              {{cportfolio.cportfolio_cnt}}번 <i class="fa-solid fa-circle-plus"></i>
            </button>
          </td>
          <td><button class="btn btn-outline-success btn-sm p-1" style="height: 2rem;" @click="pushProductToPortfolio(productInCart)"><i class="fa-solid fa-circle-plus fa-lg"></i></button></td>
          <td><button class="btn btn-outline-danger btn-sm p-1" style="height: 2rem;" @click="popProductFromCart(productInCart)"><i class="fa-solid fa-circle-minus fa-lg"></i></button></td>
        </tbody>
      </table>
    </div>
  </section>

<!-- 가상 포트폴리오 섹션 -->
<section class="product-detail-box">
    
    <div v-if="comparisonPortfolio.length === 0" class="cart-none">
      <img src="../../assets/image/물음표개미_none.png" alt="없음" style="width:30%">
      
      <br><br>
      <h5>가상 포트폴리오가 없습니다.</h5>
      <br><br>
    </div>

    <div v-else>
      <h1>가상 포트폴리오</h1>
      <div class="product-detail d-flex flex-wrap justify-content-center">
        <div class="m-2 p-4 border border-1 d-grid gap-2" v-for="cportfolio in comparisonPortfolio" id="cportfolio" :key="cportfolio">
          <div class="border p-2" style="height: 40px; background-color: #92ce95; font-family: 'jua';">
            <h5 class="text-center m-0"> 예상 포트폴리오 {{cportfolio.cportfolio_cnt}}</h5>
          </div>
          <div class="my-2 mx-2" v-if="cportfolio.products.length === 0">
            <div>아직 상품이 없습니다.</div>
          </div>
          <div v-else v-for="cproduct in cportfolio.products" class="d-flex mx-2 my-2" style="font-size: 15px;" :key="cproduct">
                {{cproduct.product.name}}
                <button style="height:1.2rem; font-size: 5px;" class="d-flex align-items-center p-2 btn btn-outline-danger btn-sm" @click="popProductFromCPortfolio([cportfolio.cportfolio_cnt, cproduct])">상품삭제</button>
          </div>
          <button class="btn btn-danger btn-sm" type="button" @click="deleteCportfolio(cportfolio.cportfolio_cnt)">포트폴리오 삭제</button>
        </div>
        
      </div>
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
  components : { Navbar, Modal, draggable },
  computed: {
    ...mapGetters(['isLoggedIn', 'userInfo', 'cart', 'portfolios', 'comparisonPortfolio', 'newlyAddedPortfolio', 'deletedPortfolio', 'products', 'comparisonProducts']),
  },
  methods: {
    ...mapActions(['fetchProduct', 'saveToDb', 'getFromDb', 'pushProductToPortfolio']),
    ...mapMutations(['POP_PRODUCT_FROM_CPORTFOLIO', 'CLEAR_CPORTFOLIO_DB', 'CLEAR_CART',
    'POP_PRODUCT_FROM_CART', 'PUSH_PRODUCT_TO_PORTFOLIO', 'ADD_COMPARISON_PORTFOLIO',
    'CLEAR_CPORTFOLIOS', 'PUSH_PRODUCT_TO_CPORTFOLIO', 'POP_CPORTFOLIO', 'UPDATE_CPORTFOLIO_FROM_DB']),
    
    popProductFromCPortfolio(value) {
      this.POP_PRODUCT_FROM_CPORTFOLIO(value)
    },
    clearDB() {
      this.CLEAR_CPORTFOLIO_DB()
    },
    clearCart() {
      this.CLEAR_CART()
    },
    deleteCportfolio(cportfolio_cnt) {
      this.POP_CPORTFOLIO(cportfolio_cnt)
    },
    popProductFromCart(product) {
      this.POP_PRODUCT_FROM_CART(product)
    },
    openModal(value) {
      this.modalData = value,
      this.showModal = true,
      this.fetchProduct(value[1].product_id)
    },
    addComparisonPortfolio() {
      this.ADD_COMPARISON_PORTFOLIO()
    },
    clearcomparisonportfolio() {
      this.CLEAR_CPORTFOLIOS()
    },
    pushProductToCportfolio(value) {
      this.PUSH_PRODUCT_TO_CPORTFOLIO(value)
    },
  },
  mounted() {
    this.getFromDb()
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