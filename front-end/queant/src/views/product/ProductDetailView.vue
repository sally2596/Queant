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
                      <dt style="font-weight: bold; font-size: 120%;">가입 연령</dt>
                      <dd v-if="product?.product.age_min === 0 && product?.product.age_max === 0"><br />제한 없음</dd>
                      <dd v-else-if="product?.product.age_min === 0 && product?.product.age_max !== 0"><br />~ 만{{ product?.product.age_max }}세</dd>
                      <dd v-else-if="product?.product.age_min !== 0 && product?.product.age_max === 0"><br />만{{ product?.product.age_min }}세 ~</dd>
                      <dd v-else><br />만 {{ product?.product.age_min }}세 ~ 만 {{ product?.product.age_max }}세</dd>
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
                  <div v-if="product?.conditions.length === 0">조건 없음</div>
                  <ul v-for="condition in product?.conditions" :key="condition" style="padding-left: 0px;">
                    <li style="list-style: none" v-if="condition.condition_info">
                      - {{ condition.value }} : {{ condition.condition_info }}
                    </li>
                    <li style="list-style: none" v-else>
                      - {{ condition.value }} : 상단 조건과 동일
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

<style scoped>
@import "../../assets/css/product.css";

/* 레페리포인트 */
/* @font-face {
    font-family: 'LeferiPoint-BlackA';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2201-2@1.0/LeferiPoint-BlackA.woff') format('woff');
    font-weight: normal;
    font-style: normal;
} */

@font-face {
    font-family: 'LeferiBaseType-RegularA';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2201-2@1.0/LeferiBaseType-RegularA.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

* {
  font-family: LeferiBaseType-RegularA;
  /* font-family: LeferiPoint-BlackA; */
}

</style>
