import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/home/Home'
import Detail from '@/components/detail/detail'
import Cart from '@/components/cart/Cart'
import MyInfo from '@/components/myinfo/MyInfo'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/productDetail/:id',
      name: 'Detail',
      component: Detail
    },
    {
      path: '/cart',
      name: 'cart',
      component: Cart
    },
    {
      path: '/myinfo',
      name: 'myinfo',
      component: MyInfo
    }
  ]
})
