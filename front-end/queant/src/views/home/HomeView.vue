<template>
    <Navbar/> 
  <div class="home">
      <h1><strong>Save Your Money, Safely.</strong></h1>
  </div>
  <div class="news">
    <a href=""><img><p class="text-none">News 1</p></a>
    
    <a href=""><button></button><p>News 2</p></a>
    
    <a href=""><button></button><p>News 3</p></a>
    
    <a href=""><button></button><p>News 4</p></a>

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