<template>
  <div>
    <my-header></my-header>
    <main class="main" v-if="hackReset">
      <div class="content-modify" v-if="userType === 0">
        <div class="title">我的信息</div>
        <div class="content-item">
          <div class="item-title">会员号:</div>
          <div class="item-content">{{myInfo.id}}</div>
        </div>
        <div class="content-item">
          <div class="item-title">会员姓名:</div>
          <div class="item-content">
            <input type="text" v-model="myInfo.name" class="input-box">
          </div>
        </div>
        <div class="content-item">
          <div class="item-title">性别:</div>
          <div class="item-content">
            <input type="text" v-model="myInfo.gender" class="input-box">
          </div>
        </div>
        <div class="content-item">
          <div class="item-title">城市:</div>
          <div class="item-content">
            <input type="text" v-model="myInfo.city" class="input-box">
          </div>
        </div>
        <div class="content-item">
          <div class="item-title">地址:</div>
          <div class="item-content">
            <input type="text" v-model="myInfo.address" class="input-box">
          </div>
        </div>
        <div class="content-item">
          <div class="item-title">邮编:</div>
          <div class="item-content">
            <input type="text" v-model="myInfo.mail_num" class="input-box">
          </div>
        </div>
        <div class="content-item">
          <div class="item-title">电话:</div>
          <div class="item-content">
            <input type="text" v-model="myInfo.phone" class="input-box">
          </div>
        </div>
        <div class="content-item">
          <div class="item-title">电子邮件:</div>
          <div class="item-content">
            <input type="text" v-model="myInfo.e_mail" class="input-box">
          </div>
        </div>
        <div class="btn-view">
          <div class="save-btn" @click="saveUserInfo">保存</div>
        </div>
        <div class="title">我的订单</div>
        <div v-for="(order,index) in orderList" :key="index" class="order">
          <div class="order-item">
            <div class="item-left">
              <img :src="order.imgUrl" class="item-left-img">
              <div class="item-left-text">
                <p>{{order.product_name}}</p>
                <p>{{order.type}}</p>
                <p>{{order.singer}}</p>
              </div>
            </div>
            <div class="item-right">
              <div>订单号：{{order.order_id}}</div>
              <div>单价：￥ {{order.price}}</div>
              <div>数量：{{order.quantity}}</div>
              <div>小结：￥{{order.mysum}}</div>
              <div>状态：<span :style="{color: order.statusColor}">{{order.status}}</span></div>
            </div>
            <div class="item-btn-view">
              <p>发货地址：</p>
              <input v-model="order.address" type="text" class="input-address">
              <div class="btn-view-right">
                <div class="btn-ship" @click="modifyOrderById(order)">修改地址</div>
              </div>
            </div>
          </div>
        </div>
        <div v-if="orderList.length===0" class="no-order">您暂时还没有订单哦！</div>
      </div>
      <div class="content-modify" v-if="userType === 1">
        <div class="title">订单管理</div>
        <div v-for="(order,index) in orderList" :key="index" class="order" v-if="iforder">
          <div class="order-item">
            <div class="item-left">
              <img :src="order.imgUrl" class="item-left-img">
              <div class="item-left-text">
                <p>{{order.product_name}}</p>
                <p>{{order.type}}</p>
                <p>{{order.singer}}</p>
              </div>
            </div>
            <div class="item-right">
              <div>订单号：{{order.order_id}}</div>
              <div>用户名：{{order.user_name}}</div>
              <div>单价：￥ {{order.price}}</div>
              <div>数量：{{order.quantity}}</div>
              <div>小结：￥{{order.mysum}}</div>
              <div>状态：<span :style="{color: order.statusColor}">{{order.status}}</span></div>
            </div>
            <div class="item-btn-view">
              <p>发货地址：</p>
              <input v-model="order.address" type="text" class="input-address">
              <div class="btn-view-right">
                <div class="btn-ship" @click="modifyOrderById(order)">修改地址</div>
                <div class="btn-ship" v-if="order.status === '未发货'" @click="handleFahuo(order)">发货</div>
              </div>
            </div>
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
  name: 'MyInfo',
  components: {
    myHeader
  },
  data () {
    return {
      user_name: '',
      userType: 0,
      iforder: true,
      hackReset: '',
      myInfo: {
        id: '',
        name: '',
        gender: '',
        city: '',
        address: '',
        mail_num: '',
        phone: '',
        e_mail: ''
      },
      orderList: []
    }
  },
  methods: {
    handleFahuo (item) {
      item.status = '已发货'
      item.statusColor = 'green'
      let param = {
        order_id: item.order_id
      }
      axios.get('http://localhost:8080/sendPro', {params: param}).then(res => {
        alert('已发货')
      })
    },
    checkInputData () {
      if (this.myInfo.gender !== '男' && this.myInfo.gender !== '女' && this.myInfo.gender != null) {
        alert(this.gender)
        return false
      }
      var re = /^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/
      if (!re.test(this.myInfo.e_mail) && this.myInfo.e_mail != null) {
        alert('邮箱格式不正确！')
        return false
      }
      return true
    },
    saveUserInfo () {
      if (!this.checkInputData()) {
        return
      }
      let param = {
        name: this.myInfo.name,
        gender: this.myInfo.gender,
        city: this.myInfo.city,
        address: this.myInfo.address,
        mail_num: this.myInfo.mail_num,
        phone: this.myInfo.phone,
        e_mail: this.myInfo.e_mail,
        user_name: this.user_name
      }
      console.log(param)
      axios.get('http://localhost:8080/modifyUserInfo', {params: param}).then(res => {
        console.log(res.data)
        if (res.data.code === 200) {
          alert('提交成功！')
          window.location.reload()
          axios.get('http://localhost:8080/getUserByUserName', {params: {user_name: this.user_name}}).then(res => {
            if (res.data.code === 200) {
              sessionStorage.setItem('user', JSON.stringify(res.data.result))
            }
          })
        } else {
          alert('提交失败！')
        }
      })
    },
    modifyOrderById (order) {
      let param = {
        address: order.address,
        order_id: order.order_id
      }
      axios.get('http://localhost:8080/modifyOrder', {params: param})
      alert('修改成功！')
    }
  },
  created () {
    this.user_name = JSON.parse(sessionStorage.getItem('user')).user_name
    if (this.user_name === 'admin') {
      this.userType = 1
    }
    if (this.userType === 0) {
      let param = {
        user_name: this.user_name
      }
      axios.get('http://localhost:8080/getOrderByUser', {params: param}).then(res => {
        if (res.data.code === 200) {
          if (res.data.result === null) {
            this.iforder = false
          }
          this.orderList = res.data.result
          for (let x = 0; x < this.orderList.length; x++) {
            let param = {
              id: this.orderList[x].product_id
            }
            axios.get('http://localhost:8080/product', {params: param}).then(res => {
              if (res.data.code === 200) {
                this.orderList[x].product_name = res.data.result.name
                this.orderList[x].type = res.data.result.type
                this.orderList[x].imgUrl = require('@/assets/' + res.data.result.description + '.jpg')
                this.orderList[x].singer = res.data.result.singer
                if (this.orderList[x].status === '未发货') {
                  this.orderList[x].statusColor = 'red'
                } else {
                  this.orderList[x].statusColor = 'green'
                }
                this.$forceUpdate()
              }
            })
            this.hackReset = false
            this.$nextTick(() => {
              this.hackReset = true
            })
          }
        }
      })
      this.myInfo = JSON.parse(sessionStorage.getItem('user'))
      this.$forceUpdate()
    }
    if (this.userType === 1) {
      axios.get('http://localhost:8080/getAllOrder').then(res => {
        console.log(res)
        this.orderList = res.data.result
        for (let x = 0; x < this.orderList.length; x++) {
          let param = {
            id: this.orderList[x].product_id
          }
          axios.get('http://localhost:8080/product', {params: param}).then(res => {
            if (res.data.code === 200) {
              this.orderList[x].product_name = res.data.result.name
              this.orderList[x].type = res.data.result.type
              this.orderList[x].imgUrl = require('@/assets/' + res.data.result.description + '.jpg')
              this.orderList[x].singer = res.data.result.singer
              if (this.orderList[x].status === '未发货') {
                this.orderList[x].statusColor = 'red'
              } else {
                this.orderList[x].statusColor = 'green'
              }
              this.$forceUpdate()
            }
          })
        }
      })
    }
    this.hackReset = false
    this.$nextTick(() => {
      this.hackReset = true
    })
  }
}
</script>

