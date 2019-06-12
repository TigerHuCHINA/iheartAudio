<template>
  <div>
    <my-header></my-header>
    <main>
      <section class="flex-row content" v-if="user_name !== 'admin'">
        <div>
          <img :src="imgUrl" class="content-img"/>
        </div>
        <div class="content-text">
          <p class="title">{{resource.name}}</p>
          <div class="content-text-item">
            <p class="item-title">价格：</p>
            <p class="item-text content-price">￥{{resource.price}}</p>
          </div>
          <div class="content-text-item">
            <p class="item-title">产地：</p>
            <p class="item-text">{{resource.place}}</p>
          </div>
          <div class="content-text-item">
            <p class="item-title">类型：</p>
            <p class="item-text">{{resource.type}}</p>
          </div>
          <div class="content-text-item">
            <p class="item-title">制作人：</p>
            <p class="item-text">{{resource.producer}}</p>
          </div>
          <div class="content-text-item">
            <p class="item-title">歌手：</p>
            <p class="item-text">{{resource.singer}}</p>
          </div>
          <div class="content-text-item">
            <p class="item-title">日期：</p>
            <p class="item-text">{{resource.date}}</p>
          </div>
          <div class="flex-row">
            <button class="btn" @click="purchase">立即购买</button>
          </div>
        </div>
      </section>
      <section class="flex-row content" v-else>
        <div>
          <img :src="imgUrl" class="content-img"/>
        </div>
        <div class="content-text">
          <div class="content-text-item">
            <p class="item-title">名称：</p>
            <input v-model="resource.name" type="text" class="input-box">
          </div>
          <div class="content-text-item">
            <p class="item-title">价格：</p>
            <input v-model="resource.price" type="text" class="input-box">
          </div>
          <div class="content-text-item">
            <p class="item-title">产地：</p>
            <input v-model="resource.place" type="text" class="input-box">
          </div>
          <div class="content-text-item">
            <p class="item-title">类型：</p>
            <input v-model="resource.type" type="text" class="input-box">
          </div>
          <div class="content-text-item">
            <p class="item-title">制作人：</p>
            <input v-model="resource.producer" type="text" class="input-box">
          </div>
          <div class="content-text-item">
            <p class="item-title">歌手：</p>
            <input v-model="resource.singer" type="text" class="input-box">
          </div>
          <div class="content-text-item">
            <p class="item-title">日期：</p>
            <input v-model="resource.date" type="text" class="input-box">
          </div>
          <div class="flex-row">
            <button class="btn" @click="modifyProductInfo">确认修改</button>
          </div>
        </div>
      </section>
    </main>
  </div>
</template>

<script>
import myHeader from '@/components/home/Header'
import axios from 'axios'
export default {
  name: 'detail',
  components: {
    myHeader
  },
  data () {
    return {
      userLogin: false,
      user_name: '',
      isAdmin: false,
      imgUrl: '',
      resource: {
        type: '',
        name: '',
        price: '',
        place: '',
        producer: '',
        singer: '',
        date: '',
        description: ''
      },
      productId: ''
    }
  },
  methods: {
    purchase () {
      if (this.user_name === '') {
        alert('请先登录！')
      } else {
        if (sessionStorage.getItem('cartList') == null) {
          console.log('为空')
          let cartlist = []
          cartlist.push(this.productId)
          sessionStorage.setItem('cartList', JSON.stringify(cartlist))
        } else {
          console.log('不为空', sessionStorage.getItem('cartList'))
          let cartlist = JSON.parse(sessionStorage.getItem('cartList'))
          var exist = false
          for (var x in cartlist) {
            if (cartlist[x] === this.productId) {
              exist = true
            }
          }
          if (!exist) {
            cartlist.push(this.productId)
          }
          sessionStorage.setItem('cartList', JSON.stringify(cartlist))
        }
        this.$router.push({path: '/cart'})
      }
    },
    modifyProductInfo () {
      let param = {
        name: this.resource.name,
        price: this.resource.price,
        place: this.resource.place,
        type: this.resource.type,
        producer: this.resource.producer,
        singer: this.resource.singer,
        date: this.resource.date,
        id: this.productId
      }
      axios.get('http://localhost:8080/modifyProduct', {params: param}).then(res => {
        if (res.data.code === 200) {
          alert('修改成功')
        }
      })
    }
  },
  created () {
    this.productId = this.$route.params.id
    this.user_name = JSON.parse(sessionStorage.getItem('user')).user_name
    let param = {
      id: this.productId
    }
    axios.get('http://localhost:8080/product', {params: param}).then(res => {
      if (res.data.code === 200) {
        this.resource = res.data.result
        this.imgUrl = require('@/assets/' + this.resource.description + '.jpg')
      }
    })
  }
}
</script>

<style scoped>
  .title{
    font-size: 30px;
    font-weight: 700;
    line-height: 35px;
    color: #3C3C3C;
    margin-bottom: 30px;
  }
  .flex-row{
    display: flex;
    flex-direction: row;
  }
  .content{
    margin-left: 200px;
    margin-top: 50px;
    margin-bottom: 50px;
  }
  .content-img{
    width: 400px;
    height: 400px;
  }
  .input-box{
    width: 300px;
    height: 30px;
    font-size: 16px;
    border-radius: 4px;
    border: 1px solid #bfcbd9;
    box-sizing: border-box;
    color: #1f2d3d;
    padding: 3px 10px
  }
  .content-text{
    margin-left: 80px;
  }
  .content-text-item{
    margin-bottom: 30px;
    display: flex;
    flex-direction: row;
    align-items: center;
  }
  .content-price{
    color: #f40;
    font-weight: 700;
    font-size: 35px;
  }
  .item-title{
    width: 80px;
    font-size: 16px;
    text-align: right;
    margin-right: 20px;
  }
  .item-text{
    width: 300px;
  }
  .btn{
    margin: 30px auto 0;
    color: white;
    background: rgb(46, 130, 255);
    border: 0;
    border-radius: 3px;
    display: block;
    height: 40px;
    line-height: 40px;
    width: 100px;
    font-size: 16px;
    font-weight: 500;
    cursor: pointer;
    appearance: none;
  }
</style>
