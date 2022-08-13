<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">추천 결과</h1>
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
      <tbody v-for="product in tenProducts" :key="product.product_id">
        <td><router-link :to="{ name: 'bankInfoDetail' , params: { bankId: product.bank_id }}"><img :src="product.picture" alt=""></router-link></td>
        <td><router-link :to="{ name: 'productDetail' , params: { productId: product.product_id }}">{{product.name}}</router-link></td>
        <td>{{product.base_rate}}</td>
        <td>{{product.term_min}}</td>
        <button class="btn btn-outline-success btn-sm mx-3" id="show-modal" @click="openModal(product)">담기</button>
      </tbody>
    </table>
  </div>

  <!-- 모달 -->
  <Modal
    v-if="showModal" @close="showModal=false"
    :modalData="modalData">
    <h3>모달 창 제목</h3>
  </Modal>

  <!-- 페이지네이션 -->
  <div
    v-for="page in totalPage"
    :key="page">
    <button class="btn btn-sm" @click="changePage(page)">{{ page }}</button>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import Navbar from '@/components/Navbar.vue'
import Modal from '@/components/Modal.vue'

export default {
  name: 'ProductSavingResultView',
  components: { Navbar, Modal },
  computed: {
    ...mapGetters(['products']),
    tenProducts() {
      return this.products.slice(this.productIdx, this.productIdx + 10)
    },
    totalPage() {
      let productsLength = this.products.length
      if (productsLength % 10)
        return ((productsLength - (productsLength % 10)) / 10) + 1
      else
        return (productsLength - (productsLength % 10)) / 10
    }
  },
  methods: {
    ...mapActions(['fetchProduct']),
    openModal(product) {
      this.modalData = product,
      this.showModal = true,
      this.fetchProduct(product.product_id)
    },
    changePage(page) {
      this.productIdx = (page - 1) * 10 
    }
  },
  data() {
    return {
      showModal: false,
      modalData: null,
      productIdx: 0
    }
  }
}
</script>

<style>

</style>