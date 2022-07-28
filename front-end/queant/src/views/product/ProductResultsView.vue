<template>
  <div class="container">
    <div class="row">
      <div
        v-for="product in products"
        :key="product.id" 
        class="col-4">
        <img :src="product.imgpath">
        {{ product.bank }}
        {{ product.name }}
        {{ product.eza }}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ProductResultsView',
  data() {
    return {
      bestPicksLength: 3,
      products: [
        {
          bank: '하나은행',
          name: '하나적금',
          imgpath: 'https://m.kebhana.com/cont/comm/img/common/img_metaOg01.jpg',
          eza: 5,
          id: 1
        },
        {
          bank: '신한은행',
          name: '신한적금',
          imgpath: 'https://blog.kakaocdn.net/dn/Iz97w/btqyXx4yZhO/3myJG9cf8ioKZ67PuYfeIK/img.jpg',
          eza: 3,
          id: 2
        },
        {
          bank: '국민은행',
          name: '국민적금',
          imgpath: 'https://play-lh.googleusercontent.com/BRgz8ZiUGabAoSIG0Cy7J22VO2E_m4liOU3TJtuQD7iRaxCv0c8cOww_oMopzi9isKTr',
          eza: 1,
          id: 3
        },
        {
          bank: '우리은행',
          name: '우리적금',
          imgpath: 'https://pbs.twimg.com/profile_images/1430767071667789825/NJfqeCLr_400x400.jpg',
          eza: 6,
          id: 4
        },
        {
          bank: '기업은행',
          name: '기업적금',
          imgpath: 'https://yt3.ggpht.com/zZg2axjXO9n1-KYsb-m3SQUVMHOPqKVRayyEmFgeRV1esvDOVC9Zhl1HwYi1TDniq7SjWk5s=s900-c-k-c0x00ffffff-no-rj',
          eza: 10,
          id: 5
        }
      ]
    }
  }
}
</script>

<style>
img {
  width: 50px;
  height: 50px;
}
</style>