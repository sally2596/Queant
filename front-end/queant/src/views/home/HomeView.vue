<template>
    <Navbar/> 
    <div class="home">
        <h1><strong>Save Your Money, Safely.</strong></h1>
    </div>
    <div class="home-recommend-product">
      <div class="square">

      </div>
      <div class="square">

      </div>
    </div>
</template>

<script>
import Navbar from '@/components/Navbar.vue'
export default {
  name: 'HomeView',
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