<template>
  <Navbar></Navbar>
  <header id="title-div">
    <h1 class="title" id="title">          <img v-bind:src="product?.product.picture" height="100" width="100"/>{{ product?.product.name }}</h1>
  </header>
  <!-- 내가 수정한곳 시작 -->
  <div class="container mt-4 mb-4 p-3 d-flex justify-content-center">
    <div class="card p-4">
      <div
        class="image d-flex flex-column justify-content-center align-items-center"
      >

        <section class="product-detail-box" style="padding-top: 5px;">
            <div class="prd-info m-3">
              <dl>
                <dd class="subject">
                  <div class="product-detail-sub">
                    <dl class="product-detail-sub-box">
                      <dt style="font-weight: bold; font-size: 120%;">가입기간</dt>
                      <dd>
                        <br /><span
                          >최소 {{ product?.product.term_min }}개월 ~ 최대
                          {{ product?.product.term_max }}개월</span
                        >
                      </dd>
                    </dl>
                    <br />
                    <dl class="product-detail-sub-box">
                      <dt style="font-weight: bold; font-size: 120%;">최소 가입 연령</dt>
                      <dd><br />{{ product?.product.age_min }}세</dd>
                    </dl>
                    <br />
                    <dl class="product-detail-sub-box">
                      <dt style="font-weight: bold; font-size: 120%;">최소 금리(12개월 기준, 세전)</dt>
                      <dd><br />{{ product?.options[0].base_rate }}%</dd>
                    </dl>
                  </div>
                </dd>
                <br />
                <dd class="foot">
                  <p style="font-weight: bold; font-size: 24px;">추가 금리 조건</p>
                  <ul v-for="condition in product?.conditions" :key="condition" style="padding-left: 0px;">
                    <li style="list-style: none">
                      - {{ condition.value }} : {{ condition.condition_info }}
                    </li>
                  </ul>
                </dd>
              </dl>
              <br /><br />
              <button
                class="btn btn-outline-success btn-sm mx-3 product_detail_button"
                @click="openModal(product?.product)"
              >
                <h3 style="font-weight: bold;margin-top: 5px; margin-bottom: 5px;">상품담기<i class="fa-solid fa-cart-arrow-down"></i></h3>
              </button>
            </div>
          <!-- 모달 -->
          <Modal v-if="showModal" @close="showModal = false" :modalData="modalData">
            <h3>모달 창 제목</h3>
          </Modal>
        </section>
      </div>
    </div>
  </div>
  <!-- 내가 수정한곳 끝 -->
</template>

<script>
import { mapActions, mapGetters } from "vuex";
import Navbar from "@/components/Navbar.vue";
import Modal from "@/components/Modal.vue";

export default {
  name: "ProductDetailView",
  components: { Navbar, Modal },
  computed: {
    ...mapGetters(["product"]),
  },
  methods: {
    ...mapActions(["fetchProduct"]),
    openModal(value) {
      (this.modalData = value), (this.showModal = true);
    },
  },
  data() {
    return {
      showModal: false,
      modalData: null,
    };
  },
  created() {
    this.fetchProduct(this.$route.params.productId);
  },
};
</script>

<style>
@import "../../assets/css/product.css";
</style>
