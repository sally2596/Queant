<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">내 포트폴리오 관리</h1>
  </header>
  <div>

    <div class="d-flex justify-content-center my-5">
      <input 
        type="text"
        placeholder="QueÆnt에서 상품찾기"
        v-model="text"
        @keyup.enter="fetchProductsByText(text)">
      <button class="searching" @click="fetchProductsByText(text)"><i class="fa-solid fa-magnifying-glass"></i></button>
      <button class="btn btn-outline-success btn-sm mx-3" @click="openCustomProductModal()">직접 추가하기</button>
    </div>


    <div class="container">
      <h2 style="font-family: NanumSquareRound;" class="text-center my-5">퀸트에서 등록한 상품들</h2>
      <table class="rwd-table">
        <tbody>
          <tr>
            <th class="text-center">기관</th>
            <th class="text-center">상품명</th>
            <th class="text-center">납입금액</th>
            <th class="text-center">가입기간</th>
            <th class="text-center">유형</th>
            <th class="text-center">적용금리</th>
            <th class="text-center">관리</th>
          </tr>
          <tr
            v-for="myProduct in portfolio"
            :key="myProduct">
            <td data-th="Supplier Code">
              <router-link class="d-flex" style="text-decoration-line: none;" :to="{ name: 'bankInfoDetail', params: { bankId: myProduct.product.bank_id } }">
              <img :src="myProduct.product.picture" :alt="`${myProduct.product.name}`">
              <!-- <div
                v-for="char in myProduct.product.picture.slice(53)"
                :key=char>
                <p v-if="char !== '.' && char !== 'p' && char !== 'n' && char !== 'g'">{{ char }}</p>
              </div> -->
              </router-link>
            </td>
            <td class="text-center" data-th="Supplier Name">
              <router-link style="text-decoration-line: none;" :to="{ name: 'productDetail', params: { productId: myProduct.product_id } }">
                <p class="text-center">{{ myProduct.product.name }}</p>
              </router-link>
            </td>
            <td class="text-center" data-th="Invoice Number">
              {{ String(myProduct.amount).replace(/\B(?=(\d{3})+(?!\d))/g, ',') }}원
            </td>
            <td class="text-center" data-th="Invoice Date">
              {{ myProduct.start_date }} ~ {{ myProduct.end_date }}
            </td>
            <td class="text-center" data-th="Due Date" v-if="myProduct.product.deposit">
              예금
						</td>
						<td class="text-center" data-th="Due Date" v-if="!myProduct.product.deposit">
              적금
            </td>
            <td class="text-center" data-th="Net Amount">
              {{ myProduct.option.high_base_rate }}%
            </td>
            <td>
              <button class="btn btn-outline-success btn-sm mx-1" @click="openMyProductModal(myProduct)">수정</button>
              <button class="btn btn-outline-danger btn-sm mx-1" @click="deletePortfolio(myProduct.portfolio_id)">삭제</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <div class="container my-5">
    <h2 style="font-family: NanumSquareRound;" class="text-center my-5">직접 추가한 상품들</h2>
      <table class="rwd-table">
        <tbody>
          <tr>
            <th class="text-center">기관</th>
            <th class="text-center">상품명</th>
            <th class="text-center">납입금액</th>
            <th class="text-center">가입기간</th>
            <th class="text-center">유형</th>
            <th class="text-center">적용금리</th>
            <th class="text-center">관리</th>
          </tr>
          <tr
            v-for="customProduct in customProducts"
            :key="customProduct">
            <td class="text-center" data-th="Supplier Code">
              <p>{{ customProduct.institution_name }}</p>
            </td>
            <td class="text-cenetr" data-th="Supplier Name">
              <p class="text-center">{{ customProduct.product_name }}</p>
            </td>
            <td class="text-center" data-th="Invoice Number">
              {{ String(customProduct.amount).replace(/\B(?=(\d{3})+(?!\d))/g, ',') }}원
            </td>
            <td class="text-center" data-th="Invoice Date">
              {{ customProduct.start_date }} ~ {{ customProduct.end_date }}
            </td>
            <td class="text-center" data-th="Due Date">
              <p v-if="customProduct.deposit">예금</p>
              <p v-else>적금</p>
            </td>
            <td class="text-center" data-th="Net Amount">
              {{ customProduct.base_rate }}%
            </td>
            <td>
              <button class="btn btn-outline-success btn-sm mx-1" @click="openCustomProductEditModal(customProduct)">수정</button>
              <button class="btn btn-outline-danger btn-sm mx-1" @click="deleteCustomProduct(customProduct.product_id)">삭제</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- 사용자 정의 상품 추가 모달 -->
    <CustomProductModal 
      v-if="showCustomProductModal" 
      @close="showCustomProductModal=false">
    </CustomProductModal>

    <!-- 퀸트에서 등록한 상품 수정 모달 -->
    <portfolio-edit-modal
      v-if="showMyProductModal" 
      @close="showMyProductModal=false"
      :modalData="modalData">
      <h3>모달 창 제목</h3>
    </portfolio-edit-modal>

    <!-- 사용자 정의 상품 수정 모달 -->
    <CustomProductModal 
      v-if="showCustomProductEditModal" 
      @close="showCustomProductEditModal=false"
      :customDto="modalData">
    </CustomProductModal>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import Navbar from '@/components/Navbar.vue'
import PortfolioEditItem from '@/components/PortfolioEditItem.vue'
import CustomProductModal from '@/components/CustomProductModal.vue'
import CustomProductItem from '@/components/CustomProductItem.vue'
import PortfolioEditModal from '@/components/PortfolioEditModal.vue'


export default {
  name: 'PortfolioEditView',
  components: { Navbar, PortfolioEditItem, CustomProductModal, CustomProductItem, PortfolioEditModal },
  computed: {
    ...mapGetters(['portfolio', 'customProducts'])
  },
  methods: {
    ...mapActions(['editPortfolio', 'addProductToPortfolio', 'fetchProductsByText', 'deletePortfolio']),
    openMyProductModal(payload) {
      this.modalData = payload,
      this.showMyProductModal = true
    },
    openCustomProductModal() {
      this.showCustomProductModal = true
    },
    openCustomProductEditModal(payload) {
      this.modalData = payload
      this.showCustomProductEditModal = true
    },
  },
  data() {
    return {
      text: '',
      showMyProductModal: false,
      showCustomProductModal: false,
      showCustomProductEditModal: false,
      modalData: null
    }
  }
}
</script>

<style>
@import '@/assets/css/home.css';
@import '@/assets/css/product.css';
@import '@/assets/css/table.css';

@font-face {
  font-family: 'NanumSquareRound';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_two@1.0/NanumSquareRound.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}
</style>