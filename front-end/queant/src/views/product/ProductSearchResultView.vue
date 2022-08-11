<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">'{{this.$route.params.text}}' 상품 검색 결과</h1>
  </header>
  <div id="cart-item">
    <table>
      <thead>
        <tr>
          <th>은행</th>
          <th>상품명</th>
          <th>기본 금리</th>
          <th>최소 가입 기간(개월)</th>
        </tr>
      </thead>
        <br>
      <tbody v-for="product in products[1]" :key="product.product_id">
        <td><router-link :to="{ name: 'bankInfoDetail' , params: { bankId: product.bank_id }}"><img :src="product.picture" alt=""></router-link></td>
        <td><router-link :to="{ name: 'productDetail' , params: { productId: product.product_id }}">{{product.name}}</router-link></td>
        <td>{{product.base_rate}}</td>
        <td>{{product.term_min}}</td>
        <button id="show-modal" @click="openModal([products[0], product])">장바구니에 넣기</button>

      </tbody>
    </table>
  </div>
  <!-- 모달 -->
  <Modal
    v-if="showModal" @close="showModal=false"
    :modalData="modalData">
    <h3>모달 창 제목</h3>
  </Modal>
</template>
<script>
import { mapActions, mapGetters } from 'vuex'
import Navbar from '@/components/Navbar.vue'
import Modal from '@/components/Modal.vue'

export default {
  name: 'ProductSearchResultView',
  components: { Navbar, Modal },
  computed: {
    ...mapGetters(['products'])
  },
  methods: {
    ...mapActions(['fetchProduct']),
    openModal(value) {
      this.modalData = value,
      this.showModal = true,
      this.fetchProduct(value[1].product_id)
    }
  },
  data() {
    return {
      showModal: false,
      modalData: null
    }
  }
}
</script>
<style>
@import '@/assets/css/home.css';
@import '@/assets/css/product.css';
</style>