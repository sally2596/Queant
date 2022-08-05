<template>
  <Navbar/>
  <header id="title-div">
    <h1 class="title" id="title">컨텐츠 작성</h1>
  </header>
  <main>
  <section class="py-5 text-center container">
    <div class="row py-lg-5">
      <input v-model="title" placeholder="제목을 입력하세요.">
    </div>
    <br>
    <div class="row py-lg-5">
      <QuillEditor theme="snow" :options="options"/>
    </div>
    <br>
    <div class="col-lg-6 col-md-8 mx-auto">
      <button class="btn btn-danger my-2">업로드</button>
      <button class="btn btn-primary my-2" @click="write">작성</button>
      <button class="btn btn-primary my-2">수정</button>
    </div>
  </section>
  </main>
</template>

<script>
import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css';

export default {
  name: "ContentCreateView",
  components: {
    QuillEditor
  },
  data() {
    return {
      title: '',
      image: [],
      options: {
        modules: {
          toolbar: [[{ 'size': ['small', false, 'large', 'huge'] }],
          ['bold', 'italic', 'underline', 'strike'],
          ['blockquote', 'code-block'],
          ['image', 'video']]
        },
        placeholder: '내용을 입력하세요.',
        theme: 'snow'
      }
    }
  },
  methods: {
    write() {
      
      alert();
      //대충 store에서 함수 받아와서 this.title, this.content, this.image 등등 넣어본다는 내용
    }
  }
}
</script>

<style>

</style>