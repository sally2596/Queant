<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">추천 결과</h1>
  </header>
  <div
    v-for="product in tenProducts"
    :key="product">
    <router-link
      :to="{ name: 'productDetail', params: { productId: product.product_id } }">
      {{ product }}
    </router-link>
    <button id="show-modal" @click="openModal([products[0], product])">장바구니에 넣기</button>
    <hr>
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
      return this.products[1].slice(this.productIdx, this.productIdx + 10)
    },
    totalPage() {
      let productsLength = this.products[1].length
      if (productsLength % 10)
        return ((productsLength - (productsLength % 10)) / 10) + 1
      else
        return (productsLength - (productsLength % 10)) / 10
    }
  },
  methods: {
    ...mapActions(['fetchProduct']),
    openModal(value) {
      this.modalData = value,
      this.showModal = true,
      this.fetchProduct(value[1].product_id)
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