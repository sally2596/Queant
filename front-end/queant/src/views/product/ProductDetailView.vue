<template>
<Navbar></Navbar>
  <header id="title-div">
    <h1 class="title" id="title">{{product?.product.name}}</h1>
  </header>
  <section class="product-detail-box">     
    <div class="product-detail" v-bind:style="{ background:'url('+product?.product.picture+')'}"
             style="background-size: 300px; background-repeat :no-repeat; background-position:90% 50%;">
      <div class="prd-info m-3">
        <dl>
          <dd class="subject">
              <div>
                <dl><dt>가입기간</dt><dd><br><span>최소 {{product?.product.term_min}}개월 ~ 최대 {{product?.product.term_max}}개월</span></dd></dl><br>
                <dl><dt>최소 가입 연령</dt><dd><br>{{product?.product.age_min}}세</dd></dl><br>
                <dl><dt>최소 금리(12개월 기준, 세전)</dt><dd><br>{{product?.options[0].base_rate}}%</dd></dl>
              </div>
          </dd>
          <br>
          <dd class="foot">
            <p>추가 금리 조건</p>
              <ul v-for="condition in product?.conditions" :key="condition">
                <li style="list-style:none;">- {{condition.value}} : {{condition.condition_info}}</li>
              </ul>
          </dd>
        </dl>
        <br><br>
        <button class="btn btn-outline-success btn-sm mx-3" @click="openModal(product?.product)"><h3><i class="fa-solid fa-cart-arrow-down"></i></h3></button>
      </div>
    </div>
    <!-- 모달 -->
    <Modal
      v-if="showModal" @close="showModal=false"
      :modalData="modalData">
      <h3>모달 창 제목</h3>
    </Modal>
  </section>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import Navbar from '@/components/Navbar.vue'
import Modal from '@/components/Modal.vue'

export default {
  name: 'ProductDetailView',
  components: { Navbar, Modal },
  computed: {
    ...mapGetters(['product'])
  },
  methods: {
    ...mapActions(['fetchProduct']),
    openModal(value) {
      this.modalData = value,
      this.showModal = true
    }
  },
  data() {
    return {
      showModal: false,
      modalData: null
    }
  },
  created() {
    this.fetchProduct(this.$route.params.productId);
  }
}
</script>

<style>
@import '../../assets/css/product.css';
</style>