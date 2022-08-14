<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">추천 결과</h1>
  </header>
  <section class="product_section">
    <div id="cart-item">
      <table class="border">
        <thead class="border">
          <tr class="text-center">
            <th>은행</th>
            <th>상품명</th>
            <th>기본 금리</th>
            <th>최소 가입 기간</th>
            <th></th>
          </tr>
        </thead>
        <tbody v-for="product in tenProducts" :key="product.product_id" class="border">
          <tr scope="row">
            <td class="col-1 text-center"><router-link :to="{ name: 'bankInfoDetail' , params: { bankId: product.bank_id }}"><img :src="product.picture" alt=""></router-link></td>
            <td class="col-5 text-center"><router-link :to="{ name: 'productDetail' , params: { productId: product.product_id }}">{{product.name}}</router-link></td>
            <td class="col-2 text-center">{{product.base_rate}}%</td>
            <td class="col-2 text-center">{{product.term_min}}개월</td>
            <td class="col-2 text-center"><button class="btn btn-outline-success" id="show-modal" @click="openModal(product)">상품 담기</button></td>
          </tr>
        </tbody>
      </table>
    </div>
  </section>

  <!-- 페이지네이션 -->
  <div class="d-flex justify-content-center mb-5">
    <button v-if="pageIdx" class="btn btn-sm" @click="changePageIdx(-10)">이전</button>
    <div
      v-for="page in displayPages"
      :key="page">
      <button class="btn btn-sm" @click="changePage(page)">{{ page }}</button>
    </div>
    <button v-if="pageIdx < (totalPage.length - (totalPage.length % 10))" class="btn btn-sm" @click="changePageIdx(10)"> 다음 </button>
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
  name: 'ProductDepositResultView',
  components: { Navbar, Modal },
  computed: {
    ...mapGetters(['products']),
    tenProducts() {
      return this.products.slice(this.productIdx, this.productIdx + 10)
    },
    displayPages() {
      return this.totalPage.slice(this.pageIdx, this.pageIdx + 10)
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
    },
    countTotalPages() {
      let productsLength = this.products.length
      if (productsLength % 10) {
        for (var i=1; i <= ((productsLength - (productsLength % 10)) / 10) + 1; i++) {
          this.totalPage.push(i)
        }
      } else {
        for (var i=1; i <= (productsLength - (productsLength % 10)) / 10; i++) {
          this.totalPage.push(i)
        }
      }
    },
    changePageIdx(num) {
      this.pageIdx += num
    }
  },
  data() {
    return {
      showModal: false,
      modalData: null,
      productIdx: 0,
      pageIdx: 0,
      totalPage: []
    }
  },
  created() {
    this.countTotalPages()
  }
}
</script>

<style>
@import '@/assets/css/home.css';
@import '@/assets/css/product.css';
</style>