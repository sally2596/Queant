<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">'{{this.$route.params.keyword}}' 상품 검색 결과</h1>
  </header>
</template>
<script>
import Navbar from './Navbar.vue';

export default {
    name: "productSearching",
    props: ["keyword"],
    components: { Navbar },
    data() {
        return {};
    },
    methods: {},
}
</script>
<style>
@import '../assets/css/home.css';
</style>