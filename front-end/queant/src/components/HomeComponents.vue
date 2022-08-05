<template>
    <div class="row" data-aos="fade-up" style="margin-bottom:10%;">
      <div class="col-md-5 image-box">
        <button @click="redirectArticleLink(news.article_link)">
          <img :src=news.img_link class="img-news-thumbnail" alt="">
        </button>
      </div>
      <div class="col-md-7 pt-4">
          <h3 style="cursor: pointer" @click="redirectArticleLink(news.article_link)">
            {{ news.title }}
          </h3>
        <p style="cursor: pointer" @click="redirectArticleLink(news.article_link)" class="fst-italic">
          {{ news.preview }}
        </p>
        <ul>
          <li><i class="bi bi-check"></i> {{ news.writer }}</li>
        </ul>
      </div>
    </div>

</template>
<script>
export default {
  name: 'HomeComponents',
  props: {
    news: Object
  },
  methods: {
    redirectArticleLink(link) {
      window.open(link)
    }
  }
}
</script>