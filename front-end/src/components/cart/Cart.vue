<template>
  <div class="wrapper">
    <my-header userLogIn="true" :user_name="user_name"></my-header>
    <main class="main">
      <div class="cart-content">
        <div class="title"><p class="title-text">订单详情</p></div>
        <div class="product">
          <div class="product-title">
            <p class="product-title-text">商品信息</p>
          </div>
          <table class="product-info">
            <thead>
            <tr style="border-bottom: 1px solid #DEDFDE">
              <th scope="col" width="260px">商品名称</th>
              <th scope="col">类型</th>
              <th scope="col">单价</th>
              <th scope="col">数量</th>
              <th scope="col">小计</th>
              <th scope="col">操作</th>
            </tr>
            </thead>
            <tbody v-for="(item,index) of cartList" :key="index" style="margin-top: 10px;">
              <tr v-if="item.exist" class="table-row">
                <td class="table-item table-item-name"><img :src="item.proInfo.imgUrl" class="item-img">{{item.proInfo.name}}</td>
                <td class="table-item">{{item.proInfo.type}}</td>
                <td class="table-item">￥{{item.proInfo.price}}</td>
                <td class="table-item" width="110px"><button @click="quantityMinus(item)">-</button> {{ item.quantity }} <button @click="quantityPlus(item)">+</button></td>
                <td class="table-item" width="110px">￥{{item.sum()}}</td>
                <td class="table-item-operation"><p @click="deleteItem(item)">删除</p></td>
              </tr>
            </tbody>
          </table>
          <div v-if="showNull" class="show-null">您的购物车还没有道具，赶快去挑选几个吧</div>
          <div class="submit-view">
            <span class="submit-payment">实付款:<span class="total-price">{{totalPrice}}</span>元</span>
            <span class="submit-btn" :disabled="showNull" :style="{backgroundColor:submitColor}"><div class="submit-btn-text" @click="submit">提交订单</div></span>
          </div>
        </div>
      </div>

    </main>
  </div>
</template>

<script>
import myHeader from '@/components/home/Header'
import axios from 'axios'
export default {
  name: 'Cart',
  components: {
    myHeader
  },
  data () {
    return {
      user_name: '',
      address: '',
      cartList: [],
      submitColor: '#f74a4a'
    }
  },
  methods: {
    deleteItem (item) {
      item.exist = false
      for (let i = 0; i < this.cartList.length; i++) {
        if (!this.cartList[i].exist) {
          let cart = JSON.parse(sessionStorage.getItem('cartList'))
          for (var x in cart) {
            if (cart[x] === this.cartList[i].proId) {
              cart.splice(cart.indexOf(cart[x]), 1)
            }
          }
          sessionStorage.setItem('cartList', JSON.stringify(cart))
          this.cartList.splice(i, 1)
        }
      }
      if (this.cartList.length === 0) {
        this.submitColor = 'grey'
      }
    },
    quantityMinus (item) {
      if (item.quantity > 1) {
        item.quantity--
      } else {
        alert('不能再减少了！')
      }
    },
    quantityPlus (item) {
      item.quantity++
    },
    submit () {
      let param1 = {
        user_name: this.user_name
      }
      console.log('按钮点击')
      axios.get('http://localhost:8080/getUserByUserName', {params: param1}).then(res => {
        console.log(res.data.code)
        if (res.data.code === 200) {
          console.log('获取成功')
          if (res.data.result.address == null) {
            alert('请在个人中心设置地址！')
            return
          }
          this.address = res.data.result.address === null ? '暂无' : res.data.result.address
          console.log(res)
          console.log('已获得信息')
          for (var item in this.cartList) {
            let param = {
              product_id: this.cartList[item].proId,
              quantity: this.cartList[item].quantity,
              price: this.cartList[item].proInfo.price,
              user_name: this.user_name,
              mysum: this.cartList[item].sum(),
              mydate: new Date().toDateString(),
              address: this.address,
              status: '未发货',
              product_name: this.cartList[item].proInfo.name
            }
            console.log('提交订单')
            axios.get('http://localhost:8080/submitOrder', {params: param}).then(res => {
            })
          }
          alert('提交成功')
          this.$router.replace('/myinfo')
          window.location.reload()
          sessionStorage.removeItem('cartList')
        }
      })
    }
  },
  computed: {
    showNull () {
      return this.cartList.length === 0
    },
    totalPrice () {
      let totalPrice = 0
      for (let i = 0; i < this.cartList.length; i++) {
        totalPrice += this.cartList[i].sum()
      }
      return totalPrice
    }
  },
  created () {
    var proList = []
    proList = JSON.parse(sessionStorage.getItem('cartList'))
    console.log(proList)
    // eslint-disable-next-line no-undef
    for (let x in proList) {
      let param = {
        id: proList[x]
      }
      console.log(param)
      axios.get('http://localhost:8080/product', {params: param}).then(res => {
        console.log(res)
        let cart = {
          exist: true,
          proId: res.data.result.id,
          quantity: 1,
          proInfo: {
            name: res.data.result.name,
            type: res.data.result.type,
            price: res.data.result.price,
            imgUrl: require('@/assets/' + res.data.result.description + '.jpg')
          },
          sum () {
            return this.quantity * this.proInfo.price
          }
        }
        this.cartList.push(cart)
        console.log(this.cartList)
      })
    }
    this.user_name = JSON.parse(sessionStorage.getItem('user')).user_name
  }
}
</script>

<style scoped>
  .main{
    background-color: #f3f4f6;
    padding-top: 50px;
  }
  .cart-content{
    background-color: white;
    width: 80vw;
    margin: 0 auto;
    border-radius: 10px;
  }
  .title{
    width: 900px;
    height: 50px;
    font-size: 20px;
    font-weight: 700;
    margin: 0 auto;
    border-bottom: .8px solid grey;
    display: flex;
    flex-direction: row;
    align-items: flex-end;
  }
  .title-text{
    margin-bottom: 0;
  }
  .product{
    width: 900px;
    margin: 0 auto;
  }
  .product-title{
    width: 900px;
    margin: 0 auto;
    margin-top: 50px;
    border-bottom: .8px solid grey;
  }
  .product-info{
    background-color: #f3f4f6;
    margin: 10px auto 0;
    width: 900px;
  }
  .table-item{
    text-align: center;
  }
  .table-item-name{
    display: flex;
    width: 260px;
    flex-direction: row;
    align-items: center;
    padding-left: 50px;
  }
  .item-img{
    width: 50px;
    height: 50px;
    margin-right: 20px;
  }
  .table-item-operation{
    text-align: center;
    color: #23A6D5;
    cursor: pointer;
  }
  .table-row:hover{
    background-color: #f7e2ff;
  }
  .submit-view{
    margin-top: 50px;
    border: 1px solid #BFBFBF;
    background-color: #f7e2ff;
    height: 56px;
    position: relative;
    font-size: 14px;
  }
  .show-null{
    text-align: center;
    margin-top: 20px;
  }
  .submit-btn{
    cursor: pointer;
    width: 150px;
    height: 56px;
    background-color: #f74a4a;
    color: #fff;
    text-align: center;
    font-weight: bold;
    position: absolute;
    right: 0;
  }
  .submit-btn-text{
    position: relative;
    font-size: 18px;
    top: 50%;
    transform: translateY(-50%);
  }
  .total-price{
    font-size: 24px;
    font-weight: bold;
    color: #f74a4a;
    margin-left: 5px;
    margin-right: 5px;
  }
  .submit-payment{
    position: absolute;
    right: 200px;
    top: 50%;
    transform: translateY(-50%);
  }
</style>
