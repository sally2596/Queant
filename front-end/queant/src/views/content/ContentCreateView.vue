<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">컨텐츠 작성</h1>
  </header>
  <main>
  <section class="py-5 text-center container">
    <div class="row py-lg-5">
      <input v-model="content.title" placeholder="제목을 입력하세요.">
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
      <button class="btn btn-danger my-2">취소</button>
      <button class="btn btn-primary my-2" @click="write">작성</button>
      <button class="btn btn-primary my-2" @click="testbtn">수정</button>
    </div>
  </section>
  </main>
</template>

<script>
import ClassicEditor from '@ckeditor/ckeditor5-build-classic'
import CKEditor from '@ckeditor/ckeditor5-vue'

import spring from '@/api/spring'
import { mapActions, mapGetters } from 'vuex'

export default {
  name: "ContentCreateView",
	components: {
		ckeditor: CKEditor.component
	},
	computed: {
    ...mapGetters(['content'])
  },
  data() {
    return {
			editor: ClassicEditor,
			editorData: '<p>Content of the editor.</p>',
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
				memberId: 'test',
				content: '',
			}
    }
  },
  methods: {
		...mapActions(['editContent']),
		write() {
			this.editContent({},this.content);
			alert("글이 등록되었습니다.");
		},
		testbtn() {
			this.content.content = this.editorData;
			console.log(this.content);
		}
  }
}
</script>

<style>

</style>