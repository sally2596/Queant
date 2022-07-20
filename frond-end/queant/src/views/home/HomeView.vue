<template>
  <div>
    <Navbar/>
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
        document.body.className = 'home';
    },
}
</script>

<style>
</style>