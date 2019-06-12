<template>
  <div>
    <my-header @logIn="handleLogIn" @logout="handleLogout"></my-header>
    <main>
      <div class="headline">
        <home-swiper :list="swiperList" class="home-swiper" v-if="hackReset"></home-swiper>
        <recommend :list="recommendList" class="home-recommend" v-if="hackReset"></recommend>
      </div>
    </main>
  </div>
</template>

<script>
import myHeader from '@/components/home/Header'
import HomeSwiper from '@/components/home/Swiper'
import Recommend from '@/components/home/Recommend'

export default {
  name: 'Home',
  components: {
    myHeader,
    HomeSwiper,
    Recommend
  },
  data () {
    return {
      userLogIn: false,
      user_name: '',
      hackReset: true,
      swiperList: [
        {
          productId: '0002',
          productName: 'Fearless',
          imgUrl: require('@/assets/fearless.jpg')
        },
        {
          productId: '0003',
          productName: 'Speak Now',
          imgUrl: require('@/assets/speak_now.jpg')
        },
        {
          productId: '0004',
          productName: 'Red',
          imgUrl: require('@/assets/red.jpg')
        },
        {
          productId: '0005',
          productName: '1989',
          imgUrl: require('@/assets/1989.jpg')
        },
        {
          productId: '0006',
          productName: 'reputation',
          imgUrl: require('@/assets/reputation.jpg')
        }],
      recommendList: [
        {
          productId: '0001',
          productName: 'Taylor Swift',
          imgUrl: require('@/assets/taylor_swift.jpg')
        },
        {
          productId: '0101',
          productName: 'Wild',
          imgUrl: require('@/assets/wild.jpg')
        },
        {
          productId: '0103',
          productName: 'Bloom',
          imgUrl: require('@/assets/bloom.jpg')
        },
        {
          productId: '0102',
          productName: 'Blue Neighbourhood',
          imgUrl: require('@/assets/blue_neighbourhood.jpg')
        },
        {
          productId: '0201',
          productName: 'Prism',
          imgUrl: require('@/assets/prism.jpg')
        },
        {
          productId: '0301',
          productName: 'Sweetener',
          imgUrl: require('@/assets/sweetener.jpg')
        },
        {
          productId: '0302',
          productName: 'thank u,next',
          imgUrl: require('@/assets/thank_u,next.jpg')
        },
        {
          productId: '0401',
          productName: '21',
          imgUrl: require('@/assets/21.jpg')
        }
      ]
    }
  },
  methods: {
    handleLogIn () {
      this.user_name = JSON.parse(sessionStorage.getItem('user')).user_name
      this.hackReset = false
      this.$nextTick(() => {
        this.hackReset = true
      })
    },
    handleLogout () {
      this.hackReset = false
      this.$nextTick(() => {
        this.hackReset = true
      })
    }
  },
  created () {
    var user = sessionStorage.getItem('user')
    if (user) {
      this.user_name = JSON.parse(user).user_name
    }
  }
}
</script>

<style scoped>
  .headline{
    width: 1000px;
    margin: 50px auto 0;
    display: flex;
    flex-direction: row;
    align-items: center;
  }
  .home-recommend{
    margin-left: -6px;
  }
</style>
