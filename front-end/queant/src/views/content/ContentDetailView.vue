<template>
  <Navbar></Navbar>
  
  <header id="title-div">
    <h1 class="title" id="title">{{content.title}}</h1>
  </header>
  <div>
    <div v-html=content.content></div>
  </div>
  <button type="button" class="btn btn-sm btn-outline-secondary" @click="deleteContent(content.content_id)">삭제</button>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import Navbar from '@/components/Navbar.vue'

export default {
  name: 'ContentDetailView',
  components: { Navbar },
  computed: {
    ...mapGetters(['content', 'contents'])
  },
  methods: {
    ...mapActions(['getContent', 'deleteContent'])
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