<style scoped>
  .main{
    background-color: #f3f4f6;
    padding-top: 30px;
    padding-bottom: 30px;
  }
  .content-modify{
    width: 800px;
    margin: 0 auto;
    border: 1px solid white;
    background: white;
    border-radius: 10px;
  }
  .title{
    width: 200px;
    margin-left: 22px;
    border-bottom: 1px solid #00a1d6;
    margin-top: 50px;
    color: #00a1d6;
    font-size: 16px;
    margin-bottom: 25px;
    padding-bottom: 10px;
  }
  .content-item{
    display: flex;
    flex-direction: row;
    align-items: center;
    margin-bottom: 22px;
    margin-left: 50px;
  }
  .item-title{
    width: 80px;
    font-size: 14px;
    text-align: right;
    margin-right: 20px;
    color: #48576a;
  }
  .input-box{
    width: 300px;
    height: 30px;
    border-radius: 4px;
    border: 1px solid #bfcbd9;
    box-sizing: border-box;
    color: #1f2d3d;
    padding: 3px 10px
  }
  .input-address{
    width: 270px;
    height: 30px;
    border-radius: 4px;
    border: 1px solid #bfcbd9;
    font-size: 15px;
    box-sizing: border-box;
    color: #1f2d3d;
    padding: 3px 10px;
    margin-top: 10px;
  }
  .order-item{
    display: flex;
    flex-direction: row;
    padding-left: 25px;
    border: 1px solid #9F92A7;
    margin: 22px;
    position: relative;
  }
  .order-item:hover{
    border-color: #00a1d6;
  }
  .item-left{
    width: 270px;
    display: flex;
    flex-direction: row;
    color: #48576a;
  }
  .item-right{
    margin-left: 20px;
    width: 124px;
  }
  .item-btn-view{
    margin-left: 30px;
  }
  .item-left-img{
    width: 126px;
    height: 126px;
  }
  .item-left-text{
    margin-left: 10px;
    width: 134px;
  }
  .btn-view{
    width: 700px;
    border-top: 1px solid #00a1d6;
    margin: 0 auto;
    background: white;
  }
  .save-btn{
    width: 110px;
    color: #fff;;
    background: #00a1d6!important;
    border-color: #20a0ff;
    border-radius: 4px;
    padding: 10px 10px;
    cursor: pointer;
    font-size: 14px;
    text-align: center;
    margin: 30px auto;
  }
  .save-btn:hover{
    background: #20a0ff;
    border-color: #20a0ff;
  }
  .save-btn:active{
    background: #1d90e6;
    border-color: #1d90e6;
  }
  .btn-ship{
    width: 80px;
    height: 20px;
    color: #fff;;
    background: #00a1d6!important;
    border-color: #20a0ff;
    border-radius: 4px;
    padding: 10px 10px;
    cursor: pointer;
    font-size: 14px;
    text-align: center;
  }
  .btn-view-right{
    display: flex;
    flex-direction: row;
    width: 280px;
    height: 65px;
    justify-content: space-around;
    align-items: center;
  }
  .no-order{
    text-align: center;
  }
</style>
