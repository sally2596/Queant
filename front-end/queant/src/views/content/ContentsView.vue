<template>
    <Navbar/>
</template>
<script>
import Navbar from "@/components/Navbar.vue";
export default {
  name: 'ContentsView',
  components : {
    Navbar : Navbar
  },
    beforeCreate: function() {
        document.body.className = 'home_body';
    },
}
</script>
<style>
</style>