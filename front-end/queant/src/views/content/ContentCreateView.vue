<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">컨텐츠 작성</h1>
  </header>
  <section id="contents-create-section" class="text-center container">
    <div class="input-group mb-3">
      <span class="input-group-text" id="inputGroup-sizing-default">제목</span>
      <input required v-model="article.title" type="text" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
    </div>
      <ckeditor 
      :editor="editor" v-model="editorData" :config="editorConfig"
    />
    <div>
      <router-link :to="{name : 'contents'}" class="btn btn-danger my-2">취소</router-link>
      <button class="btn btn-primary my-2" @click="write">작성</button>
    </div>
  </section>
</template>

<script>
import ClassicEditor from '@ckeditor/ckeditor5-build-classic'
import CKEditor from '@ckeditor/ckeditor5-vue'
import Navbar from '@/components/Navbar.vue'
import spring from '@/api/spring'
import { mapActions, mapGetters } from 'vuex'

export default {
  name: "ContentCreateView",
	components: {
		ckeditor: CKEditor.component,
    Navbar
	},
	computed: {
    ...mapGetters(['userInfo']),
  },
  data() {
    return {
			editor: ClassicEditor,
			editorData: '',
			editorConfig: {
				// The configuration of the editor.
				height: '500px',
				language: 'ko',
				ckfinder: {
					uploadUrl: spring.contents.upload()
				}
			},
			article: {
				title: '',
				memberId: '',
				content: '',
			}
    }
  },
  methods: {
		...mapActions(['editContent']),
		write() {
      this.article.content = this.editorData.replaceAll('\"','㉾');
      this.article.memberId = this.userInfo.name;
			this.editContent(this.article);
			alert("글이 등록되었습니다.");
		},
  }
}
</script>

<style scoped>
@import '../../assets/css/content.css';
</style>