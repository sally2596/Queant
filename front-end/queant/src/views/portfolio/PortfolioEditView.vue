<template>
  <Navbar/>
  <div>
    <h1>PortfolioEditView</h1>
    <h1>내 포트폴리오 관리하기</h1>

    <input 
      type="text"
      placeholder="QueÆnt에서 상품찾기"
      v-model="text"
      @keyup.enter="fetchProductsByText(text)">
    <button class="searching" @click="fetchProductsByText(text)"><i class="fa-solid fa-magnifying-glass"></i></button>

    <button class="btn btn-outline-success btn-sm mx-3" @click="openCustomProductModal()">직접 입력해서 추가하기</button>

    <div class="container">
    <h1>퀸트에서 등록한 상품들</h1>
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
            <td class="d-flex" data-th="Supplier Code">
              <div
                v-for="char in myProduct.product.picture.slice(53)"
                :key=char>
                <p v-if="char !== '.' && char !== 'p' && char !== 'n' && char !== 'g'">{{ char }}</p>
              </div>
            </td>
            <td data-th="Supplier Name">
              <p class="text-center">{{ myProduct.product.name }}</p>
            </td>
            <td data-th="Invoice Number">
              {{ String(myProduct.amount).replace(/\B(?=(\d{3})+(?!\d))/g, ',') }}원
            </td>
            <td data-th="Invoice Date">
              {{ myProduct.start_date }} ~ {{ myProduct.end_date }}
            </td>
            <td data-th="Due Date">
              <p v-if="myProduct.product.deposit">예금</p>
              <p v-else>적금</p>
            </td>
            <td data-th="Net Amount">
              {{ myProduct.option.high_base_rate }}%
            </td>
            <td>
              <button class="btn btn-outline-success btn-sm mx-1" @click="openMyProductModal(myProduct)">수정</button>
              <button class="btn btn-outline-danger btn-sm mx-1" @click="deletePortfolio(myProduct.portfolio_id)">삭제</button>
            </td>
          </tr>
        </tbody>
      </table>
      <!-- <h3>퀸트에서 등록한 상품</h3> -->
    </div>

    <div class="container my-5">
    <h1>내가 직접 입력한 상품들</h1>
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
            <td data-th="Supplier Code">
              <p>{{ customProduct.institution_name }}</p>
            </td>
            <td data-th="Supplier Name">
              <p class="text-center">{{ customProduct.product_name }}</p>
            </td>
            <td data-th="Invoice Number">
              {{ String(customProduct.amount).replace(/\B(?=(\d{3})+(?!\d))/g, ',') }}원
            </td>
            <td data-th="Invoice Date">
              {{ customProduct.start_date }} ~ {{ customProduct.end_date }}
            </td>
            <td data-th="Due Date">
              <p v-if="customProduct.deposit">예금</p>
              <p v-else>적금</p>
            </td>
            <td data-th="Net Amount">
              {{ customProduct.base_rate }}%
            </td>
            <td>
              <button class="btn btn-outline-success btn-sm mx-1" @click="openCustomProductEditModal(customProduct)">수정</button>
              <button class="btn btn-outline-danger btn-sm mx-1" @click="deleteCustomProduct(customProduct.product_id)">삭제</button>
            </td>
          </tr>
        </tbody>
      </table>
      <!-- <h3>퀸트에서 등록한 상품</h3> -->
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

@import 'https://fonts.googleapis.com/css?family=Open+Sans:600,700';

* {font-family: 'Open Sans', sans-serif;}

.rwd-table {
  margin: auto;
  min-width: 300px;
  max-width: 100%;
  border-collapse: collapse;
}

.rwd-table tr:first-child {
  border-top: none;
  background: #3cb371;
  color: #fff;
}

.rwd-table tr {
  border-top: 1px solid #ddd;
  border-bottom: 1px solid #ddd;
  background-color: #f5f9fc;
}

.rwd-table tr:nth-child(odd):not(:first-child) {
  background-color: #ebf3f9;
}

.rwd-table th {
  display: none;
}

.rwd-table td {
  display: block;
}

.rwd-table td:first-child {
  margin-top: .5em;
}

.rwd-table td:last-child {
  margin-bottom: .5em;
}

