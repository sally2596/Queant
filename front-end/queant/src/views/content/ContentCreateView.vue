<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">컨텐츠 작성</h1>
  </header>
  <section id="contents-create-section" class="text-center container">
    <div class="input-group mb-3">
      <span class="input-group-text" id="inputGroup-sizing-default">제목</span>
      <input required v-model="content.title" type="text" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
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
    ...mapGetters(['content', 'userInfo']),
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
			content: {
				title: '',
				memberId: '',
				content: '',
			}
    }
  },
  methods: {
		...mapActions(['editContent']),
		write() {
      this.content.content = this.editorData.replace('"','㉾');
      this.content.memberId = this.userInfo.name;
			this.editContent(this.content);
			alert("글이 등록되었습니다.");
		},
  }
}
</script>

<style>
@import '../../assets/css/content.css';
</style>