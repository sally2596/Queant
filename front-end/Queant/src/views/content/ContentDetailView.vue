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
        <router-link v-if="modifyAvailable"
          :to="{ name: 'contentEdit', params: { contentId } }"><button class="btn btn-sm btn-outline-secondary">수정</button></router-link>
        <button type="button" class="btn btn-sm btn-outline-secondary" @click="remove(contentId)">삭제</button>
      </div>
    </div>
  </section>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import Navbar from '@/components/Navbar.vue'
import router from '@/router'

export default {
  name: 'ContentDetailView',
  components: { Navbar },
  computed: {
    ...mapGetters(['content', 'contents', 'userInfo', 'isAdmin'])
  },
  data() {
    return {
      contentId: this.$route.params.contentId,
      modifyAvailable: false
    }
  },
  methods: {
    ...mapActions(['getContent', 'deleteContent', 'editContent']),
		remove(id){
			this.deleteContent(id);
			alert("컨텐츠가 삭제되었습니다.");
			this.$router.push({ name: 'contents' });
		}
  },
  created() {
    this.getContent(this.contentId)
  },
  updated () {
    if (this.content.member_id === this.userInfo.name) {
      this.modifyAvailable = true;
    }
  }
}
</script>

<style scoped>
@import '../../assets/css/home.css';
</style>