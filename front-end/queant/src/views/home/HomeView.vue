<template>
    <Navbar/>
    <HomeComponents/>
</template>

<script>
import Navbar from '@/components/Navbar.vue'
import HomeComponents from '@/components/HomeComponents.vue'

export default {
  name: 'HomeView',
  components : {
    Navbar : Navbar,
    HomeComponents : HomeComponents
  },
  beforeCreate: function() {
    document.body.className = 'home_body'
  }
}
</script>

<style>
</style>