<template>
  <Navbar/>
  
  <header id="title-div">
    <h1 class="title" id="title">{{content.title}}</h1>
  </header>
  <section class="content">
    <div class="content_content">
      <div>
        <div v-html=content.content></div>
      </div>
      <div v-if="isAdmin">
        <router-link
          :to="{ name: 'contentEdit', params: { contentId } }"><button class="btn btn-sm btn-outline-secondary">수정</button></router-link>
        <button type="button" class="btn btn-sm btn-outline-secondary" @click="deleteContent(contentId)">삭제</button>
      </div>
    </div>
  </section>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import Navbar from '@/components/Navbar.vue'

export default {
  name: 'ContentDetailView',
  components: { Navbar },
  computed: {
    ...mapGetters(['content', 'contents', 'userInfo', 'isAdmin'])
  },
  data() {
    return {
      contentId: this.$route.params.contentId
    }
  },
  methods: {
    ...mapActions(['getContent', 'deleteContent', 'editContent'])
  },
  created() {
    this.getContent(this.contentId)
  }
}
</script>

<style scoped>
@import '../../assets/css/home.css';
</style>