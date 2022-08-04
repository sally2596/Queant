<template>
    <Navbar/> 
    <section id="hero" class="d-flex justify-cntent-center align-items-center">
      <div id="heroCarousel" class="container carousel carousel-fade" data-bs-ride="carousel" data-bs-interval="5000">
        <div class="carousel-item active">
          <div class="carousel-container">
            <h2>Save Your Moeny, Safely. QueÆnt</h2>
            <router-link :to="{ name: 'portfolio' }" class="btn-get-started">내 포트폴리오 만들기</router-link>
          </div>
        </div>

      </div>
    </section><!-- End Hero -->
</template>

<script>
import Navbar from '@/components/Navbar.vue'

export default {
  name: 'HomeView',
  components : { Navbar },
  beforeCreate: function() {
    document.body.className = 'home_body'
  }
}
</script>

<style>
</style>