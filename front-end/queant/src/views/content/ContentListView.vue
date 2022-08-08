<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">컨텐츠</h1>
  </header>
  <main>

  <section class="py-5 text-center container">
    <div class="row py-lg-5">
      <div class="col-lg-6 col-md-8 mx-auto">
        <h1 class="fw-light">자산 관리의 핵심, Queant</h1>
        <p class="lead text-muted">Queant가 당신의 자산을 안전하고 확실하게 불려드립니다.</p>
        <div v-if="isAdmin">
          <router-link  class="btn btn-secondary my-2" :to="{ name: 'contentCreate' }">기사 작성하기</router-link>
        </div>
        
      </div>
    </div>
  </section>

  <div class="album py-5 bg-light">
    <div class="container">

      <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
        <div class="col" data-aos="fade-up" v-for="content in contents">
          <div class="card shadow-sm">
            <div class="card-body">
              <p class="card-text">{{content.content}}</p>
              <div class="d-flex justify-content-between align-items-center">
                <div class="btn-group">
                  <router-link class="btn btn-sm btn-outline-secondary"
                :to="{ name: 'contentDetail' , params: { contentId: content.content_id }}">보기</router-link>
                  <button type="button" class="btn btn-sm btn-outline-secondary" @click="test">삭제</button>
                </div>
                <small class="text-muted">9 mins</small>
              </div>  
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

</main>
</template>
<script>
import Navbar from "@/components/Navbar.vue"
import { mapGetters, mapActions } from 'vuex'

export default {
  name: 'ContentListView',
  components : { Navbar },
  computed: {
    ...mapGetters(['userInfo', 'isAdmin', 'contents']),
  },
  beforeCreate: function() {
    document.body.className = 'home_body'
  },
  methods : {
    ...mapActions(['getContentList', 'getContent', 'deleteContent']),
    test() {
      console.log(this.content.content_id)
    }
  },
  created() {
    this.getContentList();
  },
  data() {
  }
}
</script>
<style>
</style>