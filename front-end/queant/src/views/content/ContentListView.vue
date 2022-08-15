<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">컨텐츠</h1>
  </header>
  <main>

  <section class="py-5 text-center container" id="content-header-section">
    <div class="row py-lg-5">
      <div class="col-lg-6 col-md-8 mx-auto">
        <h1>자산 관리의 핵심, Queant</h1>
        <div v-if="isAdmin">
          <router-link  class="btn btn-secondary my-2" :to="{ name: 'contentCreate' }">기사 작성하기</router-link>
        </div>
        
      </div>
    </div>
  </section>

  <div class="album py-5">
    <div class="container p-0">

      <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
        <div class="col" data-aos="fade-up" v-for="content in contents" :key="content">
          <div class="card shadow-sm">
            <div class="card-body">
              <img :src=randomThumbnail() class="card-img-top" alt="..." style="height:300px;">
              <br><br>
              <h5 class="card-title">{{content.title}}</h5>
              <br><br>
              <div class="d-flex justify-content-between align-items-center">
                <div class="btn-group">
                  <router-link class="btn btn-sm btn-outline-secondary"
                :to="{ name: 'contentDetail' , params: { contentId: content.content_id }}">보기</router-link>
                  <button type="button" class="btn btn-sm btn-outline-secondary" @click="deleteContent(content.content_id)" v-if="isAdmin">삭제</button>
                </div>
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
import _ from 'lodash'
import thumbnail1 from '../../assets/image/thumbnail/1.jpg'
import thumbnail2 from '../../assets/image/thumbnail/2.jpg'
import thumbnail3 from '../../assets/image/thumbnail/3.jpg'
import thumbnail4 from '../../assets/image/thumbnail/4.jpg'
import thumbnail5 from '../../assets/image/thumbnail/5.jpg'
import thumbnail6 from '../../assets/image/thumbnail/6.jpg'
import thumbnail7 from '../../assets/image/thumbnail/7.jpg'
import thumbnail8 from '../../assets/image/thumbnail/8.jpg'
import thumbnail9 from '../../assets/image/thumbnail/9.jpg'
import thumbnail10 from '../../assets/image/thumbnail/10.jpg'


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
    ...mapActions(['getContentList', 'getContent', 'deleteContent', 'editContent']),
    randomThumbnail() {
      const idx = Math.floor(Math.random() * this.thumbnails.length)
      let thumbnail = this.thumbnails[idx]
      return thumbnail
    },
  },
  created() {
    this.getContentList();
  },
  data() {
    return {
      thumbnails : [
        thumbnail1,
        thumbnail2,
        thumbnail3,
        thumbnail4,
        thumbnail5,
        thumbnail6,
        thumbnail7,
        thumbnail8,
        thumbnail9,
        thumbnail10,
      ],
    }
  }
}
</script>
<style>
</style>