<template>
  <div>
    <Navbar/>
  </div>
</template>

<script>
import Navbar from '@/components/Navbar.vue'
export default {
  name: 'ProductCartView',
  components : {
    Navbar : Navbar
  },
    beforeCreate: function() {
        document.body.className = 'home';
    },
}
</script>

<style>
@import '../../assets/css/home.css';
</style>