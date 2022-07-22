<template>
  <div>
    <Navbar/>
  </div>
</template>

<script>
import Navbar from '@/components/Navbar.vue'
export default {
  name: 'ProductRecommendationView',
  components : {
    Navbar : Navbar
  },
    beforeCreate: function() {
        document.body.className = 'home_body';
    },
}
</script>

<style>
@import '../../assets/css/home.css';
</style>