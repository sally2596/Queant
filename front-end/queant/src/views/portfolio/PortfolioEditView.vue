<template>
  <Navbar/>
  <div>
    <h1>PortfolioEditView</h1>
    <h1>내 포트폴리오 관리하기</h1>

    <input 
      type="text"
      placeholder="Queant에서 상품찾기"
      v-model="text"
      @keyup.enter="fetchProductsByText(text)">
    <button class="searching" @click="fetchProductsByText(text)"><i class="fa-solid fa-magnifying-glass"></i></button>

    <button class="btn btn-outline-success btn-sm mx-3" @click="openCustomProductModal()">사용자 정의 상품 추가</button>

    <h3>퀸트에서 등록한 상품목록</h3>
    <table>
      <thead>
        <tr>
          <th>기관</th>
          <th>이름</th>
          <th>납입금액</th>
          <th>가입일</th>
          <th>만기일</th>
          <th>예금/적금</th>
          <th>적용금리</th>
        </tr>
      </thead>
      <tbody
        v-for="myProduct in portfolio"
        :key="myProduct">
        <td>
          <img :src="myProduct.product.picture" :alt="myProduct.product.name">
        </td>
        <td>
          {{ myProduct.product.name }}
        </td>
        <td>
          {{ myProduct.amount }}원
        </td>
        <td>
          {{ myProduct.start_date }}
        </td>
        <td>
          {{ myProduct.end_date }}
        </td>
        <td>
          {{ myProduct.product.deposit }}
        </td>
        <td>
          {{ myProduct.option.base_rate }}%
        </td>
      <button class="btn btn-outline-success btn-sm mx-2" @click="openMyProductModal(myProduct)">수정</button>
      <button class="btn btn-outline-danger btn-sm mx-2" @click="deletePortfolio(myProduct.portfolio_id)">삭제</button>
      </tbody>
    </table>

    <h3>내가 직접 입력한 상품들</h3>
    <table>
      <thead>
        <tr>
          <th>기관</th>
          <th>이름</th>
          <th>납입금액</th>
          <th>가입일</th>
          <th>만기일</th>
          <th>예금/적금</th>
          <th>적용금리</th>
        </tr>
      </thead><br>
      
      <tbody
        v-for="customProduct in customProducts"
        :key="customProduct">
        <td>
          <input
            type="text"
            v-model="customProduct.institution_name"
            disabled>
        </td>
        <td>
          <input 
            type="text"
            v-model="customProduct.product_name"
            disabled>
        </td>
        <td>
          <input 
            type="text"
            v-model="customProduct.amount"
            disabled>
        </td>
        <td>
          <input 
            type="date"
            v-model="customProduct.start_date"
            disabled>
        </td>
        <td>
          <input 
            type="date"
            v-model="customProduct.end_date"
            disabled>
        </td>
        <td>
          <input 
            type="text"
            v-model="customProduct.deposit"
            disabled>
        </td>
        <td>
          <input 
            type="text"
            v-model="customProduct.base_rate"
            disabled>
        </td>
        <button class="btn btn-outline-success btn-sm mx-2" @click="openCustomProductEditModal(customProduct)">수정</button>
        <button class="btn btn-outline-danger btn-sm mx-2" @click="deleteCustomProduct(customProduct.product_id)">삭제</button>
      </tbody>
    </table>

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

</style>