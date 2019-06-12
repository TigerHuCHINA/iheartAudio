<template>
  <div class="my-swiper-wrapper">
    <div class="my-swiper-swiper">
      <swiper :options="swiperOption" v-if="list.length">
        <swiper-slide v-for="item of list" v-bind:key="item.productId">
            <div class="swiper-image-wrapper" @click="goToDetail(item.productId)"><img class="swiper-image" :src="item.imgUrl"></div>
        </swiper-slide>
        <div class="swiper-pagination" slot="pagination"></div>
      </swiper>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HomeSwiper',
  props: ['list'],
  data () {
    return {
      user_name: '',
      swiperOption: {
        pagination: {
          el: '.swiper-pagination',
          clickable: true
        },
        loop: true,
        autoplay: {
          deley: 1000,
          disableOnInteraction: false
        },
        bulletClass: 'my-bullet',
        bulletActiveClass: 'my-bullet-active'
      }
    }
  },
  methods: {
    goToDetail (id) {
      if (this.user_name === '') {
        alert('请先登录')
      } else {
        console.log(this.user_name)
        this.$router.push('/productDetail/' + id)
      }
    }
  },
  created () {
    var user = sessionStorage.getItem('user')
    console.log(user)
    if (user) {
      this.user_name = JSON.parse(user).user_name
    }
    this.$forceUpdate()
  }
}
</script>

<style>
  .my-swiper-wrapper{
    width: 300px;
  }
  .my-swiper-swiper{
    display: block;
    position: relative;
    overflow: hidden;
    width: 100%;
    height: 0;
    padding-bottom: 100%;
  }
  .swiper-image-wrapper{
    cursor: pointer;
  }
  .swiper-image{
    width: 100%;
  }
  .swiper-pagination-bullet{
    width: 18px;
    height: 18px;
    background: url("../../assets/icons.png");
    background-position: -855px -790px;
    opacity: 1;
  }
  .swiper-pagination-bullet:hover{
    background-position: -919px -790px;
  }
  .swiper-pagination-bullet-active{
    background-position: -855px -727px;
  }
</style>