.rwd-table td:before {
  content: attr(data-th) ": ";
  font-weight: bold;
  width: 120px;
  display: inline-block;
  color: #000;
}

.rwd-table th,
.rwd-table td {
  text-align: left;
}

.rwd-table {
  color: #333;
  border-radius: .4em;
  overflow: hidden;
}

.rwd-table tr {
  border-color: #bfbfbf;
}

.rwd-table th,
.rwd-table td {
  padding: .5em 1em;
}
@media screen and (max-width: 601px) {
  .rwd-table tr:nth-child(2) {
    border-top: none;
  }
}
@media screen and (min-width: 600px) {
  .rwd-table tr:hover:not(:first-child) {
    background-color: #d8e7f3;
  }
  .rwd-table td:before {
    display: none;
  }
  .rwd-table th,
  .rwd-table td {
    display: table-cell;
    padding: .25em .5em;
  }
  .rwd-table th:first-child,
  .rwd-table td:first-child {
    padding-left: 0;
  }
  .rwd-table th:last-child,
  .rwd-table td:last-child {
    padding-right: 0;
  }
  .rwd-table th,
  .rwd-table td {
    padding: 1em !important;
  }
}


/* THE END OF THE IMPORTANT STUFF */

/* Basic Styling */
/* body { */
/* background: #4B79A1;
background: -webkit-linear-gradient(to left, #4B79A1 , #283E51);
background: linear-gradient(to left, #4B79A1 , #283E51);         */
/* } */
h1 {
  text-align: center;
  font-size: 2.4em;
  /* color: #f2f2f2; */
}
/* .container { */
  /* display: block; */
  /* text-align: center; */
/* } */
h3 {
  display: inline-block;
  position: relative;
  text-align: center;
  font-size: 1.5em;
  /* color: #cecece; */
}
h3:before {
  content: "\25C0";
  position: absolute;
  left: -50px;
  -webkit-animation: leftRight 2s linear infinite;
  animation: leftRight 2s linear infinite;
}
h3:after {
  content: "\25b6";
  position: absolute;
  right: -50px;
  -webkit-animation: leftRight 2s linear infinite reverse;
  animation: leftRight 2s linear infinite reverse;
}
@-webkit-keyframes leftRight {
  0%    { -webkit-transform: translateX(0)}
  25%   { -webkit-transform: translateX(-10px)}
  75%   { -webkit-transform: translateX(10px)}
  100%  { -webkit-transform: translateX(0)}
}
@keyframes leftRight {
  0%    { transform: translateX(0)}
  25%   { transform: translateX(-10px)}
  75%   { transform: translateX(10px)}
  100%  { transform: translateX(0)}
}

/*
    Don't look at this last part. It's unnecessary. I was just playing with pixel gradients... Don't judge.
*/
/*
@media screen and (max-width: 601px) {
  .rwd-table tr {
    background-image: -webkit-linear-gradient(left, #428bca 137px, #f5f9fc 1px, #f5f9fc 100%);
    background-image: -moz-linear-gradient(left, #428bca 137px, #f5f9fc 1px, #f5f9fc 100%);
    background-image: -o-linear-gradient(left, #428bca 137px, #f5f9fc 1px, #f5f9fc 100%);
    background-image: -ms-linear-gradient(left, #428bca 137px, #f5f9fc 1px, #f5f9fc 100%);
    background-image: linear-gradient(left, #428bca 137px, #f5f9fc 1px, #f5f9fc 100%);
  }
  .rwd-table tr:nth-child(odd) {
    background-image: -webkit-linear-gradient(left, #428bca 137px, #ebf3f9 1px, #ebf3f9 100%);
    background-image: -moz-linear-gradient(left, #428bca 137px, #ebf3f9 1px, #ebf3f9 100%);
    background-image: -o-linear-gradient(left, #428bca 137px, #ebf3f9 1px, #ebf3f9 100%);
    background-image: -ms-linear-gradient(left, #428bca 137px, #ebf3f9 1px, #ebf3f9 100%);
    background-image: linear-gradient(left, #428bca 137px, #ebf3f9 1px, #ebf3f9 100%);
  }
}*/
</style>