<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">컨텐츠 작성</h1>
  </header>
  <main>
  <section class="py-5 text-center container">
    <div class="row py-lg-5"> 
      <input v-model="newContent.title" placeholder="제목을 입력하세요.">
    </div>
    <br>
    <div>
      <ckeditor :editor="editor"
              v-model="editorData"
              :config="editorConfig"
    />
    </div>
    <br>
    <div class="col-lg-6 col-md-8 mx-auto">
      <router-link :to="{name : 'contents'}" class="btn btn-danger my-2">취소</router-link>
      <button class="btn btn-primary my-2" @click="write">작성</button>
      <button class="btn btn-primary my-2" @click="testbtn">수정</button>
    </div>
  </section>
  </main>
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
    Navbar: Navbar
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
			newContent: {
				title: '',
				memberId: this.userInfo?.name,
				content: '',
			}
    }
  },
  methods: {
		...mapActions(['editContent']),
		write() {
      this.content.content = this.editorData.replace('\"','\\"');
			this.editContent(this.content);
			alert("글이 등록되었습니다.");
		},
  }
}
</script>

<style>

</style>