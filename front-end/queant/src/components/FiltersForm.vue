<template>

  <!-- 사용자가 검색한 조건 -->
  <div>
    {{ filters }}
    <hr>
    {{ payload }}
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
        page: this.filters.page
      }
    }
  },
  watch: {
    payload: {
      deep: true,
      handler(value) {
        this.fetchProductsByDepositFilters(value)
      }
    }
  },
  methods: {
    ...mapActions(['fetchProductsByDepositFilters']),
    changePage(page) {
      this.payload.page = page
    }
  }
}
</script>

<style>

</style>