<template>
  <Navbar/>

  <div class="container">
    <h1 class="title" id="title">추천 결과</h1>
    <table class="rwd-table my-5">
      <tbody>
        <tr>
          <th class="text-center">은행</th>
          <th class="text-center">상품명</th>
          <th class="text-center">기본 금리</th>
          <th class="text-center">최소 가입기간</th>
          <th class="text-center">유형</th>
          <th></th>
        </tr>
        <tr
          v-for="product in tenProducts"
          :key="product.product_id">
          <td data-th="Supplier Code">
            <router-link :to="{ name: 'bankInfoDetail' , params: { bankId: product.bank_id }}"><img :src="product.picture" alt=""></router-link>
          </td>
          <td class="text-center" data-th="Supplier Name">
            <router-link style="text-decoration-line: none;" :to="{ name: 'productDetail' , params: { productId: product.product_id }}">{{product.name}}</router-link>
          </td>
          <td class="text-center" data-th="Invoice Number">
            {{ product.base_rate }}%
          </td>
          <td class="text-center" data-th="Invoice Date">
            {{ product.term_min }}개월
          </td>
          <td class="text-center" data-th="Due Date">
            <p v-if="product.deposit">예금</p>
            <p v-else>적금</p>
          </td>
          <td data-th="Net Amount">
            <button class="btn btn-outline-success" id="show-modal" @click="openModal(product)"><i class="fa-solid fa-cart-arrow-down"></i></button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>

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
  name: 'ProductSavingResultView',
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

</style>