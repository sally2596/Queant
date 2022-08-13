<template>
  <Navbar></Navbar>
  
  <header id="title-div">
    <h1 class="title" id="title">{{content.title}}</h1>
  </header>
  <section class="content">
    <div class="content_content">
      <div>
        <div v-html=content.content></div>
      </div>
      <router-link type="button" class="btn btn-sm btn-outline-secondary" v-if="isAdmin"
      :to="{ name: 'contentEdit' , params: { contentId: content.content_id }}">수정</router-link>
      <button type="button" class="btn btn-sm btn-outline-secondary" v-if="isAdmin" @click="deleteContent(content.content_id)">삭제</button>
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
  methods: {
    ...mapActions(['getContent', 'deleteContent', 'editContent'])
  },
  created() {
    this.getContent(this.$route.params.contentId);
  },
  data() {
  }
}
</script>

<style>
@import '../../assets/css/home.css';
</style>