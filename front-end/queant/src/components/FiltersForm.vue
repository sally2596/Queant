<template>
  <!-- 사용자가 검색한 조건 -->
  <div>
    <label>납입금</label>
    <input 
      type="text"
      v-model="payload.amount">

    <label for="">기간</label>
    <input
      type="text"
      v-model="payload.period">

    <button>재검색</button>
    <hr>
  </div>

  <!-- 페이지네이션 -->
  <div>
    <div
      v-for="page in totalPage"
      :key="page">
      <button @click="changePage(page)">{{ page }}</button>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
export default {
  name: 'FiltersForm',
  props: {
    filters: Object,
    totalPage: Number
  },
  computed: {
    ...mapGetters(['products'])
  },
  data() {
    return {
      payload: {
        amount: this.filters.amount,
        period: this.filters.period,
        isSimpleInterest: this.filters.is_simple_interest,
        bank: this.filters.bank,
        joinway: this.filters.joinway,
        conditions: this.filters.conditions,
        bankType: this.filters.bank_type,
        traitSet: this.filters.trait_set,
        isFixed: this.filters.isFixed,
        page: this.filters.page
      }
    }
  },
  watch: {
    payload: {
      deep: true,
      handler(value) {
        console.log(this.$route.name)
        if (this.$route.name === 'productDepositResult')
          this.fetchProductsByDepositFilters(value)
        else if (this.$route.name === 'productSavingResult')
          this.fetchProductsBySavingFilters(value)
      }
    }
  },
  methods: {
    ...mapActions(['fetchProductsByDepositFilters', 'fetchProductsBySavingFilters']),
    changePage(page) {
      this.payload.page = page
    }
  }
}
</script>

<style>

</style>