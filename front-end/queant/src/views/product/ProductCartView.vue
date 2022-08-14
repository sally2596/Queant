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
      <h3><strong>담긴 상품이 없습니다.</strong></h3>
      <br>
      <h6>상품을 <router-link :to="{ name: 'productRecommend'}" style="text-decoration-line : none;">추천</router-link> 받거나, 상단의 검색창을 이용해 추가해보세요.</h6>
    </div>

    <!-- 장바구니에 상품이 담겨 있을 때 -->
    <div v-else id="cart-item">
      <button class="btn btn-outline-danger btn-sm" @click="clearCart()">장바구니 전체 비우기 <i class="fa-solid fa-circle-minus fa-lg"></i></button>
      <button class="btn btn-outline-primary btn-sm" @click="addComparisonPortfolio()">가상 포트폴리오 추가하기</button>
      <button class="btn btn-outline-success btn-sm" @click="clearcomparisonportfolio()" v-if="comparisonPortfolio.length != 0">포트폴리오 전체 삭제</button>
      <br><br>
      <table class="table table--block" cellspacing="0" cellpadding="0">
        <thead>
          <tr>
            <th>은행</th>
            <th>상품명</th>
            <th>금리</th>
            <th>기간(개월)</th>
            <th>비교 포트폴리오에 추가</th>
            <th>내 포트폴리오에 추가</th>
            <th>상품 삭제하기</th>
          </tr>
        </thead>
        <tbody class="border" v-for="productInCart in cart" :key="productInCart.product.product_id">
          <td><router-link :to="{ name: 'bankInfoDetail' , params: { bankId: productInCart.product.bank_id }}"><img :src="productInCart.product.picture" alt="" style="width: 2rem;"></router-link></td>
          <td><router-link style="text-decoration-line: none;" :to="{ name: 'productDetail' , params: { productId: productInCart.product.product_id }}">{{productInCart.product.name}}</router-link></td>
          <td>{{productInCart.product.base_rate}}%</td>
          <td>{{productInCart.product.term_min}}</td>
          <td class="flex-wrap">
            <p v-if="comparisonPortfolio.length === 0">포트폴리오가 없습니다.</p>
            <button v-for="cportfolio in comparisonPortfolio"
             :key="cportfolio.cportfolio_cnt"
             class="btn btn-outline-primary btn-sm p-1"
             style="height: 2rem; font-size: 0.8rem;"
             @click="pushProductToCportfolio([cportfolio.cportfolio_cnt, productInCart])">
              {{cportfolio.cportfolio_cnt}}번 <i class="fa-solid fa-circle-plus"></i>
            </button>
          </td>
          <td><button class="btn btn-outline-success btn-sm p-1" style="height: 2rem;" @click="addProductInCart(productInCart)"><i class="fa-solid fa-circle-plus fa-lg"></i></button></td>
          <td><button class="btn btn-outline-danger btn-sm p-1" style="height: 2rem;" @click="popProductFromCart(productInCart)"><i class="fa-solid fa-circle-minus fa-lg"></i></button></td>
        </tbody>
      </table>
    </div>
  </section>
  <section class="product_section">
    <div v-if="comparisonPortfolio.length === 0" class="cart-none">
      <img src="../../assets/image/물음표개미_none.png" alt="없음" style="width: 30%; height: 30%;">
      
      <br><br>
      <h5>가상 포트폴리오가 없습니다.</h5>
      <button class="btn btn-outline-primary btn-sm" @click="addComparisonPortfolio()">가상 포트폴리오 추가하기</button>
      <br><br>
    </div>
    <div v-else id="cart-item">
      <div id="cportfolio_container" class="d-flex">
        <div class="border m-2 col-3-lg col-6" v-for="cportfolio in comparisonPortfolio" :key="cportfolio" style="height:30vh; width: 25%;">
          <div v-if="cportfolio.products.length === 0">
            <div class="border">
              <h5 class="text-center"> 예상 포트폴리오 {{cportfolio.cportfolio_cnt}}</h5>
            </div>
            <div>아직 상품이 없습니다.</div>
          </div>
          <div v-else>
            <div class="border" style="background-color: #92ce95;">
              <h5 class="text-center"> 예상 포트폴리오 {{cportfolio.cportfolio_cnt}}</h5>
            </div>
            <div v-for="cproduct in cportfolio.products" :key="cproduct.product.product_id">
              <ul>
                <li style="font-size: 1rem"><router-link :to="{ name: 'productDetail' , params: { productId: cproduct.product.product_id }}">{{cproduct.product.name}}</router-link></li>
             </ul>
            </div>
          </div>
        </div>
      </div>
      
    <button class="btn btn-outline-success" @click="addComparisonPortfolio()">포트폴리오 추가하기</button>
    <button class="btn btn-outline-success" @click="clearcomparisonportfolio()">포트폴리오 전체 삭제</button>
    <button class="btn btn-outline-success" @click="saveToDb()">최종 저장</button>
    <button class="btn btn-outline-success" @click="getFromDb()">DB 불러오기</button>
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
    ...mapGetters(['userInfo', 'cart', 'portfolios', 'comparisonPortfolio'])
  },
  methods: {
    ...mapActions(['fetchProduct', 'saveToDb', 'getFromDb']),
    ...mapMutations(['CLEAR_CART', 'POP_PRODUCT_FROM_CART', 'PUSH_PRODUCT_TO_PORTFOLIO', 'ADD_COMPARISON_PORTFOLIO', 'CLEAR_CPORTFOLIOS', 'PUSH_PRODUCT_TO_CPORTFOLIO', 'POP_CPORTFOLIO', 'UPDATE_CPORTFOLIO_FROM_DB']),
    clearCart() {
      this.CLEAR_CART()
    },
    deleteCportfolio(cportfolio_cnt) {
      this.POP_CPORTFOLIO(cportfolio_cnt)
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