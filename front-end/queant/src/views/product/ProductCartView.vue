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
        <!-- <Modal
          v-if="showModal" @close="showModal=false"
          :product="modalData">
          <h3>모달 창 제목</h3>
        </Modal>

        <button id="show-modal" @click="openModal(productInCart)">가상 포트폴리오에 넣기</button> -->

        <select 
          @change="pushProductToPortfolio([$event, productInCart])">
          <option selected disabled>선택</option>
          <option
            v-for="number in portfolios.length+1"
            :key="number"
            :value="number">
            {{ number }}번 포트폴리오
          </option>
        </select>

        <button @click="popProductFromCart(productInCart)">장바구니에서 빼기</button>
        <hr>
      </div>
    </div>
    

  </section>
</template>

<script>
import Navbar from '@/components/Navbar.vue'
import Modal from '@/components/Modal.vue'
import { mapGetters, mapMutations } from 'vuex'

export default {
  name: 'ProductCartView',
  components : { Navbar, Modal },
  computed: {
    ...mapGetters(['cart', 'portfolios'])
  },
  methods: {
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
    // openModal(product) {
    //   this.modalData = product,
    //   this.showModal = true
    // }
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