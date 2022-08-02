<template>
  <div>

  </div>
</template>

<script>
export default {
  name: 'BankInfoDetailView',
  created() {
    this.fetchBank(this.$route.params.bankId)
  }
}
</script>

<style>

</style>