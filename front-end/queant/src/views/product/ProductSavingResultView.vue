<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">추천 결과</h1>
  </header>
  <div
    v-for="product in products[1]"
    :key="product">
     <router-link
      :to="{ name: 'productDetail', params: { productId: product.product_id } }">
      {{ product }}
    </router-link>

    <!-- 모달 -->
    <Modal
      v-if="showModal" @close="showModal=false"
      :modalData="modalData">
      <h3>모달 창 제목</h3>
    </Modal>
    <button id="show-modal" @click="openModal([products[0], product])">장바구니에 넣기</button>
    <hr>
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

</style>