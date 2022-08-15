<template>
  <Navbar/>
  <div class="container">
    <h1 class="title" id="title">'{{this.$route.params.text}}' 검색 결과</h1>
    <table class="rwd-table my-5">
      <tbody>
        <tr>
          <th class="text-center">은행</th>
          <th class="text-center">상품명</th>
          <th class="text-center">기본 금리</th>
          <th class="text-center">최소 가입기간</th>
          <th class="text-center">유형</th>
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
        </tr>
      </tbody>
    </table>
    <!-- <h3>퀸트에서 등록한 상품</h3> -->
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
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import Navbar from '@/components/Navbar.vue'

export default {
  name: 'ProductSearchResultView',
  components: { Navbar },
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
@import '@/assets/css/table.css';
</style>